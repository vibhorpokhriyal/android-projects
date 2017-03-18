package com.vibhor.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button buttonReset;
    TextView textViewTimer;
    TextView textViewProblem;
    TextView textViewScore;
    TextView textViewWinnerMessage;
    Button button0;
    Button button1;
    Button button2;
    Button button3;
    Button pressedButton;

    //Random generated (between 1 and 10) input values
    int firstValue = generateRandom(10);
    int secondValue = generateRandom(10);

    //Output values for 4 buttons
    int actualOut = firstValue + secondValue;               // Actual solution
    int secondOut = actualOut + generateRandom(10);
    int thirdOut = actualOut + generateRandom(10);
    int forthOut = actualOut + generateRandom(10);

    //Score Keeper
    int correctChoice = 0;
    int wrongChoice = 0;

    //Boolean gameIsActive
    Boolean gameIsActive = true;

    //get resource (button pressed) id
    int id;
    String ourId = "";

    //variable to switch output and compare with taggedValue to find the button pressed
    int switchDisplay;


    //generateProblem method is to be called to generate a new mathematical problem
    public void generateProblem(){
        //Inputs
        firstValue = generateRandom(10);
        secondValue = generateRandom(10);

        //Outputs
        actualOut = firstValue + secondValue;               // Actual solution
        secondOut = actualOut + generateRandom(10);
        thirdOut = actualOut + generateRandom(10);
        forthOut = actualOut + generateRandom(10);

        //Display
        textViewProblem.setText(String.valueOf(firstValue) + " + " + String.valueOf(secondValue));
        switchDisplay = generateRandom(4);
        Log.i("switchDisplay : ", String.valueOf(switchDisplay));
        if(switchDisplay == 1) {
            button0.setText(String.valueOf(actualOut));
            button1.setText(String.valueOf(secondOut));
            button2.setText(String.valueOf(thirdOut));
            button3.setText(String.valueOf(forthOut));
        } else if (switchDisplay == 2){
            button1.setText(String.valueOf(actualOut));
            button0.setText(String.valueOf(secondOut));
            button2.setText(String.valueOf(thirdOut));
            button3.setText(String.valueOf(forthOut));
        } else if(switchDisplay == 3){
            button2.setText(String.valueOf(actualOut));
            button0.setText(String.valueOf(secondOut));
            button1.setText(String.valueOf(thirdOut));
            button3.setText(String.valueOf(forthOut));
        } else{
            button3.setText(String.valueOf(actualOut));
            button0.setText(String.valueOf(secondOut));
            button1.setText(String.valueOf(thirdOut));
            button2.setText(String.valueOf(forthOut));
        }
    }

    public String getTime(long x){
        String hms = String.format("00:%02d", TimeUnit.MILLISECONDS.toSeconds(x) % TimeUnit.MINUTES.toSeconds(1));
        return hms;
    }

    public int generateRandom(int x){
        Random rn = new Random();
        int n = rn.nextInt(x) + 1;
        return n;
    }

    //Game crashes/hangs when button pressed : probable issue with do...While().
    public void buttonPressed(View view){
        id = view.getId();
        ourId = view.getResources().getResourceEntryName(id);
        Log.i("ourId: ", ourId);

        pressedButton = (Button) view;
            if(gameIsActive == true) {
                int taggedValue = Integer.parseInt(pressedButton.getTag().toString());
                Log.i("taggedValue", String.valueOf(taggedValue));
                Log.i("switchDisplay_bP", String.valueOf(switchDisplay));

                if (taggedValue == 0 && switchDisplay == 1 || taggedValue == 1 && switchDisplay == 2 || taggedValue == 2 && switchDisplay == 3 || taggedValue == 3 && switchDisplay == 4) {
                    correctChoice++;
                } else {
                    wrongChoice++;
                }
                textViewScore.setText(String.valueOf(correctChoice) + " / " + String.valueOf(wrongChoice));
                generateProblem();
            }
    }

    public void startGame(View view){
        gameIsActive = true;
        textViewWinnerMessage.setVisibility(View.INVISIBLE);
        //correctChoice = 0;
        //wrongChoice = 0;
        textViewScore.setText(String.valueOf(correctChoice) + " / " + String.valueOf(wrongChoice));
        buttonReset.setText("STOP");
        //textViewProblem.setText(String.valueOf(firstValue) + " + " + String.valueOf(secondValue));
        //button0.setText(String.valueOf(actualOut));
        //button1.setText(String.valueOf(secondOut));
        //button2.setText(String.valueOf(thirdOut));
        //button3.setText(String.valueOf(forthOut));
        generateProblem();


        new CountDownTimer(30000 + 100,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(getTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                textViewTimer.setText("00:00");
                gameIsActive = false;
                buttonReset.setText("PLAY AGAIN!");
                textViewWinnerMessage.setText("You Scored "+ correctChoice);
                textViewWinnerMessage.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonReset = (Button) findViewById(R.id.buttonReset);
        buttonReset.setText("START");
        textViewTimer = (TextView) findViewById(R.id.textViewTimer);
        textViewProblem = (TextView) findViewById(R.id.textViewProblem);
        textViewScore = (TextView) findViewById(R.id.textViewScore);
        textViewWinnerMessage = (TextView) findViewById(R.id.textViewWinnerMessage);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        textViewWinnerMessage.setVisibility(View.INVISIBLE);

    }
}
