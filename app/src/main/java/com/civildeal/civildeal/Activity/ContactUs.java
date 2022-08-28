package com.civildeal.civildeal.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.R;

public class ContactUs extends AppCompatActivity {

    TextView tv_contact_us_no,emailus;
    ImageView img_call;
    Button call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        img_call = findViewById(R.id.img_call);
        call = findViewById(R.id.callus);
        tv_contact_us_no = findViewById(R.id.tv_contact_us_no);
        emailus = findViewById(R.id.email);

        tv_contact_us_no.setText(Constant.contact_us_no);

        final String num = "+91 9983830099";
        final String text = "Hello";

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri u = Uri.parse("tel:" + Constant.contact_us_no);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    s.printStackTrace();
                }
            }
        });

        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Uri u = Uri.parse("tel:" + Constant.contact_us_no);
//                Intent i = new Intent(Intent.ACTION_DIAL, u);
//                try {
//                    startActivity(i);
//                } catch (SecurityException s) {
//                    s.printStackTrace();
//                }

                boolean installed = isAppInstalled("com.whatsapp");

                if (installed)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+num+"&text="+ text));
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ContactUs.this, "Whatsapp is not installed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isAppInstalled(String s) {
        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }
}