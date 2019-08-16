package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mediaplayer;
    private ImageView imageView;
    private TextView right;
    private TextView left;
    private Button prevbtn;
    private Button playbtn;
    private Button nextbtn;
    private SeekBar seekBar;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        int duration = mediaplayer.getDuration();
        String mDuraton = String.valueOf(duration);
        seekBar.setMax(duration);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaplayer.seekTo(i);
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                int currpos = mediaplayer.getCurrentPosition();
                int duration = mediaplayer.getDuration();
                left.setText(dateFormat.format(currpos));
                right.setText(dateFormat.format(duration - currpos));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void setupUI() {
        imageView = findViewById(R.id.imageView3);
        right = findViewById(R.id.righTime);
        left = findViewById(R.id.left_time);
        prevbtn = findViewById(R.id.prevBtn);
        playbtn = findViewById(R.id.playBtn);
        nextbtn = findViewById(R.id.nextBtn);
        seekBar = findViewById(R.id.seekBar);
        mediaplayer = new MediaPlayer();
        mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.saansein);

        prevbtn.setOnClickListener(this);
        playbtn.setOnClickListener(this);
        nextbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.prevBtn:

                break;

            case R.id.playBtn:
                if (mediaplayer.isPlaying())
                    pauseMusic();
                else
                    startMusic();


                break;

            case R.id.nextBtn:

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void pauseMusic() {
        if (mediaplayer != null) {
            mediaplayer.pause();
            playbtn.setBackgroundResource(android.R.drawable.ic_media_play);
        }

    }

    public void startMusic() {
        if (mediaplayer != null) {
            mediaplayer.start();
            updateThread();
            playbtn.setBackgroundResource(android.R.drawable.ic_media_pause);

        }
    }

    public void updateThread() {

        thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try

                {
                    while (mediaplayer != null && mediaplayer.isPlaying()) {


                        Thread.sleep(50);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int newPosition = mediaplayer.getCurrentPosition();
                                int newDuration = mediaplayer.getDuration();
                                seekBar.setMax(newDuration);
                                seekBar.setProgress(newPosition);

                                left.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss").format(new Date(mediaplayer.getCurrentPosition()))));
                                right.setText(String.valueOf(new java.text.SimpleDateFormat("mm:ss").format(new Date(mediaplayer.getDuration() - mediaplayer.getCurrentPosition()))));

                            }
                        });
                    }

                }
                catch(
                        InterruptedException e)

                {
                    e.printStackTrace();

                }
            }
        };
        thread.start();
    }

}