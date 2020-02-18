package com.dcodestar.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    CountDownTimer ct;
    SeekBar seekBar;
    TextView textView;
    Button button;
    boolean running;
    int time=900;

    void seekBarChanged(int progress){
        int minutes=progress/60;
        int seconds=progress%60;
        StringBuilder timeLeft=new StringBuilder();
        if(minutes<=9){
            timeLeft.append(Integer.toString(0));
        }
        timeLeft.append(Integer.toString(minutes)+":");
        if(seconds<=9){
            timeLeft.append(Integer.toString(0));
        }
        timeLeft.append(Integer.toString(seconds));
        textView.setText(timeLeft);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar=findViewById(R.id.seekBar);
        textView=findViewById(R.id.textView);
        button=findViewById(R.id.button);

        seekBar.setMax(1800);
        seekBar.setProgress(time);
        seekBarChanged(time);
        button.setText("play");
        running =false;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarChanged(progress);
                time=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running) {
                    ct = new CountDownTimer(time * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            seekBarChanged((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            seekBar.setEnabled(true);
                            button.setText("play");
                            seekBar.setProgress(time);
                            seekBarChanged(time);
                            MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                            mediaPlayer.start();
                        }
                    };
                    ct.start();
                    seekBar.setEnabled(false);
                    button.setText("stop");
                }else{
                    ct.cancel();
                    seekBar.setEnabled(true);
                    button.setText("play");
                    seekBar.setProgress(time);
                    seekBarChanged(time);
                }
                running=!running;
            }
        });
    }
}
