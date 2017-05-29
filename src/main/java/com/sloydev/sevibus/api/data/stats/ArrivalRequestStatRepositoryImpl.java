package com.sloydev.sevibus.api.data.stats;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sloydev.sevibus.api.data.stats.mapper.ArrivalRequestStatMapper;
import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStat;
import com.sloydev.sevibus.api.domain.stats.ArrivalRequestStatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sloydev.sevibus.api.FirebaseUtils.readFrom;
import static com.sloydev.sevibus.api.StreamUtils.streamOf;

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

        int month = statDateTime.getMonthValue();
        int dayOfMonth = statDateTime.getDayOfMonth();
        int hourOfDay = statDateTime.getHour();

        getHourReference(month, dayOfMonth, hourOfDay)
                .push()
                .setValue(arrivalRequestStat);
    }

    @Override
    public List<ArrivalRequestStat> getByMonthAndDayAndHour(int month, int day, int hour) {
        DatabaseReference hourReference = getHourReference(month, day, hour);
        DataSnapshot hourSnapshot = readFrom(hourReference);
        return readStatsFromHour(hourSnapshot)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArrivalRequestStat> getByMonthAndDay(int month, int day) {
        DatabaseReference dayReference = getDayReference(month, day);
        DataSnapshot daySnapshot = readFrom(dayReference);
        return readStatsFromDay(daySnapshot)
                .collect(Collectors.toList());
    }

    @Override
    public void removeByMonth(Integer month) {
        getMonthReference(month)
                .removeValue();
    }

    private DatabaseReference getMonthReference(int month) {
        return firebaseDatabase.getReference(FIREBASE_PATH)
                .child(String.valueOf(month));
    }

    private DatabaseReference getDayReference(int month, int day) {
        return getMonthReference(month)
                .child(String.valueOf(day));
    }

    private DatabaseReference getHourReference(int month, int day, int hour) {
        return getDayReference(month, day)
                .child(String.valueOf(hour));
    }

    private Stream<ArrivalRequestStat> readStatsFromHour(DataSnapshot hourSnapshot) {
        return streamOf(hourSnapshot.getChildren())
                .map(ArrivalRequestStatMapper::map);
    }

    private Stream<ArrivalRequestStat> readStatsFromDay(DataSnapshot daySnapshot) {
        return streamOf(daySnapshot.getChildren())
                .flatMap(this::readStatsFromHour);
    }

}
