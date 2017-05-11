package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class Game2 extends Activity {
    //carrying matric and username from previous activity
    String msg;
    //initializing components in xml
    TextView username;
    TextView level;
    TextView score;
    TextView timer;
    ImageView img;
    Button present;
    Button absent;
    //calling integer-array from strings.xml that is storing images for the game
    TypedArray imgs;

    ArrayList<Integer> remove=new ArrayList<Integer>();
    //the count for level
    int i=40;
    //countdown timer for the game
    CountDownTimer countdown;
    //string for the image name to know the answer
    String ans;
    //to get the time left as score
    long time_left;

    //to know if user passed 75% of correctness for absent trials to make sure user is paying attention while playing


    double avgtime_con_3;
    double avgtime_con_6;
    double avgtime_con_9;
    double avgtime_con_12;

    int con3=0;
    int con6=0;
    int con9=0;
    int con12=0;


    int counter=0;
    TrialsData stg2_trial_data=new TrialsData();
    final FirebaseDatabase trial_data=FirebaseDatabase.getInstance("https://visual2-af586.firebaseio.com/");
    final DatabaseReference trial_ref=trial_data.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //getting intent from pervious activity
        Intent intent=getIntent();
        msg=intent.getStringExtra("msg");
        Log.i("Game result",String.valueOf(Game.result));
        //initializing componenets in xml
        username=(TextView)findViewById(R.id.username);
        level=(TextView)findViewById(R.id.level);
        score=(TextView)findViewById(R.id.score);
        img=(ImageView)findViewById(R.id.image);
        timer=(TextView)findViewById(R.id.timer);
        present=(Button)findViewById(R.id.present);
        absent=(Button)findViewById(R.id.absent);

        //initializing integer array for images in strings.xml
        imgs = getResources().obtainTypedArray(R.array.conjunction_img);

        //set original name,level,score on top of the display bar
        username.setText(msg);
        level.setText("40/80");
        score.setText("Score : " + Game.result);
        stg2_trial_data.setPlayer_name(msg);


        //start intializing runtask that randomize images, restart and cancel timer and handles button event
        gameplay();

    }

    //the method that will be repeated, the core of the page
    public void gameplay() {
        i++;
        Random rand = new Random();
        if(i>41 && i<=imgs.length()){
            level.setText(String.valueOf(i) + "/80");
            stg2_trial_data.setTrial_no(i);
            int rndInt = rand.nextInt(imgs.length());
            while(remove.contains(rndInt)){
                rndInt = rand.nextInt(imgs.length());

            }
            img.setImageResource(imgs.getResourceId(rndInt, 0));
            remove.add(0,rndInt);
            // get resource ID by index
            //0 is the value to return when no image is found
            ans = imgs.getString(rndInt);
            // or set you ImageView's resource to the id


        }
        else if(i<=41){
            int rndInt = rand.nextInt(imgs.length());
            stg2_trial_data.setTrial_no(i);
            remove.add(0,rndInt);
            // get resource ID by index
            //0 is the value to return when no image is found
            ans = imgs.getString(rndInt);
            // or set you ImageView's resource to the id
            img.setImageResource(imgs.getResourceId(rndInt, 0));
        }

        countdown=new CountDownTimer(41000, 1000) {
            public void onTick(long millisUntilFinished) {
                time_left=millisUntilFinished/1000;
                timer.setText(time_left+" s");
            }

            public void onFinish() {
                gameplay();
            }
        }.start();
        final String p=ans.substring(26,27);
        final String dist=ans.substring(24,25);

        stg2_trial_data.setTask("Conjunction");




        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.setEnabled(false);
                absent.setEnabled(false);
                countdown.cancel();
                stg2_trial_data.setUser_ans("Present");
                stg2_trial_data.setResponse_time(time_left);
                if(p.equals("p") || ans.substring(27,28).equals("p")){
                    stg2_trial_data.setTarget_status("Present");
                    stg2_trial_data.setReal_answer("Correct");
                    if(dist.equals(String.valueOf(3))){
                        stg2_trial_data.setDist_no(3);
                        stg2_trial_data.setScore_per_trial(time_left);
                        stg2_trial_data.setResponse_time(time_left);
                        Game.result+=time_left;
                        avgtime_con_3+=time_left;
                        con3++;
                    }
                    else if(dist.equals(String.valueOf(6))){
                        stg2_trial_data.setDist_no(6);
                        stg2_trial_data.setScore_per_trial(time_left);
                        stg2_trial_data.setResponse_time(time_left);
                        Game.result+=time_left;
                        avgtime_con_6+=time_left;
                        con6++;
                    }
                    else if(dist.equals(String.valueOf(9))){
                        stg2_trial_data.setDist_no(9);
                        stg2_trial_data.setScore_per_trial(time_left);
                        stg2_trial_data.setResponse_time(time_left);
                        Game.result+=time_left;
                        avgtime_con_9+=time_left;
                        con9++;
                    }
                    else if(ans.substring(24,26).equals(String.valueOf(12))){
                        stg2_trial_data.setDist_no(12);
                        stg2_trial_data.setScore_per_trial(time_left);
                        stg2_trial_data.setResponse_time(time_left);
                        Game.result+=time_left;
                        avgtime_con_12+=time_left;
                        con12++;
                    }


                }

                else{
                    stg2_trial_data.setTarget_status("Absent");
                    stg2_trial_data.setReal_answer("Wrong");
                    stg2_trial_data.setScore_per_trial(0);

                }

                score.setText("Score: "+Game.result);
                trial_ref.push().setValue(stg2_trial_data);

                gameplay();
                present.setEnabled(true);
                absent.setEnabled(true);


            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                absent.setEnabled(false);
                present.setEnabled(false);
                stg2_trial_data.setUser_ans("Absent");
                countdown.cancel();
                if(p.equals("a") || ans.substring(27,28).equals("a")){
                    Game.correct_absent+=1;
                    stg2_trial_data.setTarget_status("Absent");
                    stg2_trial_data.setReal_answer("Correct");
                    stg2_trial_data.setScore_per_trial(0);
                }
                else if(p.equals("p") || ans.substring(27,28).equals("p")){
                    stg2_trial_data.setTarget_status("Present");
                    stg2_trial_data.setReal_answer("Wrong");
                    if(dist.equals(String.valueOf(3))){
                        stg2_trial_data.setDist_no(3);
                        stg2_trial_data.setResponse_time(time_left);
                        avgtime_con_3+=time_left;
                        con3++;
                    }
                    else if(dist.equals(String.valueOf(6))){
                        stg2_trial_data.setDist_no(6);
                        stg2_trial_data.setResponse_time(time_left);
                        avgtime_con_6+=time_left;
                        con6++;
                    }
                    else if(dist.equals(String.valueOf(9))){
                        stg2_trial_data.setDist_no(9);
                        stg2_trial_data.setResponse_time(time_left);
                        avgtime_con_9+=time_left;
                        con9++;
                    }
                    else if(ans.substring(24,26).equals(12)){
                        stg2_trial_data.setDist_no(12);
                        stg2_trial_data.setResponse_time(time_left);
                        avgtime_con_12+=time_left;
                        con12++;
                    }
                }
                trial_ref.push().setValue(stg2_trial_data);
                gameplay();
                absent.setEnabled(true);
                present.setEnabled(true);


            }
        });

        if(i==imgs.length()+1){
            FirebaseDatabase db=FirebaseDatabase.getInstance("https://visualsearchtask.firebaseio.com/");
            DatabaseReference ref=db.getReference();
            MainActivity.user.setScore(String.valueOf(Game.result));
            MainActivity.user.setAccurate((Game.correct_absent/40)*100);
            if(MainActivity.user.getAccurate()>=75){
                MainActivity.user.setStatus("Pass");
            }
            else{
                MainActivity.user.setStatus("Fail");
            }
            MainActivity.user.setCon_AvgTime3(avgtime_con_3/5);
            MainActivity.user.setCon_AvgTime6(avgtime_con_6/5);
            MainActivity.user.setCon_AvgTime9(avgtime_con_9/5);
            MainActivity.user.setCon_AvgTime12(avgtime_con_12/5);
            //to be call after everything ends because we need to get avg

            ref.push().setValue(MainActivity.user);
            Intent target = new Intent(Game2.this,End.class);
            startActivity(target);
            finish();

        }



    }



}
