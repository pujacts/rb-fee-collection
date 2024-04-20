package com.rak.feecollection.service.impl;

import com.rak.feecollection.entity.Receipt;
import com.rak.feecollection.entity.SchoolDetail;
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
    public ReceiptResponse performFeePayment(final PaymentRequest paymentRequest) {
        logger.info("Perform Fee Payment of Student ID :: [{}] ", paymentRequest.getStudentDetails().getStudentId());
        ReceiptResponse receiptResponse = new ReceiptResponse();
        try {
            List<Receipt> receiptList = new ArrayList<>();
            PurchaseDetailsResponse purchaseDetailsResponse = new PurchaseDetailsResponse();
            StudentDetail studentDetail = paymentRequest.getStudentDetails();
            SchoolDetail schoolDetail = studentDetail.getSchoolDetail();
            String schoolName = schoolDetail.getSchoolName();
            String grade = schoolDetail.getGrade();
            PaymentTransactionDetail paymentTransactionDetail = CalculateFee.feeCalculation(
                    schoolName,
                    grade
            );
            if (paymentTransactionDetail.getTotalFee() > paymentRequest.getCardDetails().getTransactionAmount().intValue()) {
                throw new IllegalStateException("Transaction Amount is lesser than required Fee");
            }
            schoolDetail = validateSchoolDetail(schoolName, grade);
            createReceiptEntity(paymentRequest, studentDetail, schoolDetail, paymentTransactionDetail, receiptList);

            List<Receipt> receipts = receiptRepository.saveAll(receiptList);
            List<ReceiptData> receiptDataList = mapStudentToStudentResponse(receipts);

            receiptResponse.setReceiptDataList(receiptDataList);
            purchaseDetailsResponse.setGrade(grade);
            purchaseDetailsResponse.setTuitionFee(paymentTransactionDetail.getTotalFee());
            purchaseDetailsResponse.setCustomAmount(CURRENCY_AED + " " + calculateCustomFee(1, paymentTransactionDetail.getTotalFee()));
            receiptResponse.setPurchaseDetailsResponse(purchaseDetailsResponse);
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

    private SchoolDetail validateSchoolDetail(String schoolName, String grade) {
        SchoolDetail schoolDetail;
        logger.info("Validate school detail :: [{}] ", schoolName, grade);
        schoolDetail = schoolDetailRepository.findBySchoolName(schoolName);
        if (schoolDetail == null) {
            schoolDetail = new SchoolDetail();
            schoolDetail.setSchoolName(schoolName);
            schoolDetail.setGrade(grade);
            schoolDetailRepository.save(schoolDetail);
        }
        return schoolDetail;
    }

    private static void createReceiptEntity(PaymentRequest paymentRequest, StudentDetail studentDetail, SchoolDetail schoolDetail, PaymentTransactionDetail paymentTransactionDetail, List<Receipt> receiptList) {
        logger.info("Create receipt entity");
        Receipt receipt = new Receipt();
        receipt.setStudentName(studentDetail.getStudentName());
        receipt.setStudentId(studentDetail.getStudentId());
        receipt.setTransactionReference(paymentTransactionDetail.getTransactionReference());
        receipt.setCardNumber(paymentRequest.getCardDetails().getCardNumber());
        receipt.setCardType(paymentRequest.getCardDetails().getCardType());
        receipt.setTransactionAmount(paymentTransactionDetail.getTotalFee());
        receipt.setCollectionDate(OffsetDateTime.now());
        receipt.setSchoolDetail(schoolDetail);
        receiptList.add(receipt);
    }

    @Override
    public ReceiptResponse getReceiptDetail(final Long studentId) {
        logger.info("Get receipt detail of Student ID :: [{}] ", studentId);
        ReceiptResponse receiptResponse = new ReceiptResponse();
        try {
            List<Receipt> receipts = receiptRepository.findByStudentId(studentId);
            if (receipts.isEmpty()) {
                receiptResponse.setStatusMessage(NOT_FOUND_MSG);
                receiptResponse.setStatusCode(NOT_FOUND);
                return receiptResponse;
            }
            List<ReceiptData> receiptData = mapStudentToStudentResponse(receipts);
            PurchaseDetailsResponse purchaseDetailsResponse = new PurchaseDetailsResponse();
            double customTotal = receiptData.stream().map(ReceiptData::getTransactionAmount).reduce(0.0, (a, b) -> a + b);
            purchaseDetailsResponse.setCustomAmount(String.valueOf(customTotal));
            receiptResponse.setPurchaseDetailsResponse(purchaseDetailsResponse);
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
    public ReceiptResponse getAllReceipts(final String schoolName, final String grade) {
        logger.info("Get ALl receipt detail of School :: [{}] and Grade :: [{}] ", schoolName, grade);

        ReceiptResponse receiptResponse = new ReceiptResponse();
        try {
            List<Receipt> receipts = receiptRepository
                    .findAllBySchoolDetail_SchoolNameAndSchoolDetail_Grade(schoolName, grade);
            List<ReceiptData> receiptData = mapStudentToStudentResponse(receipts);
            PurchaseDetailsResponse purchaseDetailsResponse = new PurchaseDetailsResponse();
            double customTotal = receiptData.stream().map(ReceiptData::getTransactionAmount).reduce(0.0, (a, b) -> a + b);
            purchaseDetailsResponse.setCustomAmount(String.valueOf(customTotal));
            receiptResponse.setPurchaseDetailsResponse(purchaseDetailsResponse);
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
