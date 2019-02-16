package com.reecube.sensedoor_prototype;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.reecube.sensedoor_prototype.firebase.JoystickEntity;

public class JoystickFragment extends AbstractFirebaseFragment {

    private ImageView joystickLeft;

    private ImageView joystickUp;

    private ImageView joystickRight;

    private ImageView joystickDown;

    private ImageView joystickCenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joystick, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        joystickLeft = view.findViewById(R.id.joystickLeft);
        joystickUp = view.findViewById(R.id.joystickUp);
        joystickRight = view.findViewById(R.id.joystickRight);
        joystickDown = view.findViewById(R.id.joystickDown);
        joystickCenter = view.findViewById(R.id.joystickCenter);

        firebaseDatabase.child("joystick").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                JoystickEntity joystick = dataSnapshot.getValue(JoystickEntity.class);

                assert joystick != null;

                updateView(joystick);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Getting Post failed, log a message
                Log.w("ERROR", "loadPost:onCancelled", databaseError.toException());

                updateView(new JoystickEntity());
            }
        });
    }

    public void updateView(JoystickEntity joystick) {
        if (joystick.left.equals(JoystickEntity.PRESSED)) {
            joystickLeft.setImageResource(R.drawable.ic_joystick_left_1);
        } else {
            joystickLeft.setImageResource(R.drawable.ic_joystick_left_0);
        }


        if (joystick.up.equals(JoystickEntity.PRESSED)) {
            joystickUp.setImageResource(R.drawable.ic_joystick_up_1);
        } else {
            joystickUp.setImageResource(R.drawable.ic_joystick_up_0);
        }


        if (joystick.right.equals(JoystickEntity.PRESSED)) {
            joystickRight.setImageResource(R.drawable.ic_joystick_right_1);
        } else {
            joystickRight.setImageResource(R.drawable.ic_joystick_right_0);
        }


        if (joystick.down.equals(JoystickEntity.PRESSED)) {
            joystickDown.setImageResource(R.drawable.ic_joystick_down_1);
        } else {
            joystickDown.setImageResource(R.drawable.ic_joystick_down_0);
        }


        if (joystick.click.equals(JoystickEntity.PRESSED)) {
            joystickCenter.setImageResource(R.drawable.ic_joystick_center_1);
        } else {
            joystickCenter.setImageResource(R.drawable.ic_joystick_center_0);
        }
    }
}
