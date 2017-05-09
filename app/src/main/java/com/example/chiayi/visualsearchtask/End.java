package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class End extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        Intent intent=getIntent();
    }
    public void replay(View view){
        Intent target = new Intent(End.this,MainActivity.class);
        startActivity(target);
    }
}
