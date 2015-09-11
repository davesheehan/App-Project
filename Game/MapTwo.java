package com.rainbowroad.amazement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MapTwo extends Activity implements View.OnClickListener {

    Button up, left, right, down;
    ImageView i1, i2,i3;
    TextView score;
    int i = 0,j = 0, n = 2000;
    float horInc, vertInc, pLastPos, g1LastPos,g2LastPos;
    boolean swapped = false;
    MediaPlayer clickSound,caughtSound,winSound;
    AudioManager am;

    Bundle mapScrnIntentData;
    Intent backToMainMenu;

    private AlertDialog.Builder endGameDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_two);

        //Buttons for each direction
        up = (Button) findViewById(R.id.up_button);
        left = (Button) findViewById(R.id.left_button);
        right = (Button) findViewById(R.id.right_button);
        down = (Button) findViewById(R.id.down_button);

        //Get the data sent with the intent from MapScreen ie.Player Image
        mapScrnIntentData = getIntent().getExtras();
        if(mapScrnIntentData == null){return;}

        backToMainMenu = new Intent(MapTwo.this, MainMenu.class);
        //Create new AlertDialog instance and set positive button to send intent back to main menu
        endGameDialog = new AlertDialog.Builder(this);
        endGameDialog.setPositiveButton("Back to Main Menu", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                startActivity(backToMainMenu);
            }
        });

        //Create character, two guards and score textview
        i1 = (ImageView) findViewById(R.id.mapOnePlayer);
        i2 = (ImageView) findViewById(R.id.mapOneGuardOne);
        i3 = (ImageView) findViewById(R.id.mapOneGuardTwo);
        score = (TextView)findViewById(R.id.score);
        score.setText("Score: " + n);

        i1.setImageResource(mapScrnIntentData.getInt("CharacterImage"));

        clickSound = MediaPlayer.create(this, R.raw.click_sound); //Create sounds for movement, win and loss
        caughtSound = MediaPlayer.create(this, R.raw.caught_sound);
        winSound = MediaPlayer.create(this, R.raw.win_sound);
        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE); //Used the check if phone is set to silent or vibrate

        up.setOnClickListener(this); //Creates listener for when the button is pressed
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);

        //Get display length and width and set a horizontal and vertical increment
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        vertInc=screenHeight/9;
        horInc=screenWidth/5;

        //Set all character initial positions
        i1.setX(20);            i1.setY(20);

        i2.setX(20+(horInc*4)); i2.setY(20+(vertInc*2));

        i3.setX(20+(horInc*1)); i3.setY(20+(vertInc*6));

        //Call checkButtons to see which buttons should be active
        checkButtons();
    }//End of onCreate method

    public void checkButtons() {
        float addedPos = i1.getX() + i1.getY();

        float[][] pos = new float[][]{
                {40, 40 + horInc, 40 + horInc * 2, 40 + horInc * 3, 0},  //Line 0
                {0, 0, 0, 40 + horInc * 3 + vertInc, 0},  //Line 1
                {0, 0, 0, 40 + horInc * 3 + vertInc * 2, 40 + horInc * 4 + vertInc * 2},    //Line 2
                {0, 0, 0, 40 + horInc * 3 + vertInc * 3, 40 + horInc * 4 + vertInc * 3},    //Line 3
                {40 + vertInc * 4, 40 + horInc + vertInc * 4, 40 + horInc * 2 + vertInc * 4, 40 + horInc * 3 + vertInc * 4, 40 + horInc * 4 + vertInc * 4},    //Line 4
                {0, 40 + horInc + vertInc * 5, 40 + horInc * 2 + vertInc * 5, 0, 0},    //Line 5
                {0, 40 + horInc + vertInc * 6, 40 + horInc * 2 + vertInc * 6, 40 + horInc * 3 + vertInc * 6, 0},  //Line 6
               // {0, 0, 40 + horInc * 2 + vertInc * 7, 40 + horInc * 3 + vertInc * 7, 0},//Line 7
                {0, 0, 40 + horInc * 2 + vertInc * 7, 0, 0},//Line 7
                {0, 0, 0, 0, 0},//Line 8
        };
//Singles
        if (addedPos == pos[0][0] || addedPos == pos[4][0] || addedPos == pos[6][1]) {
            right.setVisibility(View.VISIBLE);

            left.setVisibility(View.INVISIBLE);
            down.setVisibility(View.INVISIBLE);
            up.setVisibility(View.INVISIBLE);
        }

//Doubles
        else if (addedPos == pos[1][3] || addedPos == pos[3][3] || addedPos == pos[3][4]) {
            down.setVisibility(View.VISIBLE);
            up.setVisibility(View.VISIBLE);

            right.setVisibility(View.INVISIBLE);
            left.setVisibility(View.INVISIBLE);
        } else if (addedPos == pos[0][1] || addedPos == pos[0][2] || addedPos == pos[4][2]) {
            right.setVisibility(View.VISIBLE);
            left.setVisibility(View.VISIBLE);

            down.setVisibility(View.INVISIBLE);
            up.setVisibility(View.INVISIBLE);
        } else if (addedPos == pos[0][3] || addedPos == pos[2][4] || addedPos == pos[5][2] || addedPos == pos[6][3]) {
            down.setVisibility(View.VISIBLE);
            left.setVisibility(View.VISIBLE);

            right.setVisibility(View.INVISIBLE);
            up.setVisibility(View.INVISIBLE);
        } else if (addedPos == pos[4][4] || addedPos == pos[7][3]) {
            left.setVisibility(View.VISIBLE);
            up.setVisibility(View.VISIBLE);

            down.setVisibility(View.INVISIBLE);
            right.setVisibility(View.INVISIBLE);
        } else if (addedPos == pos[5][1]) {
            right.setVisibility(View.VISIBLE);
            up.setVisibility(View.VISIBLE);

            down.setVisibility(View.INVISIBLE);
            left.setVisibility(View.INVISIBLE);
        }
//Triples
        else if (addedPos == pos[6][2] || addedPos == pos[4][3]) {
            right.setVisibility(View.VISIBLE);
            up.setVisibility(View.VISIBLE);
            left.setVisibility(View.VISIBLE);

            down.setVisibility(View.INVISIBLE);
        } else if (addedPos == pos[2][3]) {
            right.setVisibility(View.VISIBLE);
            down.setVisibility(View.VISIBLE);
            up.setVisibility(View.VISIBLE);

            left.setVisibility(View.INVISIBLE);
        }
        else if (addedPos == pos[4][1]) {
            right.setVisibility(View.VISIBLE);
            down.setVisibility(View.VISIBLE);
            left.setVisibility(View.VISIBLE);

            up.setVisibility(View.INVISIBLE);

        }
        }//End of checkButtons method



    //Updates the score
    public void updateScore() {
        if(n>0)n-=50;
        else n=0;
        score.setText("Score: "+ n);
    }//End of updateScore method

    public void onClick(View v) {
        enemyMove();
        updateScore();

        switch (v.getId()) {
            case R.id.up_button: {
                i1.setY(i1.getY()-vertInc);
                caught();
                checkButtons();
                if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) //Checks that phone isn't on silent or vibrate
                    clickSound.start(); //Plays movement sound
            }
            break;

            case R.id.down_button: {
                i1.setY(i1.getY() + vertInc);
                caught();
                checkButtons();
                if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                    clickSound.start();
            }
            break;

            case R.id.left_button: {
                i1.setX(i1.getX()-horInc);
                caught();
                checkButtons();
                if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                    clickSound.start();
            }
            break;

            case R.id.right_button: {
                i1.setX(i1.getX()+horInc);
                caught();
                checkButtons();
                if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                    clickSound.start();
            }
            break;
        }//End of switch case
    }//End of onClick method

    public boolean checkIfSwapped(){
        if(pLastPos==i2.getX()+i2.getY() && g1LastPos==i1.getX()+i1.getY() || pLastPos==i3.getX()+i3.getY()&& g1LastPos==i1.getX()+i1.getY())
            swapped=true;
        return swapped;
    }

    public void caught(){
        swapped=checkIfSwapped();
        //If player is on same position as a guard or they "ran into each other" ie.swapped positions
        if(i1.getX() ==i2.getX()&& i1.getY() == i2.getY() || i1.getX() ==i3.getX()&& i1.getY() == i3.getY()
                || swapped)
        {
            //Create the dialog to show when the player is caught
            endGameDialog.setTitle("CAUGHT!");
            endGameDialog.setMessage("You were caught by the guards!\nYour Score: "+0);
            endGameDialog.show();

            if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                caughtSound.start();
        }

        //If player lands on finish zone
        if(i1.getX() + i1.getY() == 40+ vertInc*7 + horInc*2)
        {
            //Create the dialog to show when the player is caught
            endGameDialog.setTitle("CONGRATULATIONS!");
            if(n>500)   endGameDialog.setMessage("You escaped the guards!\nYou also beat one of our high scores!\nYour Score: "+n);
            else        endGameDialog.setMessage("You escaped the guards!\nYour Score: "+n);
            endGameDialog.show();

            if(am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL)
                winSound.start();
        }

        pLastPos=i1.getX()+i1.getY();
        g1LastPos=i2.getX()+i2.getY();
        g2LastPos=i3.getX()+i3.getY();
    }//End of caught method

    public void enemyMove() {
        //array stores the guards patrol pattern
        int[] guard1 = {3, 3, 0, 2, 2, 1};
        int[] guard2 = {1, 2, 0, 2, 0, 1, 3, 1, 3, 0};
        //0=left, 1=right, 2=up, 3=down

        switch (guard1[i]) {
            case 0:     i2.setX(i2.getX()-horInc);      break;
            case 1:     i2.setX(i2.getX()+horInc);      break;
            case 2:     i2.setY(i2.getY() - vertInc);   break;
            case 3:     i2.setY(i2.getY()+vertInc);     break;
        }

        switch (guard2[j]) {
            case 0:     i3.setX(i3.getX()-horInc);      break;
            case 1:     i3.setX(i3.getX()+horInc);      break;
            case 2:     i3.setY(i3.getY()-vertInc);     break;
            case 3:     i3.setY(i3.getY()+vertInc);     break;
        }

        i++;
        j++;
        if(i == 6) i=0;
        if(j == 10) j=0;//reset movement array
    }//End of enemyMove method
} //End of MapTwo.class