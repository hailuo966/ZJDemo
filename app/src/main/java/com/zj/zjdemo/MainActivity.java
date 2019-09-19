package com.zj.zjdemo;

/**
 * Created by yanglin on 2018/10/7.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {
    private SensorManager sensorManager = null;
    private Sensor gyroSensor = null;
    private TextView vX;
    private TextView vY;
    private TextView vZ;
    private TextView v;
    private TextView magneticx;
    private TextView magneticy;
    private TextView magneticz;
    private TextView accelerometerx;
    private TextView accelerometery;
    private TextView accelerometerz;


    private Button button;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float[] angle = new float[3];

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vX = findViewById(R.id.vx);
        vY = findViewById(R.id.vy);
        vZ = findViewById(R.id.vz);
        v = findViewById(R.id.v);

        magneticx = findViewById(R.id.magneticx);
        magneticy = findViewById(R.id.magneticy);
        magneticz = findViewById(R.id.magneticz);

        accelerometerx = findViewById(R.id.accelerometerx);
        accelerometery = findViewById(R.id.accelerometery);
        accelerometerz = findViewById(R.id.accelerometerz);


        button = (Button) findViewById(R.id.button);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        vX.setText("!!!!!!");
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
// TODO Auto-generated method stub
//声明可变字符串
                StringBuffer sb = new StringBuffer();
//获取手机全部的传感器
                List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
//迭代输出获得上的传感器

                for (Sensor sensor : sensors) {
                    sb.append(sensor.getName().toString());
                    sb.append("\n");
                    Log.i("传感器", sensor.getName().toString());
                }
//给文本控件赋值
                v.setText(sb.toString());
            }
        });


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensorManager.registerListener(sensorEventListener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorEventListener, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        float[] gyroscopeValues = new float[3];
        float[] magneticValues = new float[3];
        float[] accelerometerValues = new float[3];

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = sensorEvent.values.clone();
                accelerometerx.setText("ACCELERATION X: " + accelerometerValues[0]);
                accelerometery.setText("ACCELERATION Y: " + accelerometerValues[1]);
                accelerometerz.setText("ACCELERATION Z: " + accelerometerValues[2]);
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magneticValues = sensorEvent.values.clone();
                magneticx.setText("MAGNETIC X: " + magneticValues[0]);
                magneticy.setText("MAGNETIC Y: " + magneticValues[1]);
                magneticz.setText("MAGNETIC Z: " + magneticValues[2]);
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                gyroscopeValues = sensorEvent.values.clone();
                vX.setText("Gyroscope X: " + gyroscopeValues[0]);
                vY.setText("Gyroscope Y: " + gyroscopeValues[1]);
                vZ.setText("Gyroscope Z: " + gyroscopeValues[2]);
            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(sensorEventListener);
    }
}