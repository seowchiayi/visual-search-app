package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends Activity {
    EditText matric;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent=getIntent();

    }
    public void login(View v){
        matric=(EditText) findViewById(R.id.matric);
        name =(EditText)findViewById(R.id.name);

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        final DatabaseReference ref=db.getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User validate=new User();
                String pass;
                for(DataSnapshot data:dataSnapshot.getChildren()){
                    validate=data.getValue(User.class);
                    if(String.valueOf(validate.getMatric()).compareToIgnoreCase(matric.getText().toString().trim())==0 && String.valueOf(validate.getName()).compareToIgnoreCase(name.getText().toString().trim())==0){
                        pass=data.getKey();
                        Intent intent = new Intent(Login.this,Instruction.class);
                        String msg=name.getText().toString();
                        String num=matric.getText().toString();
                        intent.putExtra("username",msg);
                        intent.putExtra("matric",num);
                        intent.putExtra("key",pass);
                        startActivity(intent);
                        finish();
                    }

                }
                if(String.valueOf(validate.getMatric()).compareToIgnoreCase(matric.getText().toString().trim())!=0 || String.valueOf(validate.getName()).compareToIgnoreCase(name.getText().toString().trim())!=0){
                    Toast toast = Toast.makeText(Login.this,"Please sign up first!",Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("login db error", databaseError.getDetails());
            }
        });
    }

}
