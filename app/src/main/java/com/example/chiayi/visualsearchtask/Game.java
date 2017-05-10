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

import java.util.ArrayList;
import java.util.Random;

public class Game extends Activity {
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
    int i=0;
    //countdown timer for the game
    CountDownTimer countdown;
    //string for the image name to know the answer
    String ans;
    //to get the time left as score
    long time_left=0;
    //total score for the user
    public static int result=0;

    //to know if user passed 75% of correctness for absent trials to make sure user is paying attention while playing
    public static double correct_absent=0;
    double avgtime_feature_3;
    double avgtime_feature_6;
    double avgtime_feature_9;
    double avgtime_feature_12;


    int fea3=0;
    int fea6=0;
    int fea9=0;
    int fea12=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //getting intent from pervious activity
        Intent intent=getIntent();
        msg=intent.getStringExtra("msg");

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
        level.setText(1+"/"+imgs.length());
        score.setText("Score : " + 0);

        //start intializing runtask that randomize images, restart and cancel timer and handles button event
        gameplay();

    }
    //run task schedule the runtask method to run periodically until the level ends
    /*public void runtask() {
        final Handler handler = new Handler();
        final Timer t = new Timer(); //This is new

        TimerTask timertask= new TimerTask() {

            @Override
            public void run() {
                if(++counter==imgs.length()+1){
                    t.cancel();
                    Intent target = new Intent(Game.this,Stage2.class);
                    target.putExtra("msg",msg);
                    startActivity(target);
                    finish();

                }
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

            }
        };

        t.schedule(timertask, 0); // execute in every 15sec
    }*/

    //the method that will be repeated, the core of the page
    public void gameplay() {
        i++;
        Random rand = new Random();
        if(i>1 && i<=imgs.length()){
            level.setText(String.valueOf(i) + "/" + String.valueOf(imgs.length()));
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

        countdown=new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                time_left=millisUntilFinished/1000;
                timer.setText(time_left+" s");
            }

            public void onFinish() {
                timer.setText("Times Up!");
                gameplay();
            }
        }.start();


        final String p=ans.substring(22,23);
        final String dist=ans.substring(20,21);


        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.setEnabled(false);
                absent.setEnabled(false);
                countdown.cancel();
                result+=time_left;
                score.setText("Score : "+result);
                if(p.equals("p")){

                    if(dist.equals(String.valueOf(3))){
                        avgtime_feature_3+=time_left;
                        fea3++;
                    }
                    else if(dist.equals(String.valueOf(6))){
                        avgtime_feature_6+=time_left;
                        fea6++;
                    }
                    else if(dist.equals(String.valueOf(9))){
                        avgtime_feature_9+=time_left;
                        fea9++;
                    }

                }
                if(ans.substring(23,24).equals("p")){
                    if(ans.substring(20,22).equals(String.valueOf(12))){
                        avgtime_feature_12+=time_left;
                        fea12++;
                    }
                }

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
                countdown.cancel();
                if(p.equals("a") || ans.substring(23,24).equals("a")){
                    correct_absent+=1;
                }
                gameplay();
                absent.setEnabled(true);
                present.setEnabled(true);

            }
        });


        Log.i("fea3",String.valueOf(fea3));
        Log.i("fea6",String.valueOf(fea6));
        Log.i("fea9",String.valueOf(fea9));
        Log.i("fea12",String.valueOf(fea12));
        if(i==imgs.length()+1){

            if(MainActivity.user.getAccurate()>=75){
                MainActivity.user.setStatus("Pass");
            }
            else{
                MainActivity.user.setStatus("Fail");
            }

            MainActivity.user.setFea_AvgTime3(avgtime_feature_3/5);
            MainActivity.user.setFea_AvgTime6(avgtime_feature_6/5);
            MainActivity.user.setFea_AvgTime9(avgtime_feature_9/5);
            MainActivity.user.setFea_AvgTime12(avgtime_feature_12/5);

            Intent target = new Intent(Game.this,Stage2.class);
            target.putExtra("msg",msg);
            startActivity(target);
            finish();

        }


    }



}
