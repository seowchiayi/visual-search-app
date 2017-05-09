package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Stage1 extends Activity {
    String msg;
    String num;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage1);
        Intent instruction=getIntent();
        msg=instruction.getStringExtra("username");
        num=instruction.getStringExtra("matric");
        pass=instruction.getStringExtra("key");
    }
    public void play(View v){
        Intent game = new Intent(Stage1.this,Game.class);
        game.putExtra("username",msg);
        game.putExtra("matric",num);
        game.putExtra("key",pass);
        startActivity(game);
        finish();

    }
}
