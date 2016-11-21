package com.sloydev.sevibus.api.domain.stats;

import com.sloydev.sevibus.api.view.card.LegacyCardViewModel;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

public class LegacyCardStat {

    private final Long number;
    private final String userId;
    private final Instant updatedAt;
    private final LegacyCardViewModel cardInfoResult;
    private final String error;

    private LegacyCardStat(Builder builder) {
        this.number = requireNonNull(builder.number);
        this.userId = requireNonNull(builder.userId);
        this.updatedAt = requireNonNull(builder.updatedAt);
        this.cardInfoResult = builder.cardInfoResult;
        this.error = builder.error;
    }

    public static Builder create() {
        return new Builder();
    }

    public Long getNumber() {
        return number;
    }

    public LegacyCardViewModel getCardInfoResult() {
        return cardInfoResult;
    }

    public String getUserId() {
        return userId;
    }

    public Long getUpdatedAt() {
        return updatedAt.toEpochMilli();
    }

    public String getError() {
        return error;
    }

    public static final class Builder {
        private Long number;
        private LegacyCardViewModel cardInfoResult;
        private String userId;
        private Instant updatedAt;
        private String error;

        private Builder() {
        }

        public LegacyCardStat build() {
            return new LegacyCardStat(this);
        }

        public Builder number(Long number) {
            this.number = number;
            return this;
        }

        public Builder cardInfoResult(LegacyCardViewModel cardInfoResult) {
            this.cardInfoResult = cardInfoResult;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder updatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder error(Exception error) {
            this.error = error.getClass().getName() + ": " + error.getMessage();
            return this;
        }
    }
}
