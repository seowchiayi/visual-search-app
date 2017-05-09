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
    int i=0;
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
        level.setText(1+"/"+imgs.length());
        score.setText("Score : " + Game.result);

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
                    Intent target = new Intent(Game2.this,End.class);
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

        t.scheduleAtFixedRate(timertask, 0, 11000); // execute in every 15sec
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
        final String p=ans.substring(26,27);
        final String dist=ans.substring(24,25);

        Log.i("p",p);
        Log.i("dist",String.valueOf(dist));
        Log.i("12ap",ans.substring(27,28));
        Log.i("dist12",ans.substring(24,26));



        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countdown.cancel();
                if(p.equals("p")){
                    Game.result+=time_left;

                    Log.i("in present",String.valueOf(Game.result));
                    score.setText("Score: "+Game.result);
                    if(dist.equals(String.valueOf(3))){
                        avgtime_con_3+=time_left;
                        con3++;
                    }
                    else if(dist.equals(String.valueOf(6))){
                        avgtime_con_6+=time_left;
                        con6++;
                    }
                    else if(dist.equals(String.valueOf(9))){
                        avgtime_con_9+=time_left;
                        con9++;
                    }


                }
                if(ans.substring(27,28).equals("p")){
                    if(ans.substring(24,26).equals(String.valueOf(12))){
                        avgtime_con_12+=time_left;
                        con12++;
                    }
                }

                gameplay();


            }
        });
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countdown.cancel();
                if(ans.substring(27,28).equals("a")){
                    Game.correct_absent+=1;
                }
                gameplay();

            }
        });

        Log.i("con3",String.valueOf(con3));
        Log.i("con6",String.valueOf(con6));
        Log.i("con9",String.valueOf(con9));
        Log.i("con12",String.valueOf(con12));
        Log.i("ca",String.valueOf(Game.correct_absent));

        if(i==imgs.length()+1){
            FirebaseDatabase db=FirebaseDatabase.getInstance();
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
