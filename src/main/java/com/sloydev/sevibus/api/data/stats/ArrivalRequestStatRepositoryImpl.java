package com.sloydev.sevibus.api.data.stats;

import com.google.firebase.database.FirebaseDatabase;
import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStat;
import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ArrivalRequestStatRepositoryImpl implements ArrivalRequestStatRepository {

    private static final String FIREBASE_PATH = "stats";

    private final FirebaseDatabase firebaseDatabase;

    @Autowired
    public ArrivalRequestStatRepositoryImpl(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    public void putStat(ArrivalRequestStat arrivalRequestStat) {
        firebaseDatabase.getReference(FIREBASE_PATH).push().setValue(arrivalRequestStat);
    }
}
