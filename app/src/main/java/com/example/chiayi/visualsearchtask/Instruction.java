package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Instruction extends Activity {
    String msg;
    String num;
    String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        Intent signup=getIntent();
        msg=signup.getStringExtra("username");
        num=signup.getStringExtra("matric");
        pass=signup.getStringExtra("key");

    }
    public void start(View v){
        Intent target = new Intent(Instruction.this,Stage1.class);
        target.putExtra("username",msg);
        target.putExtra("matric",num);
        target.putExtra("key",pass);
        startActivity(target);
        finish();

    }
}
