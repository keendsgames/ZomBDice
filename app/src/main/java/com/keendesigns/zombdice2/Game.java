package com.keendesigns.zombdice2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import java.util.*;
import java.util.Random;
import android.media.AudioManager;


/**
 * Created by mic on 2016-11-27.
 * adapted from ZombDiceSaw.java
 * modified 2016-12-07
 * all imageviews and textviews variable ready to be updated by routines
 * draw,roll,again and pass worked
 * challenge questions worked
 * sound added but not working
 * icon added
 * all graphics scaled down and no more crashes
 * soundpool effects worked
 * added seekbar and theory pages
 * race to win and mute options added
 */
/**
 * @author Michael Chan
//dice number notation     G   Y   R
//                  roll:  1   2   3
//                         4   5   6
//                         7   8   9
//                        10  11  12
//                     brain feet blast
//java.util.List<Integer> diceStr = new java.util.ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,2,2,2,2,3,3,3));
//List<Integer> grnStr = new ArrayList<Integer>(Arrays.asList(4,4,4,5,5,6));
//List<Integer> yelStr = new ArrayList<Integer>(Arrays.asList(7,7,8,8,9,9));
//List<Integer> redStr = new ArrayList<Integer>(Arrays.asList(10,11,11,12,12,12));
//	String[] diepic = {"nodice.png","greendice.png","yellowdice.png","reddice.png","greenbrain.png","greenblast1.png","greenfeet.png",
//	"yellowbrain.png","yellowblast1.png","yellowfeet.png","redbrain.png","redblast1.png","redfeet.png"};
die1,die2,die3
stageNo=0 main page
stageNo=1 instruction page
stageNo=2 New game page
turnover=0   draw die colors
turnover=1   roll dice
turnover=2   play again/pass
turnover=3   turnover and pass
 */
public class Game extends Activity {
    ZomBDice ob;
    int robot=1;
    ImageView player1img,player2img,die1img,die2img,die3img;
    ImageView bonus1img,bonus2img;
    ImageView q1img,q2img,q3img,calmimg;
    TextView score1t,score2t;
    TextView mquest1a,mquest1b;
    TextView promptmsgt;
    TextView gbrnt,ybrnt,rbrnt,gblnt,yblnt,rblnt;
    String prompt="";
    String mq1,mq2;
    Double inpnum;

    int stageNo=0;
    int turnover=0;
    int blastn,brainn,gameover;
    int player=0;
    int score1,score2;
    int die1,die2,die3;
    int yblastn,ybrainn,gblastn,gbrainn,rbrainn,rblastn;

    java.util.List <Integer>randStr;
    java.util.List <Integer>randStrbk;
    int questno=0;
    int qmsgon=0;
    //int robot=0; //0=2 players; 1=computer opponent
    //challenge question parameter
    int nr,ng,ny;
    float probnogrn[];
    int drawnr,drawng,drawny;
    float probcolorcombo;
    float probroll;
    float rollo[];
    float probnoblast[];
    int round,bonusadd,bonussub,addflag,subflag;
    float answer;
    int aq[];
    int randq1;
    int diepic[]= {R.drawable.nodice,
            R.drawable.greendice1,
            R.drawable.yellowdice1,
            R.drawable.reddice1,
            R.drawable.greenbrain,
            R.drawable.greenfeet,
            R.drawable.greenblast1,
            R.drawable.yellowbrain,
            R.drawable.yellowfeet,
            R.drawable.yellowblast1,
            R.drawable.redbrain,
            R.drawable.redfeet,
            R.drawable.redblast1
              };
    int bonus[] = {R.drawable.nodice,R.drawable.brain12,R.drawable.brain1,R.drawable.hand};
    int imgid;

    //MediaPlayer mp;
    //AudioManager audioManager;
    SoundPool ourSounds;
    int agogothi;
    int alienbti;
    int gunshot_3;
    int tada;
    int uhoh;
    int zombie;
    int raceto=5;
    int nosound;

    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        initializeSoundPool();
        //audioManager = (AudioManager)  getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,20,0);

        Intent ii = getIntent();
        robot = ii.getIntExtra("robot",-1);
        System.out.println("Game getting robot from main : "+robot);
        raceto = ii.getIntExtra("raceto",-1);
        System.out.println("Game getting raceto from main : "+raceto);
        nosound = ii.getIntExtra("nosound",-1);
        System.out.println("Game getting nosound from main : "+nosound);
        stageNo=2;
/*
        prompt="Press draw button to play";
        promptmsgt = (TextView)  findViewById(R.id.prompt);
        promptmsgt.setText(prompt);
        mq1="";
        mq2="";
        mquest1a = (TextView)  findViewById(R.id.mquest1a);
        mquest1a.setText(mq1);
        mquest1b = (TextView)  findViewById(R.id.mquest1b);
        mquest1b.setText(mq2);
        score1t = (TextView)  findViewById(R.id.score1);
        score1t.setText(""+score1);
        score2t = (TextView)  findViewById(R.id.score2);
        score2t.setText(""+score2);


        bonus1img = (ImageView) findViewById(R.id.bonus1);
        bonus1img.setImageResource(R.drawable.calculator);
        bonus1img.setVisibility(View.INVISIBLE);
        bonus2img = (ImageView) findViewById(R.id.bonus2);
        bonus2img.setImageResource(R.drawable.calculator);
        bonus2img.setVisibility(View.INVISIBLE);
        q1img = (ImageView) findViewById(R.id.q1);
        q1img.setImageResource(R.drawable.calculator);
        q1img.setVisibility(View.INVISIBLE);
        q2img = (ImageView) findViewById(R.id.q2);
        q2img.setImageResource(R.drawable.calculator);
        q2img.setVisibility(View.INVISIBLE);
        q3img = (ImageView) findViewById(R.id.q3);
        q3img.setImageResource(R.drawable.calculator);
        q3img.setVisibility(View.INVISIBLE);
        calmimg = (ImageView) findViewById(R.id.calm);
        calmimg.setImageResource(R.drawable.calculator);
        calmimg .setVisibility(View.INVISIBLE);

        gbrnt = (TextView)  findViewById(R.id.gbrainn);
        gbrnt.setText(""+gbrainn);
        ybrnt = (TextView)  findViewById(R.id.ybrainn);
        ybrnt.setText(""+ybrainn);
        rbrnt = (TextView)  findViewById(R.id.rbrainn);
        rbrnt.setText(""+rbrainn);

        gblnt = (TextView)  findViewById(R.id.gblastn);
        gblnt.setText(""+gblastn);
        yblnt = (TextView)  findViewById(R.id.yblastn);
        yblnt.setText(""+yblastn);
        rblnt = (TextView)  findViewById(R.id.rblastn);
        rblnt.setText(""+rblastn);

        player1img = (ImageView)  findViewById(R.id.player1);
        player1img.setImageResource(R.drawable.zombgirlc);
        imgid = R.drawable.zombrobot;
        if (robot!=1) imgid = R.drawable.zombmanc;
        player2img = (ImageView)  findViewById(R.id.player2);
        player2img.setImageResource(imgid);
        //die1=die2=die3=2;
        die1img = (ImageView) findViewById(R.id.die1);
        die1img.setImageResource(diepic[0]);
        die2img = (ImageView) findViewById(R.id.die2);
        die2img.setImageResource(diepic[0]);
        die3img = (ImageView) findViewById(R.id.die3);
        die3img.setImageResource(diepic[0]);
*/

