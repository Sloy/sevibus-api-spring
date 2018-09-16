package com.sloydev.sevibus.api.data.stats;

import com.google.firebase.database.FirebaseDatabase;
import com.sloydev.sevibus.api.domain.stats.CardStat;
import com.sloydev.sevibus.api.domain.stats.LegacyCardStat;
import com.sloydev.sevibus.api.domain.stats.LegacyCardStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LegacyCardStatRepositoryImpl implements LegacyCardStatRepository {

    private static final String FIREBASE_PATH_LEGACY = "legacycard";
    private static final String FIREBASE_PATH = "card";

    private final FirebaseDatabase firebaseDatabase;

    @Autowired
    public LegacyCardStatRepositoryImpl(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    public void put(LegacyCardStat legacyCardStat) {
        firebaseDatabase.getReference(FIREBASE_PATH_LEGACY)
                .child(legacyCardStat.getNumber().toString())
                .setValue(legacyCardStat);
    }

    @Override
    public void put(CardStat cardStat) {
        firebaseDatabase.getReference(FIREBASE_PATH)
                .child(cardStat.getNumber().toString())
                .setValue(cardStat);
    }
}
