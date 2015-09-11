package com.rainbowroad.amazement;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Character_Select_Screen extends ActionBarActivity implements GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener, SensorEventListener {

    //All drawable images and their names put into an array
    private int[] pics = {R.drawable.stickman,R.drawable.running_stickman,R.drawable.hiker,R.drawable.toilet_guy,R.drawable.slinky,R.drawable.oldcouple,R.drawable.rock};
    private String[] names = {"Stairs Guy","Exit Sign Guy","Hiker","Toilet Guy","Sneaky","Old Couple","Dwayne Johnson"};
    private ImageView characterPanel;
    private TextView characterName;
    private int i=0;
    private int length = pics.length;

    private Sensor accelerometer;
    private SensorManager mySensorManager;

    private float mAccel;           // acceleration apart from gravity
    private float mAccelCurrent;    // current acceleration including gravity
    private float mAccelLast;       // last acceleration including gravity

    private GestureDetectorCompat gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character__select__screen);

        this.gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);

        //Set Java names to the Views
        characterPanel = (ImageView)findViewById(R.id.character_panel);
        characterName = (TextView)findViewById(R.id.character_name);
        TextView toggleCharacterInfo = (TextView)findViewById(R.id.toggle_character_info);

        //Set default image and name
        characterPanel.setImageResource(pics[i]);
        characterName.setText(names[i]);

        mySensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        //Make sure the sensor we want is on the device
        if (mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
        {
            //If Accelerometer is found, tell user and update on-screen text
            Toast.makeText(Character_Select_Screen.this, "Accelerometer found", Toast.LENGTH_SHORT).show();
            toggleCharacterInfo.setText("Tap to change character. Shake for random.");
            //Assign reference to the sensor TYPE_ACCELEROMETER
            accelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            //Register the listener to accelerometer, reporting data back with DELAY_NORMAL
            mySensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else
            Toast.makeText(Character_Select_Screen.this, "Accelerometer not found", Toast.LENGTH_SHORT).show();

        //Confirm button
        Button confirmButton = (Button)findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent charScrnIntent = new Intent(Character_Select_Screen.this, MapSelect.class);
                        charScrnIntent.putExtra("CharacterImage", pics[i]);
                        startActivity(charScrnIntent);
                    }
                }
        );
    }

    //Characters change on single and double taps
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        characterToggle();
        return true;
    }

    //Characters change when the accelerometer is shaken
    @Override
    public void onSensorChanged(SensorEvent se){
        float x = se.values[0];
        float y = se.values[1];
        float z = se.values[2];
        mAccelLast = mAccelCurrent;
        mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
        float delta = mAccelCurrent - mAccelLast;
        mAccel = mAccel * 0.9f + delta; // perform low-cut filter

        if (mAccel > 12) {
            //Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_LONG);
            //toast.show();
            characterToggleRand();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    //Unregister the SensorManager to save battery etc. when paused
    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    //Register the SensorManager again when resumed
    @Override
    protected void onResume() {
        super.onPause();
        mySensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        characterToggle();
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    //Sets the picture and name of the next character
    public void characterToggle() {
        if(i < length-1)          i++;
        else if(i >= length-1)    i=0;

        characterName.setText(names[i]);
        characterPanel.setImageResource(pics[i]);
    }

    //Sets the picture and name to a random one in the series
    public void characterToggleRand() {
        i = (int)(Math.random() * length);
        characterName.setText(names[i]);
        characterPanel.setImageResource(pics[i]);
    }

    //VERY IMPORTANT TO MAKING GESTURES WORK
    //MUST OVERRRIDE THE SUPER CLASS METHOD onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
