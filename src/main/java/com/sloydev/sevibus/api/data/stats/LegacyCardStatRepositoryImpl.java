package com.sloydev.sevibus.api.data.stats;

import com.google.firebase.database.FirebaseDatabase;
import com.sloydev.sevibus.api.domain.stats.LegacyCardStat;
import com.sloydev.sevibus.api.domain.stats.LegacyCardStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LegacyCardStatRepositoryImpl implements LegacyCardStatRepository {

    private static final String FIREBASE_PATH = "legacycard";

    private final FirebaseDatabase firebaseDatabase;

    @Autowired
    public LegacyCardStatRepositoryImpl(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    public void put(LegacyCardStat legacyCardStat) {
        firebaseDatabase.getReference(FIREBASE_PATH)
                .child(legacyCardStat.getNumber().toString())
                .setValue(legacyCardStat);
    }
}
