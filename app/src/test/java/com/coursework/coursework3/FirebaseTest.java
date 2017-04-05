package com.coursework.coursework3;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 4/4/2017.
 */
public class FirebaseTest {
    @Test
    public void uploadData() throws Exception {
        double mT = System.currentTimeMillis();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbref = mDatabase.getReference("" + mT);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = (String) dataSnapshot.getValue();
                assertEquals("test", data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("tests", "some sort of error");
            }
        });

        dbref.setValue("test");

    }


}

