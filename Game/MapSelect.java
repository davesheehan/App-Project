package com.rainbowroad.amazement;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MapSelect extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_select);

        final Bundle charScrnIntentData = getIntent().getExtras();
        if(charScrnIntentData == null){return;}

        final ImageButton MapOneButton = (ImageButton)findViewById(R.id.MapOneImageButton);

        MapOneButton.setImageResource(charScrnIntentData.getInt("CharacterImage"));

        MapOneButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent mapScrnIntent1 = new Intent(MapSelect.this, MapOne.class);
                        mapScrnIntent1.putExtra("CharacterImage", charScrnIntentData.getInt("CharacterImage"));
                        startActivity(mapScrnIntent1);

                    }
                }
        );

        ImageButton MapTwoButton = (ImageButton)findViewById(R.id.MapTwoImageButton);

        MapTwoButton.setImageResource(charScrnIntentData.getInt("CharacterImage"));

        MapTwoButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent mapScrnIntent2 = new Intent(MapSelect.this, MapTwo.class);
                        mapScrnIntent2.putExtra("CharacterImage", charScrnIntentData.getInt("CharacterImage"));
                        startActivity(mapScrnIntent2);
                    }
                }
        );

        ImageButton MapThreeButton = (ImageButton)findViewById(R.id.MapThreeImageButton);

        MapThreeButton.setImageResource(charScrnIntentData.getInt("CharacterImage"));

        MapThreeButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent mapScrnIntent3 = new Intent(MapSelect.this, MapThree.class);
                        mapScrnIntent3.putExtra("CharacterImage", charScrnIntentData.getInt("CharacterImage"));
                        startActivity(mapScrnIntent3);
                    }
                }
        );
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
