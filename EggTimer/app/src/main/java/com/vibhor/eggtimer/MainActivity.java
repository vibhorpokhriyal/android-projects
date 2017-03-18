package com.vibhor.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView textViewClock; //Declare clock textView.
    SeekBar seekBar;        //Declare seekBar.
    Button buttonStart;
    int trackProgress;
    MediaPlayer rooster;   //Declare rooster sound.
    Boolean counterIsActive = false;

    public String getTime(long x){
        String hms = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(x) % TimeUnit.HOURS.toMinutes(1), TimeUnit.MILLISECONDS.toSeconds(x) % TimeUnit.MINUTES.toSeconds(1));
        return hms;
    }

    public void timerFunction(View view){
        //seekBar.setVisibility(View.INVISIBLE);
        seekBar.setEnabled(false);
        buttonStart.setText("STOP");
        /*Countdown timer
        //set total time as per slider value i.e., trackProgress (in milliseconds)
        //step value is 1000 milliseconds i.e., 1 second.
        */
        new CountDownTimer(trackProgress + 100, 1000){
            public void onTick(long ticker){
                textViewClock.setText(getTime(ticker));
            }
            public void onFinish(){
                rooster.start();
                buttonStart.setText("GO");
                //seekBar.setVisibility(View.VISIBLE);
                seekBar.setEnabled(true);
                textViewClock.setText("00:00");
                seekBar.setProgress(000000);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rooster = MediaPlayer.create(this, R.raw.rooster); //Set rooster sound.
        seekBar = (SeekBar) findViewById(R.id.seekBar); // Set slider bar.
        buttonStart = (Button) findViewById(R.id.buttonStart);
        textViewClock = (TextView) findViewById(R.id.textViewClock); //Set digital clock display.
        buttonStart.setText("GO");
        seekBar.setMax(600000);
        seekBar.setProgress(300000);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trackProgress = progress;
                int min = 0;
                int sliderValue;
                if (progress < 1){
                    sliderValue = min;
                    seekBar.setProgress(sliderValue);
                } else {
                    sliderValue = progress;
                }
                textViewClock.setText(getTime(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
