package com.rak.feecollection.enums;

public enum CardType {
    VISA(1),
    MASTER(2);

    private int id;
    CardType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CardType getById(int id) {
        for (CardType type : CardType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CardType ID: " + id);
    }
}
