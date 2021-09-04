package com.example.w01projsolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w01projsolo.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class  MainActivity extends AppCompatActivity {
//    private TextView textViewResult;

    private EditText eName;
    private EditText ePassword;
    private Button eLogin;
    private TextView eAttemptsInfo;

//    private String Username = "Admin";
//    private String Password = "12345678";
      private String idUser = "";

    private HashMap<String,String> userList = new HashMap<String,String>();



    boolean isValid = false;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList.put("Admin","pass-1");
        userList.put("Admin2","pass-2");
        userList.put("Admin3","pass-3");
        userList.put("Admin4","pass-4");

        eName = findViewById(R.id.etName);
        ePassword = findViewById(R.id.etPassword);
        eAttemptsInfo = findViewById(R.id.tvAttemptInfo);
        eLogin = findViewById(R.id.btnLogin);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String inputName = eName.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if(inputName.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please enter all details correctly!",Toast.LENGTH_SHORT).show();
                }
                else{
                    isValid = validate(inputName,inputPassword);

                    if(!isValid){
                        counter--;

                        Toast.makeText(MainActivity.this,"Incorrect credentials entered!",Toast.LENGTH_SHORT).show();

                        eAttemptsInfo.setText("Number of Attempts: " + counter);
                        if(counter == 0){
                            eLogin.setEnabled(false);
                        }
                    }else{


                        Intent intent =  HomePageActivity.getIntent(getApplicationContext(),"Login successful!!!!");
//
                        String strName = idUser;
                        intent.putExtra("STRING_I_NEED", strName);
                        startActivity(intent);



                    }

                }

            }
        });


    }

    private boolean validate(String name,String password){

//        if(name.equals(Username) && password.equals(Password)){
//            return true;
//        }

        if(userList.containsKey(name))
        {
            String pw = userList.get(name);

            String[] parts = userList.get(name).split("-");
            String part1 = parts[0];
            String part2 = parts[1];
            idUser = part2;
            if(part1.equals(password))
            {
                return true;
            }
        }


        return false;
    }
}