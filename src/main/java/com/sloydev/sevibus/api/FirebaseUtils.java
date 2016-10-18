package com.sloydev.sevibus.api;

import com.google.firebase.database.*;
import javaslang.control.Either;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class FirebaseUtils {

    public static DataSnapshot readFrom(DatabaseReference reference) throws DatabaseException {
        return readAsEitherFrom(reference)
                .getOrElseThrow(DatabaseError::toException);
    }

    private static Either<DatabaseError, DataSnapshot> readAsEitherFrom(DatabaseReference reference) {
        CompletableFuture<Either<DatabaseError, DataSnapshot>> future = new CompletableFuture<>();
        reference.addListenerForSingleValueEvent(listener(
                dataSnapshot -> future.complete(Either.right(dataSnapshot)),
                databaseError -> future.complete(Either.left(databaseError))
        ));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static ValueEventListener listener(
            Consumer<DataSnapshot> onDataChange,
            Consumer<DatabaseError> onCancelled) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onDataChange.accept(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onCancelled.accept(databaseError);
            }
        };
    }
}
