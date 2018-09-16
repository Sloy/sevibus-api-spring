package com.sloydev.sevibus.api.domain.stats;

import com.sloydev.sevibus.api.data.internal.apptussamjson.CardStatusTussamModel;
import java.time.Instant;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class CardStat {

    private final Long number;
    private final String userId;
    private final Instant updatedAt;
    private final CardStatusTussamModel cardStatus;
    private final String error;

    public CardStat(Long number, String userId, Instant updatedAt,
            CardStatusTussamModel cardStatus, String error) {
        this.number = checkNotNull(number);
        this.userId = checkNotNull(userId);
        this.updatedAt = checkNotNull(updatedAt);
        this.cardStatus = cardStatus;
        this.error = error;
    }

    @NotNull
    public Long getNumber() {
        return number;
    }

    @Nullable
    public CardStatusTussamModel getCardStatus() {
        return cardStatus;
    }

    @NotNull
    public String getUserId() {
        return userId;
    }

    @NotNull
    public Long getUpdatedAt() {
        return updatedAt.toEpochMilli();
    }

    @Nullable
    public String getError() {
        return error;
    }
}
