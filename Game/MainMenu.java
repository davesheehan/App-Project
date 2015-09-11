package com.rainbowroad.amazement;

import android.app.AlertDialog;
import android.content.Intent;
//import android.content.res.AssetManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
//import android.widget.Toast;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

public class MainMenu extends ActionBarActivity {

    private AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Play Button
        Button playButton = (Button)findViewById(R.id.play_button);

        playButton.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainMenu.this, Character_Select_Screen.class));
                    }
                }
        );

        //Create a new dialog using dialog builder
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Your high scores");
        dialogBuilder.setMessage("1) " + "Cathal Moore: +1000\n2) " + "David Sheehan: 750\n" + "3) " + "Dwayne Johnson: 500");
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
        {
                @Override
                public void onClick(DialogInterface dialog, int which)
                    {
                        //Just want dialog box to close when clicked
                        //Add some code here if it is to do something
                    }
        });

        //Scores Button
        Button scoresButton = (Button)findViewById(R.id.scores_button);

        scoresButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //Show dialog
                        dialogBuilder.show();
                    }
                }
        );

        //Quit Button
        Button quitButton = (Button)findViewById(R.id.quit_button);
        quitButton.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        //Sends intent to Home Screen
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
        );
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
