package com.civildeal.civildeal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.LoginResponse;
import com.civildeal.civildeal.api.ModelResponse.LoginResultResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    TextInputEditText mobileno, password;
    Button submit;
    TextView joinNow,forgetpassword;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mobileno = findViewById(R.id.mobile_no);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        joinNow = findViewById(R.id.register);
        forgetpassword = findViewById(R.id.forgetPassword);

//        preferences = getApplicationContext().getSharedPreferences("civildeal", MODE_PRIVATE);
//        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
//        editor = preferences.edit();


        sharedPrefManager=new SharedPrefManager(getApplicationContext());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(Login.this,R.anim.bounce);
                submit.startAnimation(myAnim);
                checkDataEntered();
            }


        });

        joinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RegisterForm.class);
                startActivity(intent);
                finish();
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this,ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkDataEntered() {
        if (mobileno.getText().toString().isEmpty()){
            Log.e("tag","erro1");
            mobileno.requestFocus();
            mobileno.setError("Please enter your mobile");
            return;

        }else if (password.getText().toString().trim().isEmpty()){
            Log.e("tag","erro2");
            password.requestFocus();
            password.setError("Please enter your password");
            return;

        }else {

            Call<LoginResultResponse> call;

            {
                Api_Client.getInstance().getLogin(mobileno.getText().toString(),password.getText().toString()).enqueue(new Callback<LoginResultResponse>() {
                    @Override
                    public void onResponse(Call<LoginResultResponse> call, Response<LoginResultResponse> response) {


                        if(response.body().getCode().equals(200)){

                            try {
                                LoginResultResponse loginResultResponse = response.body();
                                if (loginResultResponse.getData()!=null){
                                    List<LoginResponse> loginResponse = loginResultResponse.getData();
                                    sharedPrefManager.saveUser(loginResultResponse.getData().get(0));

                                    Log.e("data","data "+response.body().getData());
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Intent intent = new Intent(Login.this,MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }


//        Toast.makeText(Login.this, "Sucess", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(Login.this,MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                                finish();
                            }catch (Exception e){
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                Log.d("tag","Exception "+response.body().getMessage());

                            }



                        }else if (response.body().getMessage().equals("Sorry! Mobile number or password not match.")){
                            Toast.makeText(Login.this, "Please fill correct mobile number and password", Toast.LENGTH_SHORT).show();
                        }else {
                            Log.d("tag","fail");
                        }


                    }

                    @Override
                    public void onFailure(Call<LoginResultResponse> call, Throwable t) {
                        Log.e("tag","failuer"+t.getLocalizedMessage());
//                        Toast.makeText(Login.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sharedPrefManager.isLoggedIn()){

            Intent intent = new Intent(Login.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
    }
}