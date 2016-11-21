package com.sloydev.sevibus.api.view.card;

public class LegacyCardViewModel {

    public final Long number;
    public final String type;
    public final String lastOperationDate;
    public final String expirationDate;
    public final Double credit;

    private LegacyCardViewModel(Builder builder) {
        this.number = builder.number;
        this.type = builder.type;
        this.lastOperationDate = builder.lastOperationDate;
        this.expirationDate = builder.expirationDate;
        this.credit = builder.credit;
    }

    public static Builder create() {
        return new Builder();
    }


    public static final class Builder {
        private Long number;
        private String type;
        private String lastOperationDate;
        private String expirationDate;
        private Double credit;

        private Builder() {
        }

        public LegacyCardViewModel build() {
            return new LegacyCardViewModel(this);
        }

        public Builder number(Long number) {
            this.number = number;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder lastOperationDate(String lastOperationDate) {
            this.lastOperationDate = lastOperationDate;
            return this;
        }

        public Builder expirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public Builder credit(Double credit) {
            this.credit = credit;
            return this;
        }
    }
}
