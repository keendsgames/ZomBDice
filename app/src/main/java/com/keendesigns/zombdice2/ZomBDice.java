package com.keendesigns.zombdice2;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.CheckBox;

//Basic layout for ZomBDice starting page
//programmer: M Chan
//programme: ZomBDice.java
//created: Nov 20, 2016
//modified Nov 22, 2016
//main, rules and game template with passing player parameter to game page
public class ZomBDice extends Activity {
    Button brules,bgame,bexit;
    RadioGroup robotRadioGroup;
    CheckBox mute;
    int nosound=0;
    int robot;
    final int retCode=007;
    SoundPool ourSounds;
    int agogothi;
    int alienbti;
    SeekBar racer;
    int raceto = 5;
    TextView raceno;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        raceno = (TextView)  findViewById(R.id.racer);
        raceno.setText("Race to win set : "+raceto);
        initializeSoundPool();
        nosound=0;
        mute = (CheckBox) findViewById(R.id.mute);
        mute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //is chkIos checked?
                nosound=0;
                if (((CheckBox) v).isChecked()) {
                    nosound=1;
                }

            }
        });
        racer= (SeekBar)findViewById(R.id.seekBar);
        racer.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                raceto = progress;
                if (progress<5) raceto=5;
                System.out.println("race to win : "+raceto);
                raceno.setText("Race to win set : "+raceto);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(SeekbarActivity.this,"seek bar progress:"+progressChanged,
                        //Toast.LENGTH_SHORT).show();
            }
        });
        robotRadioGroup = (RadioGroup) findViewById(R.id.robotRadioGroup);
        RadioButton ckbutton = (RadioButton) findViewById(R.id.radioBrobot);
        ckbutton.setChecked(true);
        /*
        RadioButton selectRadio = (RadioButton) findViewById(robotRadioGroup.getCheckedRadioButtonId());
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot) robot=1;
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play) robot=0;
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot)
            System.out.println("checked 1 robot : "+robot);
        if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play)
            System.out.println("checked 0 players : "+robot);
            */
        // Locate the button in activity_main.xml
        brules = (Button) findViewById(R.id.buttonrules);
        // Capture button clicks
        brules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start Rules.class
                if (nosound==0) ourSounds.play (agogothi,0.9f,0.9f,1,0,1);
                Intent myIntent = new Intent(ZomBDice.this, Rules.class);
                startActivity(myIntent);
            }
        });

        // Locate the button in activity_main.xml
        bexit = (Button) findViewById(R.id.buttonexit);
        // Capture button clicks
        bexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start Rules.class
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
           }
        });

        bgame = (Button) findViewById(R.id.buttongame);
        // Capture button clicks
        bgame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start Rules.
                if (nosound==0) ourSounds.play (agogothi,0.9f,0.9f,1,0,1);
                RadioButton selectRadio = (RadioButton) findViewById(robotRadioGroup.getCheckedRadioButtonId());
                if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot) robot=1;
                if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play) robot=0;
                if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioBrobot)
                    System.out.println("checked 1 robot : "+robot);
                if (robotRadioGroup.getCheckedRadioButtonId()==R.id.radioB2play)
                    System.out.println("checked 0 players : "+robot);
                Intent ii = new Intent(ZomBDice.this,Game.class);
                Bundle bun = new Bundle();
                //robot=0;
                bun.putInt("robot",robot);
                //ii.putExtras(bun);
                bun.putInt("raceto",raceto);
                bun.putInt("nosound",nosound);
                ii.putExtras(bun);
                System.out.println("passing robot data : "+robot);
                System.out.println("passing raceto data : "+raceto);
                System.out.println("passing nosound data : "+nosound);
                startActivityForResult(ii,retCode);
               // startActivity(ii);
               //finish();
            }
        });

    }
    private void initializeSoundPool() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            ourSounds = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();
            agogothi = ourSounds.load(this, R.raw.agogothi, 1);
            alienbti = ourSounds.load(this, R.raw.alienbti, 1);
        } else {
            ourSounds = new SoundPool(2, AudioManager.STREAM_MUSIC, 1);
            agogothi = ourSounds.load(this, R.raw.agogothi, 1);
            alienbti = ourSounds.load(this, R.raw.alienbti, 1);
        }
    }//initializeSound method end
}