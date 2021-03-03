package com.example.fishingpdm;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    juegoView v;
    Personaje personaje;
    public float x,y;
    private SensorManager manejadorSensor;
    private Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        v = new juegoView(this);
        setContentView(v);
        personaje = new Personaje(v);
        manejadorSensor = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        if(manejadorSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!=null){
            sensor = manejadorSensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            //manejadorSensor.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        manejadorSensor.registerListener(this, sensor,SensorManager.SENSOR_DELAY_GAME);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {

            x = event.values[0];
            y = event.values[1];
            System.out.println("VALORES ACELEROMETRO "+ x +" "+ y);
            v.cambiar(x,y);
            //personaje.setX((int)x);
            //personaje.setY((int)y);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}