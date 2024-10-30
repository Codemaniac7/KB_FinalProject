package com.kb.board.dto;

public enum BoardStatus {
    ACTIVE("y"),
    INACTIVE("n");

    private final String value;

    BoardStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BoardStatus fromValue(String value) {
        if (value == null) {
            return null; // null 처리
        }
        value = value.trim(); // 공백 제거
        for (BoardStatus status : BoardStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) { // 대소문자 무시
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}
