package com.example.chiayi.visualsearchtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    String gender;
    public static User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        spinner = (Spinner) findViewById(R.id.gender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Please select your gender!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void signup(View view) {
        EditText editname = (EditText) findViewById(R.id.name);
        EditText editage = (EditText) findViewById(R.id.age);
        EditText editmatric = (EditText) findViewById(R.id.matric);

        //upload user details into firebase
        String name = editname.getText().toString().trim();
        String age = editage.getText().toString().trim();
        String matric = editmatric.getText().toString().trim();

        user.setName(name);
        user.setAge(age);
        user.setGender(gender);
        user.setMatric(matric);

        if(name.isEmpty() || age.isEmpty() || gender.isEmpty() || matric.isEmpty()){
            Toast toast = Toast.makeText(MainActivity.this, "Please fill up all the details!", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            Intent intent = new Intent(MainActivity.this, Instruction.class);
            intent.putExtra("msg",name);
            startActivity(intent);
            finish();
        }

    }


}

