package com.civildeal.civildeal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.civildeal.civildeal.R;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.UpdatePasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    Button btn_chnage_passowrd;
    EditText newPassword, confirmPassword;
    String str_password,con_password,mobile_no;
    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btn_chnage_passowrd = findViewById(R.id.btn_submit);
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);

        confirmPassword = (EditText) findViewById(R.id.editpass);
        newPassword = (EditText) findViewById(R.id.phone_number_edt);
        Intent intent = getIntent();
        mobile_no = intent.getStringExtra("mobile_no");
        Toast.makeText(this, "mobile "+mobile_no, Toast.LENGTH_SHORT).show();

        btn_chnage_passowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_password = newPassword.getText().toString().trim();
                con_password = confirmPassword.getText().toString().trim();
                if (newPassword.getText().toString().isEmpty()) {
                    newPassword.requestFocus();
                    newPassword.setError("Please fill new password");

                } else if (confirmPassword.getText().toString().isEmpty()){
                    confirmPassword.requestFocus();
                    confirmPassword.setError("Please fill confirm password");
                }else if (str_password.equals(con_password)){

                    updatePassword();
                }else {
                    Toast.makeText(ChangePassword.this, "Password not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updatePassword() {
        ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Updating your password..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Call<UpdatePasswordResponse> call = Api_Client.getInstance()
                .updatePassword(mobile_no,con_password);
        call.enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                progressDialog.dismiss();
                if (response.body().getCode().equals(200)){

                    try {
                        Toast.makeText(ChangePassword.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePassword.this,Login.class);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }

                }else {
                    Toast.makeText(ChangePassword.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
    }


}