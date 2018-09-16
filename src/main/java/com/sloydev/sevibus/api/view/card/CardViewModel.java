package com.sloydev.sevibus.api.view.card;

import static com.google.common.base.Preconditions.checkNotNull;

public class CardViewModel {
    private final Long serialNumber;
    private final Long typeCode;
    private final String typeName;
    private final String lastOperationDate;
    private final String remainingMoney;
    private final Long remainingTrips;
    private final Long result;
    private final String expirationDate;
    private final Boolean expires;

    private CardViewModel(Builder builder) {
        this.serialNumber = checkNotNull(builder.serialNumber);
        this.typeCode = checkNotNull(builder.typeCode);
        this.typeName = checkNotNull(builder.typeName);
        this.lastOperationDate = builder.lastOperationDate;
        this.remainingMoney = checkNotNull(builder.remainingMoney);
        this.remainingTrips = checkNotNull(builder.remainingTrips);
        this.result = checkNotNull(builder.result);
        this.expirationDate = checkNotNull(builder.expirationDate);
        this.expires = checkNotNull(builder.expires);
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public Long getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getLastOperationDate() {
        return lastOperationDate;
    }

    public String getRemainingMoney() {
        return remainingMoney;
    }

    public Long getRemainingTrips() {
        return remainingTrips;
    }

    public Long getResult() {
        return result;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public Boolean getExpires() {
        return expires;
    }

    public static Builder newCardViewModel() {
        return new Builder();
    }

    public static final class Builder {
        private Long serialNumber;
        private Long typeCode;
        private String typeName;
        private String lastOperationDate;
        private String remainingMoney;
        private Long remainingTrips;
        private Long result;
        private String expirationDate;
        private Boolean expires;

        private Builder() {
        }

        public CardViewModel build() {
            return new CardViewModel(this);
        }

        public Builder serialNumber(Long serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public Builder typeCode(Long typeCode) {
            this.typeCode = typeCode;
            return this;
        }

        public Builder typeName(String typeName) {
            this.typeName = typeName;
            return this;
        }

        public Builder lastOperationDate(String lastOperationDate) {
            this.lastOperationDate = lastOperationDate;
            return this;
        }

        public Builder remainingMoney(String remainingMoney) {
            this.remainingMoney = remainingMoney;
            return this;
        }

        public Builder remainingTrips(Long remainingTrips) {
            this.remainingTrips = remainingTrips;
            return this;
        }

        public Builder result(Long result) {
            this.result = result;
            return this;
        }

        public Builder expirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder expires(Boolean expires) {
            this.expires = expires;
            return this;
        }
    }
}
