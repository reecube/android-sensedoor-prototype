package com.reecube.sensedoor_prototype.firebase;

import java.util.List;

public class DatabaseEntity {
    public SensorEntity imu;
    public int imuInterval;
    public JoystickEntity joystick;
    public List<List<Integer>> matrix;
}