        initialize();

        // Locate the button in activity_main.xml
        Button menub = (Button) findViewById(R.id.menub);
        // Capture button clicks

        menub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (nosound==0) ourSounds.play (agogothi,0.9f,0.9f,1,0,1);
                System.out.println("Back to menu button clicked ! ");
                //managerOfSound(1);
                Intent i = getIntent();
                setResult(RESULT_OK,i);
                finish();
            }
        });
        Button questb = (Button) findViewById(R.id.questb);
        // Capture button clicks
        questb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {if ((qmsgon==0 && aq[0]>-1) || (qmsgon==0 && turnover>1)) {
                addflag=1;
                System.out.println("Math quest button clicked ! ");
                if (nosound==0) ourSounds.play (alienbti,0.9f,0.9f,1,0,1);
                //managerOfSound(1);
                if ((turnover!=1 && turnover<3)||(turnover==1 && bonusadd>0)) {
                    //SoundClipTest("ding.wav");
                    questno++;
                    switch (turnover){
                        case 2:
                            mq1="What is the probability of ";
                            mq2="getting dice outcome set?";
                            Q4();
                            break;
                        case 1:
                            if (bonusadd==1) {
                                Q2();
                                mq1="What is the probability of ";
                                mq2="getting drawn color combo?";
                            }
                            break;
                        case 0:
                            randq1=new Random().nextInt(4);
                            mq1="What is the probability of ";
                            mq2="drawing "+randq1+" green dice?";
                            Q1();
                            break;
                    }//switch case end
                    mquest1a.setText(mq1);
                    mquest1b.setText(mq2);

                    qmsgon=1;

                    System.out.println("mathquest clicked");
                    System.out.println("mathquest answer : "+answer);

 // get prompt.xml view
				LayoutInflater li = LayoutInflater.from(Game.this);
				View promptsView = li.inflate(R.layout.prompt, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Game.this);

				// set prompt.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
						//result.setText(userInput.getText());
                            String instr= userInput.getText().toString();
                            System.out.println("input text : "+instr);
						inpnum= Double.parseDouble(userInput.getText().toString());
                            System.out.println("mathquest input : "+inpnum);

                            if (inpnum<answer*1.05 && inpnum>answer*0.95) {
                        if (turnover==0) aq[0]=1;
                        if (turnover==1) aq[1]=1;
                        if (turnover>1) aq[2]=1;
                        System.out.println(" answer correct! ");
                       // SoundClipTest("alienbti.wav")
                                //managerOfSound(2);
                                if (nosound==0) ourSounds.play (alienbti,0.9f,0.9f,1,0,1);
                        if (turnover<2) bonusadd++;
                        else bonussub=-1;
                        System.out.println(" bonusadd :  "+bonusadd);
                    }
                    else {
                        System.out.println(" answer wrong! ");
                        //SoundClipTest("uhoh.wav");
                                //managerOfSound(3);
                                if (nosound==0) ourSounds.play (uhoh,0.9f,0.9f,1,0,1);
                        if (turnover==0) aq[0]=-1;
                        if (turnover==1) aq[1]=-1;
                        if (turnover>1) aq[2]=-1;
                    }
                    //q1img = (I
                            // mageView) findViewById(R.id.q1);
                    if (aq[0]<0) q1img.setImageResource(R.drawable.cross);
                    if (aq[0]>0) q1img.setImageResource(R.drawable.tick);
                    //q2img = (ImageView) findViewById(R.id.q2);
                    if (aq[1]<0) q2img.setImageResource(R.drawable.cross);
                    if (aq[1]>0) q2img.setImageResource(R.drawable.tick);
                    //q3img = (ImageView) findViewById(R.id.q3);
                    if (aq[2]<0) q3img.setImageResource(R.drawable.cross);
                    if (aq[2]>0) q3img.setImageResource(R.drawable.tick);
                    //calmimg = (ImageView) findViewById(R.id.calm);
                    //calmimg.setImageResource(R.drawable.calculator);
                    if (aq[0]!=0) q1img .setVisibility(View.VISIBLE);
                    if (aq[1]!=0)q2img .setVisibility(View.VISIBLE);
                    if (aq[2]!=0)q3img .setVisibility(View.VISIBLE);
                    calmimg .setVisibility(View.VISIBLE);
                    if (bonusadd!=0 || bonussub!=0) {
                        bonus1img.setVisibility(View.VISIBLE);
                        bonus2img.setVisibility(View.VISIBLE);

                        if (player == 1) {
                            imgid = bonus[0];
                            if (bonusadd == 1) imgid = bonus[1];
                            if (bonusadd == 2) imgid = bonus[2];
                            bonus1img.setImageResource(imgid);
                            imgid = bonus[0];
                            if (bonussub == -1) imgid = bonus[3];
                            bonus2img.setImageResource(imgid);
                        }//if player==1 end
                        if (player == 2) {
                            imgid = bonus[0];
                            if (bonusadd == 1) imgid = bonus[1];
                            if (bonusadd == 2) imgid = bonus[2];
                            bonus2img.setImageResource(imgid);
                            imgid = bonus[0];
                            if (bonussub == -1) imgid = bonus[3];
                            bonus1img.setImageResource(imgid);
                        }//if player==2 end

                    } //if bonusadd!=0 end

                    if (bonusadd==2) System.out.println(" Bonus: score added 1 point! ");
                    if (bonussub==-1) System.out.println(" Bonus: opp score subtracted 1 point! ");
                    addflag=0;

					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();


                 //   Double inpnum = Double.parseDouble(JOptionPane.showInputDialog("Your Answer "+": "));
                    //testScore[i] = Integer.parseInt(JOptionPane.showInputDialog("Please input mark for test " + i + ": "));
                    //System.out.println("answer should be : "+answer);

            //inpnum= (double) answer;

                }//if turnover!=1... end

            } //if qmsgon==0 end

            }//OnclickListener end
        });
        Button drawb = (Button) findViewById(R.id.drawbut);
        // Capture button clicks
        drawb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover == 0 && addflag==0) {
                    System.out.println("Draw button clicked ! ");
                    //managerOfSound(1);
                    if (nosound==0) ourSounds.play (agogothi,0.9f,0.9f,1,0,1);
                    draw();
                    die1img.setImageResource(diepic[die1]);
                    die2img.setImageResource(diepic[die2]);
                    die3img.setImageResource(diepic[die3]);
                    //qmsgon=0;
                }//if turnover=0 end
            }
        });

        Button rollb = (Button) findViewById(R.id.rollbut);
        // Capture button clicks
        rollb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover==1 && addflag==0) {
                    System.out.println("Roll button clicked ! ");
                    //managerOfSound(1);
                    if (nosound==0) ourSounds.play (agogothi,0.9f,0.9f,1,0,1);
                    roll();
                    die1img.setImageResource(diepic[die1]);
                    die2img.setImageResource(diepic[die2]);
                    die3img.setImageResource(diepic[die3]);

                    prompt = "Pass or Play Again.";
                    System.out.println("message: " + prompt);
                    checkblast();

                    gbrnt.setText("" + gbrainn);
                    ybrnt.setText("" + ybrainn);
                    rbrnt.setText("" + rbrainn);
                    gblnt.setText("" + gblastn);
                    yblnt.setText("" + yblastn);
                    rblnt.setText("" + rblastn);

                    if (nosound==0) {
                        if (brainn > 0 && die1 % 3 != 0 && die2 % 3 != 0 && die3 % 3 != 0)
                            ourSounds.play(zombie, 0.9f, 0.9f, 1, 0, 1);
                        if (die1 % 3 == 0 || die2 % 3 == 0 || die3 % 3 == 0)
                            ourSounds.play(gunshot_3, 0.9f, 0.9f, 1, 0, 1);
                    }
                    if (turnover == 3)
                        prompt = "Turn over, Pass only.";
                    promptmsgt.setText(prompt);
                } //if turnover=1 end

            }
        });//roll end
        Button againb = (Button) findViewById(R.id.againbut);
        // Capture button clicks
        againb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover==2) {
                    System.out.println("Again button clicked ! ");
                    //managerOfSound(1);
                   // SoundClipTest("agogothi.wav");
                    if (nosound==0) ourSounds.play (agogothi,0.9f,0.9f,1,0,1);
                    randStr.remove(randStr.size()-1);
                    randStr.remove(randStr.size()-1);
                    randStr.remove(randStr.size()-1);
                    System.out.println("randstr after draw : "+randStr);
                    if (die1==5) randStr.add(1);
                    if (die1==8) randStr.add(2);
                    if (die1==11) randStr.add(3);
                    if (die2==5) randStr.add(1);
                    if (die2==8) randStr.add(2);
                    if (die2==11) randStr.add(3);
                    if (die3==5) randStr.add(1);
                    if (die3==8) randStr.add(2);
                    if (die3==11) randStr.add(3);
                    prompt= "Draw dice to continue.";
                    promptmsgt.setText(prompt);
                    System.out.println("message: "+prompt);
                    //qmsg1=qmsg2="";

                    if (player==1) {
                        if (bonusadd==2) score1=score1+1;
                        if (bonussub==-1 && score2>0) score2=score2-1;
                    }
                    else {
                        if (bonusadd==2) score2=score2+1;
                        if (bonussub==-1 && score1>0) score1=score1-1;			}

                    score1t.setText(""+score1);
                    score2t.setText(""+score2);

                    bonusadd=bonussub=0;
                    turnover=0;
                    aq[0]=aq[1]=aq[2]=0;
                    bonus1img.setVisibility(View.INVISIBLE);
                    bonus2img.setVisibility(View.INVISIBLE);
                    q1img.setVisibility(View.INVISIBLE);
                    q2img.setVisibility(View.INVISIBLE);
                    q3img.setVisibility(View.INVISIBLE);
                    calmimg .setVisibility(View.INVISIBLE);
                    qmsgon=0;
                }//if turnover=2 end
            }
        });//again end
        Button passb = (Button) findViewById(R.id.passbut);
        // Capture button clicks
        passb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (turnover>1 && gameover!=1) {
                    System.out.println("Pass button clicked ! ");
                    //managerOfSound(1);
                   // SoundClipTest("agogothi.wav");
                    if (nosound==0) ourSounds.play (agogothi,0.9f,0.9f,1,0,1);
                    if (gameover==1) stageNo=0;
                    if (player==1) {
                        if (blastn<3) score1=score1+brainn;
                        if (bonusadd==2) score1=score1+1;
                        if (bonussub==-1 && score2>0) score2=score2-1;
                        System.out.println("Player 1 score update: "+score1);
                    }

                    if (player==2) {
                        if (blastn<3) score2=score2+brainn;
                        if (bonusadd==2) score2=score2+1;
                        if (bonussub==-1 && score1>0) score1=score1-1;
                        System.out.println("Player2 score update:  "+score2);
                    }
                    score1t.setText(""+score1);
                    score2t.setText(""+score2);
                    if (score1<raceto && score2<raceto) {
                        resetdata();
                        System.out.println("Turn over and switch player");
                        //prompt= "Press draw button to play.";
                    } //if scores not over 13
                    else {
                        imgid = R.drawable.tombgameover;

                        if (score1>(raceto-1)) {
                            prompt= "Player 1 won. Menu.";
                            player2img.setImageResource(imgid);
                        }
                        if (score2>(raceto-1)) {
                            prompt= "Player 2 won. Menu.";
                            player1img.setImageResource(imgid);

                        }
                        //SoundClipTest("tada.wav");
                        //managerOfSound(6);
                        if (nosound==0) ourSounds.play (tada,0.9f,0.9f,1,0,1);
                        turnover=9;
                        gameover=1;
                        System.out.println("Game Over !");
                    }
                    promptmsgt.setText(prompt);
                    System.out.println("message: "+prompt);
                    //qmsg1=qmsg2="";
                    qmsgon=0;
                    bonusadd=bonussub=0;
                }
            }
        });

    }
    //  ---------------------------------------------------------------
    private void resetdata() {
        //mclicked=false;
        //checked=false;
        turnover=0;
        die1=die2=die3=0;
        blastn=brainn=0;
        gblastn=yblastn=rblastn=gbrainn=ybrainn=rbrainn=0;
        player= player%2 + 1;
        bonusadd=bonussub=addflag=subflag=0;


        System.out.println("player : "+player);
        System.out.println("turnover "+turnover+" blastn "+blastn+" brainn "+brainn);

        java.util.List<Integer> diceStr = new java.util.ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,2,2,2,2,3,3,3));
        randStr = new java.util.ArrayList<Integer>(diceStr);
        Collections.shuffle(randStr);
        System.out.println("ramdomize dice : "+randStr);
        randStrbk=randStr;
        System.out.println("length is : "+randStr.size());
        //qmsg1=qmsg2="";

        questno=0;
        qmsgon=0;
        bonusadd=bonussub=0;
        aq[0]=aq[1]=aq[2]=0;

        prompt="Press draw button to play";
        promptmsgt = (TextView)  findViewById(R.id.prompt);
        promptmsgt.setText(prompt);
        mq1="";
        mq2="";
        mquest1a = (TextView)  findViewById(R.id.mquest1a);
        mquest1a.setText(mq1);
        mquest1b = (TextView)  findViewById(R.id.mquest1b);
        mquest1b.setText(mq2);
        score1t = (TextView)  findViewById(R.id.score1);
        score1t.setText(""+score1);
        score2t = (TextView)  findViewById(R.id.score2);
        score2t.setText(""+score2);


        bonus1img = (ImageView) findViewById(R.id.bonus1);
        bonus1img.setImageResource(R.drawable.calculator);
        bonus1img.setVisibility(View.INVISIBLE);
        bonus2img = (ImageView) findViewById(R.id.bonus2);
        bonus2img.setImageResource(R.drawable.calculator);
        bonus2img.setVisibility(View.INVISIBLE);
        q1img = (ImageView) findViewById(R.id.q1);
        q1img.setImageResource(R.drawable.calculator);
        q1img.setVisibility(View.INVISIBLE);
        q2img = (ImageView) findViewById(R.id.q2);
        q2img.setImageResource(R.drawable.calculator);
        q2img.setVisibility(View.INVISIBLE);
        q3img = (ImageView) findViewById(R.id.q3);
        q3img.setImageResource(R.drawable.calculator);
        q3img.setVisibility(View.INVISIBLE);
        calmimg = (ImageView) findViewById(R.id.calm);
        calmimg.setImageResource(R.drawable.calculator);
        calmimg .setVisibility(View.INVISIBLE);

        gbrnt = (TextView)  findViewById(R.id.gbrainn);
        gbrnt.setText(""+gbrainn);
        ybrnt = (TextView)  findViewById(R.id.ybrainn);
        ybrnt.setText(""+ybrainn);
        rbrnt = (TextView)  findViewById(R.id.rbrainn);
        rbrnt.setText(""+rbrainn);

        gblnt = (TextView)  findViewById(R.id.gblastn);
        gblnt.setText(""+gblastn);
        yblnt = (TextView)  findViewById(R.id.yblastn);
        yblnt.setText(""+yblastn);
        rblnt = (TextView)  findViewById(R.id.rblastn);
        rblnt.setText(""+rblastn);

        player1img = (ImageView)  findViewById(R.id.player1);
        player1img.setImageResource(R.drawable.zombgirlc);
        imgid = R.drawable.zombrobot;
        if (robot!=1) imgid = R.drawable.zombmanc;
        player2img = (ImageView)  findViewById(R.id.player2);
        player2img.setImageResource(imgid);
        die1=die2=die3=2;
        die1img = (ImageView) findViewById(R.id.die1);
        int die11 = diepic[die1];
        die1img.setImageResource(diepic[die1]);
        die2img = (ImageView) findViewById(R.id.die2);
        die2img.setImageResource(diepic[die2]);
        die3img = (ImageView) findViewById(R.id.die3);
        die3img.setImageResource(diepic[die3]);

        //tmsg= "Press draw button to play.";
        if (player==2 && robot==1) {
            brainn=new Random().nextInt(5)+1;  // [0...3] [min = 0, max = 5]
            if (brainn>3) brainn=0;
            System.out.println("Robot brains count this round : "+brainn);
            gbrainn=brainn;
            gbrnt = (TextView)  findViewById(R.id.gbrainn);
            gbrnt.setText(""+gbrainn);
            prompt = "Pass to add " + gbrainn + " to robot score.";
            promptmsgt = (TextView)  findViewById(R.id.prompt);
            promptmsgt.setText(prompt);
            turnover=3;
        }

    } //resetdata method end

    private void initialize() {
        player = 0;
        score1=score2=0;
        turnover=0;
        gameover=0;
        round=1;
        bonusadd=bonussub=addflag=subflag=0;
        aq = new int[]{0, 0, 0};
        java.util.List<Integer> diceStr = new java.util.ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,2,2,2,2,3,3,3));
        randStr = new java.util.ArrayList<Integer>(diceStr);
        Collections.shuffle(randStr);

        System.out.println("ramdomize dice : "+randStr);
        System.out.println("length is : "+randStr.size());

        resetdata();

    }
    private void draw() {

        mquest1a.setText("");
        mquest1b.setText("");
        qmsgon = 0;
        die1 = randStr.get(randStr.size() - 1);
        die2 = randStr.get(randStr.size() - 2);
        die3 = randStr.get(randStr.size() - 3);
        prompt = "Press Roll button to roll dice.";
        promptmsgt.setText(prompt);
             turnover = 1;

        System.out.println("die #1 : " + die1 + " die#2 : " + die2 + " die#3 : " + die3);

    } //method draw end

    private void roll() {

            java.util.List<Integer> grnlist = new java.util.ArrayList<Integer>(Arrays.asList(4, 4, 4, 5, 6, 6));
            java.util.List<Integer> yellist = new java.util.ArrayList<Integer>(Arrays.asList(7, 7, 8, 8, 9, 9));
            java.util.List<Integer> redlist = new java.util.ArrayList<Integer>(Arrays.asList(10, 11, 11, 11, 12, 12));
            //qmsg1=qmsg2="";

            mquest1a.setText("");
            mquest1b.setText("");
            qmsgon = 0;

            switch (die1) {
                case 1:
                    Collections.shuffle(grnlist);
                    die1 = grnlist.get(0);
                    break;
                case 2:
                    Collections.shuffle(yellist);
                    die1 = yellist.get(0);
                    break;
                case 3:
                    Collections.shuffle(redlist);
                    die1 = redlist.get(0);
                    break;
            }

            switch (die2) {
                case 1:
                    Collections.shuffle(grnlist);
                    die2 = grnlist.get(0);
                    break;
                case 2:
                    Collections.shuffle(yellist);
                    die2 = yellist.get(0);
                    break;
                case 3:
                    Collections.shuffle(redlist);
                    die2 = redlist.get(0);
                    break;
            }
            switch (die3) {
                case 1:
                    Collections.shuffle(grnlist);
                    die3 = grnlist.get(0);
                    break;
                case 2:
                    Collections.shuffle(yellist);
                    die3 = yellist.get(0);
                    break;
                case 3:
                    Collections.shuffle(redlist);
                    die3 = redlist.get(0);
                    break;
            }
            System.out.println("dice rolled : " + die1 + "," + die2 + "," + die3);

    } //method roll end

    private void checkblast() {

        if (die1==4) gbrainn++;
        if (die1==7) ybrainn++;
        if (die1==10) rbrainn++;

        if (die1==6) gblastn++;
        if (die1==9) yblastn++;
        if (die1==12) rblastn++;

        if (die2==4) gbrainn++;
        if (die2==7) ybrainn++;
        if (die2==10) rbrainn++;

        if (die2==6) gblastn++;
        if (die2==9) yblastn++;
        if (die2==12) rblastn++;

        if (die3==4) gbrainn++;
        if (die3==7) ybrainn++;
        if (die3==10) rbrainn++;

        if (die3==6) gblastn++;
        if (die3==9) yblastn++;
        if (die3==12) rblastn++;

        brainn=gbrainn+ybrainn+rbrainn;
        blastn=gblastn+yblastn+rblastn;

        if (blastn>2) {
            turnover=3;   //pass only
            System.out.println("turnover, pass only  brain/blast"+brainn+"/"+blastn);
        }
        else {
            turnover=2;
            System.out.println("OK to play again or pass brain/blast"+brainn+"/"+blastn);
        }
    }//checkblast method end

    //Find probability of drawing 0,1,2,3 green dice before drawing
    public void Q1() {
        nr=ng=ny=0;
        probnogrn = new float[]{0, 0, 0, 0};
        for (int i=0; i<randStr.size(); i++) {
            //System.out.println("randStr element :"+randStr.get(i));
            if (randStr.get(i)==1) ng++;
            if (randStr.get(i)==2) ny++;
            if (randStr.get(i)==3) nr++;
        }//i loop end
        System.out.println("color dice count :"+ng+","+ny+","+nr);
        int dn=0;
        int dnn=0;
        if (die1==5) dn++;
        if (die2==5) dn++;
        if (die3==5) dn++;
        if (die1==8 || die1==11) dnn++;
        if (die2==8 || die2==11) dnn++;
        if (die3==8 || die3==11) dnn++;
        System.out.println("green feet count in draw: "+dn+"nongreen feet count: "+dnn);
        switch (dn) {
            case 0: //no green foot drawn previously

                if (dnn==0)	{
                    //no previous nongreen feet
                    // 0 green:   (nr+ny)C3 / (nr+ny+ng)C3
                    probnogrn[0]= (float) ((nr+ny)*(nr+ny-1)*(nr+ny-2)/6)/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
                    // 1 green:    ngC1 x (nr+ny)C2  /  (nr+ny+ng)C3
                    probnogrn[1]= (float) (ng*(nr+ny)*(nr+ny-1)/2)/((ng+ny+nr)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
                    // 2 green:    ngC2 x (nr+ny)C1  /  (nr+ny+ng)C3
                    if (ng>1) probnogrn[2]= (float) ((nr+ny)*ng*(ng-1)/2)/((ng+ny+nr)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
                    else probnogrn[2]=0;
                    // 3 green:    ngC3   /  (nr+ny+ng)C3
                    if (ng>2) probnogrn[3]= (float) ((ng)*(ng-1)*(ng-2)/6)/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
                    else probnogrn[3]=0;
                }//no non green feet
                //1 previous nongreen feet
                if (dnn==1) {
                    // 0 green:   (nr+ny-1)C2 / (nr+ny+ng-1)C2
                    probnogrn[0]= (float) ((nr+ny-1)*(nr+ny-2)/2)/((nr+ng+ny-1)*(nr+ng+ny-2)/2);
                    // 1 green:   ngC1 x (nr+ny-1)C1 / (nr+ny+ng-1)C2
                    probnogrn[1]= (float) (ng*(nr+ny-1))/((nr+ng+ny-1)*(nr+ng+ny-2)/2);
                    // 2 green:   ngC2 / (nr+ny+ng-1)C2
                    if (ng>2) probnogrn[2]= (float) (ng*(ng-1)/2)/((nr+ng+ny-1)*(nr+ng+ny-2)/2);
                    else probnogrn[2]=0;
                    // 3 green:   not possible with one previous nongreen
                    probnogrn[3]= (float) (0);
                }//1 non green feet
                //2 previous nongreen feet
                if (dnn==2)	{
                    // 0 green:   (nr+ny-2)C1 / (nr+ny+ng-2)C1
                    probnogrn[0]= (float) (nr+ny-2)/(nr+ng+ny-2);
                    // 1 green:   ngC1 / (nr+ny+ng-2)C1
                    probnogrn[1]= (float) (ng)/(nr+ng+ny-2);
                    // 2 green:   not possible with 2 previous nongreen
                    probnogrn[2]= (float) (0);
                    // 3 green:   not possible with 2 previous nongreen
                    probnogrn[3]= (float) (0);
                }//2 non green feet
                //3 previous nongreen feet
                if (dnn==3)	{
                    probnogrn[0]= 1;
                    probnogrn[1]= (float) (0);
                    probnogrn[2]= (float) (0);
                    probnogrn[3]= (float) (0);
                }//3 non green feet


                System.out.println("Prob of "+randq1+" green :"+probnogrn[0]+","+probnogrn[1]+","+probnogrn[2]+","+probnogrn[3]);
                break;
            case 1: //1 green foot drawn previously
                ng=ng-1;
                probnogrn[0]= 0;
                probnogrn[1]= (float) (0);
                probnogrn[2]= (float) (0);
                probnogrn[3]= (float) (0);

                //no previous nongreen feet
                if (dnn==0) {
                    // 0 green:   with 1 previous green, not possible
                    probnogrn[0]= 0;
                    // 1 green:   (nr+ny)C2 / (nr+ny+ng)C2
                    probnogrn[1]= ((ny+nr)*(ny+nr-1)/2)/((ng+ny+nr)*(ng+ny+nr-1)/2); //2 other non green
                    // 2 green:   ngC1 x (nr+ny)C1 / (nr+ny+ng)C2
                    probnogrn[2]= ng*(nr+ny)/((ng+ny+nr)*(ng+ny+nr-1)/2);//2 green 1 non green
                    // 3 green:   ngC2  / (nr+ny+ng)C2
                    if (ng>1) probnogrn[3]= (ng*(ng-1)/2)/((ng+ny+nr)*(ng+ny+nr-1)/2); //2 green and 1 previous green feet
                    else probnogrn[3]= 0;
                }//n0 non green
                //1 previous nongreen feet
                if (dnn==1) {
                    // 0 green:   with 1 previous green, not possible
                    probnogrn[0]= 0;
                    // 1 green:   (nr+ny-1)C1 / (ng+nr+ny-1)C1 with 1 previous green, 1 non green
                    probnogrn[1]= (ny+nr-1)/(ng+ny+nr-1); //2 other non green
                    // 2 green:   ngC1 / (ng+nr+ny-1)C1 with 1 previous green, 1 non green
                    probnogrn[2]= ng/(ng+ny+nr-1);//1 green 1 non green
                    // 3 green:   with 1 nongreen feet, not possible
                    probnogrn[3]= 0;
                }//1 non green
                //2 previous nongreen feet
                if (dnn==2) {
                    // 0 green:   with 1 previous green
                    probnogrn[0]= 0;
                    // 1 green:   with 1 previous green, not possible
                    probnogrn[1]= 1; //2 other non green
                    probnogrn[2]= 0;//2 non green
                    probnogrn[3]= 0; //2 non green
                }//2 non green

                System.out.println("Prob of "+randq1+" green :"+probnogrn[0]+","+probnogrn[1]+","+probnogrn[2]+","+probnogrn[3]);
                break;
            case 2: //2 green feet drawn previously
                ng=ng-2;

                if (dnn==0) {
                    probnogrn[0]= 0;
                    probnogrn[1]= 0;
                    if (ng>0) probnogrn[2]= (ny+nr)/(ng+ny+nr);//2 green 1 non green
                    else probnogrn[2]=1;
                    if (ng>0) probnogrn[3]= ng/(ng+ny+nr);
                    else  probnogrn[3]= 0;
                }//0 non green
                if (dnn==1) {
                    probnogrn[0]= 0;
                    probnogrn[1]= 0;
                    probnogrn[2]= 1; //2 green 1 non green
                    probnogrn[3]= 0;
                }// non green

                System.out.println("Prob of "+randq1+" green :"+probnogrn[0]+","+probnogrn[1]+","+probnogrn[2]+","+probnogrn[3]);
                break;
            case 3: //3 green feet drawn previously
                probnogrn[0]= 0;
                probnogrn[1]= 0;
                probnogrn[2]= 0;
                probnogrn[3]= 1;
                System.out.println("Prob of "+randq1+" green :"+probnogrn[0]+","+probnogrn[1]+","+probnogrn[2]+","+probnogrn[3]);
                break;

        }//switch dn end

        answer=probnogrn[randq1];
    }//mehtod q1 end
    //Find probability of drawn color dice combo after drawing
    public void Q2() {
        nr=ng=ny=0;
        for (int i=0; i<randStrbk.size(); i++) {
            //System.out.println("randStr element :"+randStr.get(i));
            if (randStrbk.get(i)==1) ng++;
            if (randStrbk.get(i)==2) ny++;
            if (randStrbk.get(i)==3) nr++;
        }//i loop end

        drawnr=drawng=drawny=0;
        for (int i=0; i<3; i++) {
            switch (randStrbk.get(randStrbk.size()-1-i)) {
                case 1:
                    drawng++;
                    break;
                case 2:
                    drawny++;
                    break;
                case 3:
                    drawnr++;
                    break;
            } //switch end
        } //iloop end
        //checking feet drawn
        int dfeet=0;
        //die1 a green foot
        if (die1==5) {
            drawng--;
            dfeet++;
            ng--;
        }
        //die2 a green foot
        if (die2==5) {
            drawng--;
            dfeet++;
            ng--;
        }
        //die3 a green foot
        if (die1==5) {
            drawng--;
            dfeet++;
            ng--;
        }
        //die1 a yellow foot
        if (die1==8) {
            drawny--;
            dfeet++;
            ny--;
        }
        //die2 a yellow foot
        if (die2==8) {
            drawny--;
            dfeet++;
            ny--;
        }
        //die3 a yellow foot
        if (die1==8) {
            drawny--;
            dfeet++;
            ny--;
        }
        //die1 a red foot
        if (die1==11) {
            drawnr--;
            dfeet++;
            nr--;
        }
        //die2 a red foot
        if (die2==11) {
            drawnr--;
            dfeet++;
            nr--;
        }
        //die3 a red foot
        if (die1==11) {
            drawnr--;
            dfeet++;
            nr--;
        }

        System.out.println("color dice count :"+ng+","+ny+","+nr);
        System.out.println("color dice combo :"+drawng+","+drawny+","+drawnr);
        System.out.println("count of drawn feet :"+dfeet);
        int pdg,pdr,pdy;
        //no previous drawn feet
        pdg=pdr=pdy=1;
        switch (drawng) {
            case 0:
                pdg=1;
                break;
            case 1:
                pdg=ng;
                break;
            case 2:
                pdg=ng*(ng-1)/2;
                break;
            case 3:
                pdg=ng*(ng-1)*(ng-2)/6;
                break;
        }//switch drawng end
        switch (drawny) {
            case 0:
                pdy=1;
                break;
            case 1:
                pdy=ny;
                break;
            case 2:
                pdy=ny*(ny-1)/2;
                break;
            case 3:
                pdy=ny*(ny-1)*(ny-2)/6;
                break;
        }//switch drawny end
        switch (drawnr) {
            case 0:
                pdr=1;
                break;
            case 1:
                pdr=nr;
                break;
            case 2:
                pdr=nr*(nr-1)/2;
                break;
            case 3:
                pdr=nr*(nr-1)*(nr-2)/6;
                break;

        }//switch drawnr end
        //no drawn feet
        if (dfeet==0)
            probcolorcombo= (float)pdg*pdy*pdr/((nr+ng+ny)*(nr+ng+ny-1)*(nr+ng+ny-2)/6);
        //1 drawn feet
        if (dfeet==1)
            probcolorcombo= (float)pdg*pdy*pdr/((nr+ng+ny)*(nr+ng+ny-1)/2);
        //2 drawn feet
        if (dfeet==2)
            probcolorcombo= (float)pdg*pdy*pdr/((nr+ng+ny));
        //3 drawn feet
        if (dfeet==3)
            probcolorcombo= 1;
        System.out.println("prob of drawn color combo :"+probcolorcombo);
        answer= probcolorcombo;
    }//mehtod q2 end

    //before rolling dice probabilities of 0,1,2,3 blasts based on drawn color combo
    public void Q3() {

        float probrblast= (float) 1/2;
        float probyblast= (float) 1/3;
        float probgblast= (float) 1/6;
        probnoblast = new float[]{0, 0, 0, 0};
        nr=ng=ny=0;
        for (int i=0; i<randStrbk.size(); i++) {
            //System.out.println("randStr element :"+randStr.get(i));
            if (randStrbk.get(i)==1) ng++;
            if (randStrbk.get(i)==2) ny++;
            if (randStrbk.get(i)==3) nr++;
        }//i loop end
        drawnr=drawng=drawny=0;
        for (int i=0; i<3; i++) {

            switch (randStrbk.get(randStrbk.size()-1-i)) {
                case 1:
                    drawng++;
                    break;
                case 2:
                    drawny++;
                    break;
                case 3:
                    drawnr++;
                    break;
            } //switch end
        } //iloop end
        float ptemp;
        float Prob1rblast,Prob1yblast,Prob1gblast;
        //System.out.println("color dice count :"+ng+","+ny+","+nr);
        //System.out.println("color dice combo :"+drawng+","+drawny+","+drawnr);
//case of 0 blasts
        ptemp = (float) Math.pow((double) (1-probrblast),(double) drawnr);
        probnoblast[0]= ptemp;
        ptemp = (float) Math.pow((double) (1-probyblast),(double) drawny);
        probnoblast[0] = (float) probnoblast[0]*ptemp;
        ptemp = (float) Math.pow((double) (1-probgblast),(double) drawng);
        probnoblast[0] = (float) probnoblast[0]*ptemp;
        //probnoblast[0] = (float) (Math.pow((double)(1-probrblast),(double)drawnr))*(Math.pow((double)(1-probyblast),(double) drawny))*(Math.pow((double)(1-probgblast),(double) drawng));
        System.out.println("prob of no blast :"+probnoblast[0]);

//case of 1 blast
        //case:  RGY dice
        if (drawny==1 && drawng==1 && drawnr==1) {
            Prob1rblast= (float) (probrblast)*(1-probyblast)*(1-probgblast);
            Prob1yblast= (float) (1-probrblast)*(probyblast)*(1-probgblast);
            Prob1gblast= (float) (1-probrblast)*(1-probyblast)*(probgblast);
            probnoblast[1] = Prob1rblast+Prob1yblast+Prob1gblast;
        } //if rgy end
        //case: RRY dice
        if (drawny==1 && drawnr==2) {
            Prob1rblast= (float) (probrblast)*(1-probrblast)*(1-probyblast)*2;
            Prob1yblast= (float) (1-probrblast)*(1-probrblast)*(probyblast);
            probnoblast[1] = Prob1rblast+Prob1yblast;
        } //if rry end
        //case: RRG dice
        if (drawng==1 && drawnr==2) {
            Prob1rblast= (float) (probrblast)*(1-probrblast)*(1-probgblast)*2;
            Prob1gblast= (float) (1-probrblast)*(1-probrblast)*(probgblast);
            probnoblast[1] = Prob1rblast+Prob1gblast;
        } //if rrg end
        //case: RRR dice
        if (drawnr==3) {
            Prob1rblast= (float) (probrblast)*(1-probrblast)*(1-probrblast)*3;
            probnoblast[1] = Prob1rblast;
        } //if rrr end
        //case: YYG dice
        if (drawny==2 && drawng==1) {
            Prob1yblast= (float) (probyblast)*(1-probyblast)*(1-probgblast)*2;
            Prob1gblast= (float) (1-probyblast)*(1-probyblast)*(probgblast);
            probnoblast[1] = Prob1yblast+Prob1gblast;
        } //if yyg end
        //case: YYR dice
        if (drawny==2 && drawnr==1) {
            Prob1yblast= (float) (probyblast)*(1-probyblast)*(1-probrblast)*2;
            Prob1rblast= (float) (1-probyblast)*(1-probyblast)*(probrblast);
            probnoblast[1] = Prob1rblast+Prob1yblast;
        } //if yyr end
        //case: YYY dice
        if (drawny==3) {
            Prob1yblast= (float) (probyblast)*(1-probyblast)*(1-probyblast)*3;
            probnoblast[1] = Prob1yblast;
        } //if yyy end
        //case: GGR dice
        if (drawng==2 && drawnr==1) {
            Prob1gblast= (float) (probgblast)*(1-probgblast)*(1-probrblast)*2;
            Prob1rblast= (float) (1-probgblast)*(1-probgblast)*(probrblast);
            probnoblast[1] = Prob1rblast+Prob1gblast;
        } //if ggr end
        //case: GGY dice
        if (drawny==1 && drawng==2) {
            Prob1gblast= (float) (probgblast)*(1-probgblast)*(1-probyblast)*2;
            Prob1yblast= (float) (1-probgblast)*(1-probgblast)*(probyblast);
            probnoblast[1] = Prob1yblast+Prob1gblast;
        } //if ggy end
        //case: GGG dice
        if (drawng==3) {
            Prob1gblast= (float) (probgblast)*(1-probgblast)*(1-probgblast)*3;
            probnoblast[1] = Prob1gblast;
        } //if ggg end
//case of 3 blasts
        ptemp = (float) Math.pow((double) probrblast,(double) drawnr);
        probnoblast[3]= ptemp;
        ptemp = (float) Math.pow((double) probyblast,(double) drawny);
        probnoblast[3] = (float) probnoblast[3]*ptemp;
        ptemp = (float) Math.pow((double) probgblast,(double) drawng);
        probnoblast[3] = (float) probnoblast[3]*ptemp;
//case of 2 blasts
        probnoblast[2]= (float) (1-probnoblast[0]-probnoblast[1]-probnoblast[3]);
        System.out.println("Prob of no blast :"+probnoblast[0]+","+probnoblast[1]+","+probnoblast[2]+","+probnoblast[3]);

    }//method q3 end

    //after rolling probability of rolled outcome
    public void Q4() {

        rollo = new float[]{0,0,0,0,0,0,0,0,0,0,0,0,0};

        //rollo[0]=rollo[1]=rollo[2]=rollo[3]=(float) 0.0 ;
        rollo[0]=(float) 0*1;
        rollo[1]=(float) 0*1;
        rollo[2]=(float) 0*1;
        rollo[3]=(float) 0*1;
        rollo[4]=(float) 1/2; //green brain outcome
        rollo[5]=(float) 1/3; //green feet outcome
        rollo[6]=(float) 1/6; //green blast outcome
        rollo[7]=(float) 1/3; //yellow brain outcome
        rollo[8]=(float) 1/3; //yellow feet outcome
        rollo[9]=(float) 1/3; //yellow blast outcome
        rollo[10]=(float) 1/6; //red brain outcome
        rollo[11]=(float) 1/3; //red feet outcome
        rollo[12]=(float) 1/2; //red blast outcome

        Q2();
        //die1=die2=die3=4;
        System.out.println("Rolled outcome combo : "+die1+","+die2+","+die3);
        probroll = (float) probcolorcombo*rollo[die1]*rollo[die2]*rollo[die3];
        System.out.println("prob of rolled outcome combo :"+probroll);
        answer=probroll;
    }//mehtod q4 end


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
            gunshot_3 = ourSounds.load(this, R.raw.gunshot_3, 1);
            tada = ourSounds.load(this, R.raw.tada, 1);
            uhoh = ourSounds.load(this, R.raw.uhoh, 1);
            zombie = ourSounds.load(this, R.raw.zombie, 1);
        } else {
            ourSounds = new SoundPool(2, AudioManager.STREAM_MUSIC, 1);
            agogothi = ourSounds.load(this, R.raw.agogothi, 1);
            alienbti = ourSounds.load(this, R.raw.alienbti, 1);
            gunshot_3 = ourSounds.load(this, R.raw.gunshot_3, 1);
            tada = ourSounds.load(this, R.raw.tada, 1);
            uhoh = ourSounds.load(this, R.raw.uhoh, 1);
            zombie = ourSounds.load(this, R.raw.zombie, 1);

        }
    }//initializeSound method end
}