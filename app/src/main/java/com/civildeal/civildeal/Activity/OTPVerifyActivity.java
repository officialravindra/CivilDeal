package com.civildeal.civildeal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.civildeal.civildeal.R;

public class OTPVerifyActivity extends AppCompatActivity {

    String otp,mobile_no;
    EditText edt_otp;
    Button verifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verify);

        edt_otp = findViewById(R.id.inputCode);
        verifyOtp = findViewById(R.id.codeInputButton);


        Intent intent = getIntent();
        otp = intent.getStringExtra("otp");
        mobile_no = intent.getStringExtra("mobile_no");

        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = ((EditText) findViewById(R.id.inputCode)).getText().toString().trim();
                if (!code.isEmpty()) {
                    hideKeypad();
                    if (code.equals(otp)) {
                        Toast.makeText(OTPVerifyActivity.this, "SuccessFully Verified", Toast.LENGTH_SHORT).show();
                        showCompleted();
                    } else {
                        Toast.makeText(OTPVerifyActivity.this, "OTP is Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void hideKeypad() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    void showCompleted() {
        ImageView checkMark = (ImageView) findViewById(R.id.checkmarkImage);
        checkMark.setVisibility(View.VISIBLE);
        Intent i = new Intent(OTPVerifyActivity.this, ChangePassword.class);
        i.putExtra("mobile_no",mobile_no);
        startActivity(i);
        finish();
    }
}