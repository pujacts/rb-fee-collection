package com.rak.feecollection.service.impl;

import com.rak.feecollection.entity.Receipt;
import com.rak.feecollection.entity.School;
import com.rak.feecollection.model.*;
import com.rak.feecollection.repository.ReceiptRepository;
import com.rak.feecollection.repository.SchoolDetailRepository;
import com.rak.feecollection.service.ReceiptService;
import com.rak.feecollection.util.CalculateFee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.rak.feecollection.constant.ReceiptConstants.*;
import static com.rak.feecollection.mapper.SchoolMapper.toDto;
import static com.rak.feecollection.mapper.SchoolMapper.toEntity;
import static com.rak.feecollection.util.ReceiptUtility.calculateCustomFee;
import static com.rak.feecollection.util.ReceiptUtility.mapStudentToStudentResponse;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    private final ReceiptRepository receiptRepository;
    private final SchoolDetailRepository schoolDetailRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, SchoolDetailRepository schoolDetailRepository) {
        this.receiptRepository = receiptRepository;
        this.schoolDetailRepository = schoolDetailRepository;
    }

    @Override
    public ReceiptResponseDto performFeePayment(final PaymentRequestDto paymentRequestDto) {
        logger.info("Perform Fee Payment of Student ID :: [{}] ", paymentRequestDto.getStudentDto().getStudentId());
        ReceiptResponseDto receiptResponse = new ReceiptResponseDto();
        try {
            List<Receipt> receiptList = new ArrayList<>();
            PurchaseDetailsResponseDto purchaseDetailsResponseDto = new PurchaseDetailsResponseDto();
            StudentDto studentDto = paymentRequestDto.getStudentDto();
            SchoolDto schoolDto = studentDto.getSchoolDto();
            String schoolName = schoolDto.getSchoolName();
            String grade = schoolDto.getGrade();
            PaymentTransactionDto paymentTransactionDto = CalculateFee.feeCalculation(
                    schoolName,
                    grade
            );
            if (paymentTransactionDto.getTotalFee() > paymentRequestDto.getCardDto().getTransactionAmount().intValue()) {
                throw new IllegalStateException("Transaction Amount is lesser than required Fee");
            }
            schoolDto = validateSchoolDetail(schoolName, grade);
            createReceiptEntity(paymentRequestDto, studentDto, schoolDto, paymentTransactionDto, receiptList);

            List<Receipt> receipts = receiptRepository.saveAll(receiptList);
            List<ReceiptDto> receiptDtoList = mapStudentToStudentResponse(receipts);

            receiptResponse.setReceiptDataList(receiptDtoList);
            purchaseDetailsResponseDto.setGrade(grade);
            purchaseDetailsResponseDto.setTuitionFee(paymentTransactionDto.getTotalFee());
            purchaseDetailsResponseDto.setCustomAmount(CURRENCY_AED + " " + calculateCustomFee(1, paymentTransactionDto.getTotalFee()));
            receiptResponse.setPurchaseDetailsResponse(purchaseDetailsResponseDto);
            receiptResponse.setStatusCode(SUCCESS_CODE);
            receiptResponse.setStatusMessage(SUCCESS);
        } catch (Exception ex) {
            String errMsg = ex.getLocalizedMessage();
            logger.error(errMsg);
            receiptResponse.setStatusMessage(errMsg);
            receiptResponse.setStatusCode(SERVER_ERROR);
        }
        return receiptResponse;
    }

    private SchoolDto validateSchoolDetail(String schoolName, String grade) {

        logger.info("Validate school detail :: [{}] ", schoolName, grade);
        School school = schoolDetailRepository.findBySchoolName(schoolName);
        if (school == null) {
            school = new School();
            school.setSchoolName(schoolName);
            school.setGrade(grade);
            schoolDetailRepository.save(school);
        }
        return toDto(school);
    }

    private static void createReceiptEntity(PaymentRequestDto paymentRequestDto, StudentDto studentDto, SchoolDto schoolDto, PaymentTransactionDto paymentTransactionDto, List<Receipt> receiptList) {
        logger.info("Create receipt entity");
        Receipt receipt = new Receipt();
        receipt.setStudentName(studentDto.getStudentName());
        receipt.setStudentId(studentDto.getStudentId());
        receipt.setTransactionReference(paymentTransactionDto.getTransactionReference());
        receipt.setCardNumber(paymentRequestDto.getCardDto().getCardNumber());
        receipt.setCardType(paymentRequestDto.getCardDto().getCardType());
        receipt.setTransactionAmount(paymentTransactionDto.getTotalFee());
        receipt.setCollectionDate(OffsetDateTime.now());
        receipt.setSchoolDetail(toEntity(schoolDto));
        receiptList.add(receipt);
    }

    @Override
    public ReceiptResponseDto getReceiptDetail(final Long studentId) {
        logger.info("Get receipt detail of Student ID :: [{}] ", studentId);
        ReceiptResponseDto receiptResponse = new ReceiptResponseDto();
        try {
            List<Receipt> receipts = receiptRepository.findByStudentId(studentId);
            if (receipts.isEmpty()) {
                receiptResponse.setStatusMessage(NOT_FOUND_MSG);
                receiptResponse.setStatusCode(NOT_FOUND);
                return receiptResponse;
            }
            List<ReceiptDto> receiptData = mapStudentToStudentResponse(receipts);
            PurchaseDetailsResponseDto purchaseDetailsResponseDto = new PurchaseDetailsResponseDto();
            double customTotal = receiptData.stream().map(ReceiptDto::getTransactionAmount).reduce(0.0, (a, b) -> a + b);
            purchaseDetailsResponseDto.setCustomAmount(String.valueOf(customTotal));
            receiptResponse.setPurchaseDetailsResponse(purchaseDetailsResponseDto);
            receiptResponse.setReceiptDataList(receiptData);
            receiptResponse.setStatusMessage(SUCCESS);
            receiptResponse.setStatusCode(SUCCESS_CODE);
        } catch (Exception e) {
            String errMsg = e.getLocalizedMessage();
            logger.error(errMsg);
            receiptResponse.setStatusCode(SERVER_ERROR);
            receiptResponse.setStatusMessage(errMsg);
        }
        return receiptResponse;
    }

    @Override
    public ReceiptResponseDto getAllReceipts(final String schoolName, final String grade) {
        logger.info("Get ALl receipt detail of School :: [{}] and Grade :: [{}] ", schoolName, grade);

        ReceiptResponseDto receiptResponse = new ReceiptResponseDto();
        try {
            List<Receipt> receipts = receiptRepository
                    .findAllBySchoolDetail_SchoolNameAndSchoolDetail_Grade(schoolName, grade);
            List<ReceiptDto> receiptData = mapStudentToStudentResponse(receipts);
            PurchaseDetailsResponseDto purchaseDetailsResponseDto = new PurchaseDetailsResponseDto();
            double customTotal = receiptData.stream().map(ReceiptDto::getTransactionAmount).reduce(0.0, (a, b) -> a + b);
            purchaseDetailsResponseDto.setCustomAmount(String.valueOf(customTotal));
            receiptResponse.setPurchaseDetailsResponse(purchaseDetailsResponseDto);
            receiptResponse.setReceiptDataList(receiptData);
            receiptResponse.setStatusMessage(SUCCESS);
            receiptResponse.setStatusCode(SUCCESS_CODE);
        } catch (Exception e) {
            String errMsg = e.getLocalizedMessage();
            logger.error(errMsg);
            receiptResponse.setStatusCode(SERVER_ERROR);
            receiptResponse.setStatusMessage(errMsg);
        }
        return receiptResponse;
    }
}
