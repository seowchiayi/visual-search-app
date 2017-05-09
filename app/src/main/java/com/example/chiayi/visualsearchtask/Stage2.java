package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Stage2 extends Activity {
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage2);
        Intent intent=getIntent();
        msg=intent.getStringExtra("msg");
    }
    public void play(View v){
        Intent game = new Intent(Stage2.this,Game2.class);
        game.putExtra("msg",msg);
        startActivity(game);

    }
}
