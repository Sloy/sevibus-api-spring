package com.sloydev.sevibus.api.data.stats;

import com.google.firebase.database.FirebaseDatabase;
import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStat;
import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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

        ZonedDateTime statDateTime = arrivalRequestStat.getZonedDateTime();

        String dayOfMonth = String.valueOf(statDateTime.getDayOfMonth());
        String month = String.valueOf(statDateTime.getMonthValue());
        String hourOfDay = String.valueOf(statDateTime.getHour());

        firebaseDatabase.getReference(FIREBASE_PATH)
                .child(month)
                .child(dayOfMonth)
                .child(hourOfDay)
                .push()
                .setValue(arrivalRequestStat);
    }
}
