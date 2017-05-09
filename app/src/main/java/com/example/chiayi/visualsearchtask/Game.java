package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Activity {
    //carrying matric and username from previous activity
    String msg;
    String num;
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
    int i=0;
    //countdown timer for the game
    CountDownTimer countdown;
    //string for the image name to know the answer
    String ans;
    //to get the time left as score
    long time_left;
    //total score for the user
    int result;

    //to know if user passed 75% of correctness for absent trials to make sure user is paying attention while playing
    double correct_absent=0;

    double avgtime_feature_3;
    double avgtime_feature_6;
    double avgtime_feature_9;
    double avgtime_feature_12;




    String pass="";
    int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //getting intent from pervious activity
        Intent intent=getIntent();
        msg=intent.getStringExtra("username");
        num=intent.getStringExtra("matric");
        pass=intent.getStringExtra("key");
        //initializing componenets in xml
        username=(TextView)findViewById(R.id.username);
        level=(TextView)findViewById(R.id.level);
        score=(TextView)findViewById(R.id.score);
        img=(ImageView)findViewById(R.id.image);
        timer=(TextView)findViewById(R.id.timer);
        present=(Button)findViewById(R.id.present);
        absent=(Button)findViewById(R.id.absent);

        //initializing integer array for images in strings.xml
        imgs = getResources().obtainTypedArray(R.array.feature_img);
        //set original name,level,score on top of the display bar
        username.setText(msg);
        level.setText(0+"/"+imgs.length());
        score.setText("Score : " + result);



        //start intializing runtask that randomize images, restart and cancel timer and handles button event
        runtask();

    }
    //run task schedule the runtask method to run periodically until the level ends
    public void runtask() {
        final Handler handler = new Handler();
        final Timer t = new Timer(); //This is new

        TimerTask timertask= new TimerTask() {

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            gameplay();

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
                if(++counter==imgs.length()+1){
                    t.cancel();
                    Intent target = new Intent(Game.this,Stage2.class);
                    target.putExtra("username",msg);
                    target.putExtra("matric",num);
                    target.putExtra("key",pass);
                    startActivity(target);
                    finish();

                }
            }
        };

        t.scheduleAtFixedRate(timertask, 0, 3000); // execute in every 15sec
    }

    //the method that will be repeated, the core of the page
    public void gameplay() {
        i++;
        level.setText(String.valueOf(i) + "/" + String.valueOf(imgs.length()));
        Random rand = new Random();
        if(i>1 && i<=imgs.length()){
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
        else if(i<=1){
            int rndInt = rand.nextInt(imgs.length());
            remove.add(0,rndInt);
            // get resource ID by index
            //0 is the value to return when no image is found
            ans = imgs.getString(rndInt);
            // or set you ImageView's resource to the id
            img.setImageResource(imgs.getResourceId(rndInt, 0));
        }


        countdown=new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                time_left=millisUntilFinished/1000;
                timer.setText(time_left+" s");
            }

            public void onFinish() {
                //timer.setText("Times up!");
            }
        }.start();

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ans.substring(22,23).equals("p")){
                    result+=time_left;
                    score.setText("Score : "+result);

                    if(ans.substring(20,21).equals(String.valueOf(3))){
                        avgtime_feature_3+=result;
                        Log.i("if adddd",String.valueOf(avgtime_feature_3));
                    }
                    else if(ans.substring(20,21).equals(String.valueOf(6))){
                        avgtime_feature_6+=result;
                        Log.i("6 if adddd",String.valueOf(avgtime_feature_6));
                    }
                    else if(ans.substring(20,21).equals(String.valueOf(9))){
                        avgtime_feature_9+=result;
                    }
                    else if(ans.substring(20,21).equals(String.valueOf(12))){
                        avgtime_feature_12+=result;
                    }
                }
                countdown.cancel();

            }
        });

        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ans.substring(22,23).equals("a")){
                    correct_absent+=1;
                }
                countdown.cancel();


            }
        });


        if(i==imgs.length()){
            Record r=new Record();
            r.setScore(String.valueOf(result));
            r.setAccurate((correct_absent/imgs.length())*100);
            if(r.getAccurate()>=75){
                r.setStatus("Pass");
            }
            else{
                r.setStatus("Fail");
            }


            r.setFea_AvgTime3(avgtime_feature_3/imgs.length());
            r.setFea_AvgTime6(avgtime_feature_6/imgs.length());
            r.setFea_AvgTime9(avgtime_feature_9/imgs.length());
            r.setFea_AvgTime12(avgtime_feature_12/imgs.length());


            //to be call after everything ends because we need to get avg
            Log.i("test",String.valueOf(avgtime_feature_3));
            Log.i("score",String.valueOf(r.getScore()));
            Log.i("accurate",String.valueOf(r.getAccurate()));
            Log.i("status ",r.getStatus());
            Log.i("3",String.valueOf(r.getFea_AvgTime3()));
            Log.i("6",String.valueOf(r.getFea_AvgTime6()));
            Log.i("9",String.valueOf(r.getFea_AvgTime9()));
            Log.i("12",String.valueOf(r.getFea_AvgTime12()));

            FirebaseDatabase db=FirebaseDatabase.getInstance();
            DatabaseReference ref=db.getReference();
            Log.i("pass",pass);

            ref.child(pass).child("Stage 1").setValue(r);



        }



    }



}
