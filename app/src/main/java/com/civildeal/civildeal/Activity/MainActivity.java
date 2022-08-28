package com.civildeal.civildeal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.civildeal.civildeal.Adapter.MyCartAdapter;
import com.civildeal.civildeal.Fragments.HistoryFragments;
import com.civildeal.civildeal.Fragments.HomeFragment;
import com.civildeal.civildeal.Fragments.LeadsFragment;
import com.civildeal.civildeal.Fragments.PostRequrimentsFragment;
import com.civildeal.civildeal.Prefrences.SharedPrefForLocation;
import com.civildeal.civildeal.Prefrences.SharedPrefManager;
import com.civildeal.civildeal.R;
import com.civildeal.civildeal.Service.MyFirebaseMessagingService;
import com.civildeal.civildeal.Util.Config;
import com.civildeal.civildeal.Util.NotificationUtils;
import com.civildeal.civildeal.api.Api_Client;
import com.civildeal.civildeal.api.ModelResponse.GetVendorProfilePicResponse;
import com.civildeal.civildeal.api.ModelResponse.GetvendorProfilePicResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResponse;
import com.civildeal.civildeal.api.ModelResponse.ItemAddToCartResultResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResponse;
import com.civildeal.civildeal.api.ModelResponse.ProfileResultResponse;
import com.civildeal.civildeal.api.ModelResponse.SwitchPromoterResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResponse;
import com.civildeal.civildeal.api.ModelResponse.WalletResultResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    public TextView countTv;
    static String count_no ;
    ImageButton imageButton;
    MenuItem cartIconMenuItem;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    TextView username,userlastname,usermobile,walletamount,city;
    ImageView userImage;
    RecyclerView recyclerView;
    String vendor_id,vendor_type,vendor_image,user_type;

    SharedPrefManager sharedPrefManager;
    SharedPreferences.Editor editor;
    String name,mobile,lastname,Location;
    int userimage;
    Integer x =0;
    SharedPrefForLocation sharedPrefForLocation;
    MenuItem menuItem;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment()).commit();

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);


        sharedPrefManager = new SharedPrefManager(getApplicationContext());


        vendor_id = String.valueOf(sharedPrefManager.getUserId());
        vendor_type = sharedPrefManager.getVendorType();
//        Toast.makeText(this, "vendor_type "+vendor_type, Toast.LENGTH_SHORT).show();
        user_type = sharedPrefManager.getUserType();
//        Toast.makeText(this, "user_type "+user_type, Toast.LENGTH_SHORT).show();

        vendor_image = sharedPrefManager.getUserimage();
//        Toast.makeText(this, "name"+sharedPrefManager.getUserName(), Toast.LENGTH_SHORT).show();

//        userimage = Integer.parseInt(sharedPrefManager.getUserimage());

        sharedPrefForLocation = new SharedPrefForLocation(getApplicationContext());

//        Toast.makeText(this, ""+sharedPrefManager.getPromoterId(), Toast.LENGTH_SHORT).show();

        MyFirebaseMessagingService.getToken(MainActivity.this);
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
//            String newToken = instanceIdResult.getToken();
//            Log.e("newToken", newToken);
//            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//            SharedPreferences.Editor editor = pref.edit();
//            editor.putString("regId", newToken);
//            //Toast.makeText(this, ""+newToken, Toast.LENGTH_SHORT).show();
//            editor.commit();
//            editor.apply();
//        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

              /*      NotificationUtils notificationUtils = new NotificationUtils(MainActivity.this);

                    notificationUtils.showNotificationMessage(message,message,message,new Intent(MainActivity.this,MainActivity.class));
              */
                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getCartList();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                updateNavigationViewHeader();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        hideMenuItem();



        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        //bottomNavigationView.menu.getItem(2).isEnabled() = false;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.mihome:
                        fragment = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
                        break;
                    case R.id.leads:
                        fragment = new LeadsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                        break;
                    case R.id.post_requirments:
                        fragment = new PostRequrimentsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                        break;
                    case R.id.history:
                        fragment = new HistoryFragments();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
                        break;

                }

                return true;
            }
        });

        floatingActionButton = findViewById(R.id.floating_btn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void hideMenuItem() {

        Menu nav_Menu = navigationView.getMenu();
        if (user_type.equals("2") || user_type.equals("5")){
            nav_Menu.findItem(R.id.bid_submission).setVisible(true);
            Log.d("tag","bid");
        }else {
            Log.d("tag","fail");
        }
            if (user_type.equals("3") || user_type.equals("2") || user_type.equals("5") || user_type.equals("10") ){
            nav_Menu.findItem(R.id.check_bid).setVisible(true);

        }else {
            Log.d("tag","bid_fail");
        }

    }

    private void getCartList() {

        Call<ItemAddToCartResultResponse> call = Api_Client
                .getInstance()
                .getCartList(vendor_id,vendor_type);
        call.enqueue(new Callback<ItemAddToCartResultResponse>() {
            @Override
            public void onResponse(Call<ItemAddToCartResultResponse> call, Response<ItemAddToCartResultResponse> response) {

                if(response.body().getCode().equals(200)) {
                    try {
                        ItemAddToCartResultResponse itemAddToCartResultResponse = response.body();
                        List<ItemAddToCartResponse> myCartList = itemAddToCartResultResponse.getData();
                        MyCartAdapter myCartAdapter = new MyCartAdapter(MainActivity.this,myCartList);

                        count_no = countTv.getText().toString();
                        Integer x = myCartList.size();
                        count_no = x.toString();
                        countTv.setText(count_no);
//                    Toast.makeText(MainActivity.this, "count "+count_no, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }






                }
                else
                {
//                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ItemAddToCartResultResponse> call, Throwable t) {

                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }

    private void updateNavigationViewHeader() {

        View header = navigationView.getHeaderView(0);
        username = header.findViewById(R.id.username);
        userlastname = header.findViewById(R.id.userLastname);
        usermobile = header.findViewById(R.id.userMobile);
        userImage = header.findViewById(R.id.userimage);
        walletamount = header.findViewById(R.id.walletbalance);
        city = header.findViewById(R.id.city);

        city.setText(" " +sharedPrefManager.getLocation());

//        if (!sharedPrefForLocation.getname().equals("0") && !sharedPrefForLocation.getlastname().equals("0")){
//
//            username.setText(sharedPrefForLocation.getname()+" "+sharedPrefForLocation.getlastname());
//            usermobile.setText(sharedPrefForLocation.getmobile());
//        }else {
//
//            username.setText(sharedPrefManager.getUserName()+ " "+sharedPrefManager.getUserLastName());
//            usermobile.setText(sharedPrefManager.getUserMobile());
//        }



        getProfilePic();
        loadingProfile();


        getWalletamount();

    }

    private void loadingProfile(){

        Call<ProfileResultResponse> call = Api_Client
                .getInstance()
                .getProfileDetails(vendor_id);
        call.enqueue(new Callback<ProfileResultResponse>() {
            @Override
            public void onResponse(Call<ProfileResultResponse> call, Response<ProfileResultResponse> response) {

                if(response.body().getCode().equals(200)) {
                    try {
                        //                    progressDialog.dismiss();
                        ProfileResultResponse profileResultResponse = response.body();
                        List<ProfileResponse> profiledetails = profileResultResponse.getData();

                        username.setText(profiledetails.get(0).getName()+ " "+profiledetails.get(0).getLastname());
                        usermobile.setText(profiledetails.get(0).getMobile());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }



                }
                else
                {
//                    progressDialog.dismiss();
//                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("tag","fail "+response.body().getMessage());
                }

            }

            @Override
            public void onFailure(Call<ProfileResultResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tag","failure "+t.getLocalizedMessage());

            }
        });


    }

    private void getProfilePic() {
        Call<GetvendorProfilePicResultResponse> call = Api_Client
                .getInstance()
                .getVendorImage(vendor_id);
        call.enqueue(new Callback<GetvendorProfilePicResultResponse>() {
            @Override
            public void onResponse(Call<GetvendorProfilePicResultResponse> call, Response<GetvendorProfilePicResultResponse> response) {

                if (response.body().getCode().equals(200)){
                    try {
                        GetvendorProfilePicResultResponse getvendorProfilePicResultResponse = response.body();
                        List<GetVendorProfilePicResponse> profileImage = Collections.singletonList(getvendorProfilePicResultResponse.getData());
                        Picasso.get()
                                .load(profileImage.get(0).getImagePath()+profileImage.get(0).getUserimage())
                                .error(R.drawable.icon_image)
                                .placeholder(R.drawable.icon_image)
                                .into(userImage);
//                    Log.e("tag","image "+response.body().getData());
//                    Log.e("tag","image "+profileImage.get(0).getImagePath());
//                    Log.e("tag","image "+profileImage.get(0).getUserimage());
                    }catch (Exception e){
                        Log.d("tag","Exception "+response.body().getMessage());
                    }


                }else {

                    Log.e("tag",""+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<GetvendorProfilePicResultResponse> call, Throwable t) {
                Log.e("tag",""+t.getLocalizedMessage());

            }
        });
    }



    private void getWalletamount() {

        Call<WalletResultResponse> call = Api_Client
                .getInstance()
                .getWallet(vendor_id);
        call.enqueue(new Callback<WalletResultResponse>() {
            @Override
            public void onResponse(Call<WalletResultResponse> call, Response<WalletResultResponse> response) {

                if (response.body().getCode().equals(200)){

                    WalletResultResponse walletResultResponse = response.body();
                    List<WalletResponse> walletList = Collections.singletonList(walletResultResponse.getData());

                    walletamount.setText("₹ " +walletList.get(0).getWalletAmount().toString());
//                    Log.e("tag","amount "+walletList.get(0).getWalletAmount());
//                    Log.e("tag","wallet "+response.body().getMessage());

                }else if (response.body().getCode().equals(400)){

                    walletamount.setText("0");
                    Log.e("tag","fail "+response.body().getMessage());
                }


            }

            @Override
            public void onFailure(Call<WalletResultResponse> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);
        cartIconMenuItem = menu.findItem(R.id.cart);
        View actionView = cartIconMenuItem.getActionView();
//        Log.e("tag1","menu"+actionView);

        if (actionView!=null){
            Log.e("tag", String.valueOf("count"+actionView!=null));
            countTv=actionView.findViewById(R.id.count_tv);
            imageButton=actionView.findViewById(R.id.cart_ic_image);



            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //countTv.setText("2");
//                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Cart.class);
                    startActivity(intent);
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.cart) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
//            Util.hideSoftKeyboard(this);
            super.onBackPressed();
        }


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        Menu nav_menu = navigationView.getMenu();
        Fragment fragment = null;
        if (id == R.id.Home) {
        } else if (id == R.id.buyleads) {
            fragment = new LeadsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        } else if (id == R.id.transHistrory) {
            fragment = new HistoryFragments();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).addToBackStack(null).commit();
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }else if (id == R.id.mycart) {
            startActivity(new Intent(MainActivity.this, Cart.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }else if (id == R.id.project_post) {
            startActivity(new Intent(MainActivity.this, ProjectPost.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }else if (id == R.id.switchPromoter) {

//            startActivity(new Intent(MainActivity.this, SwitchPromoter.class));
//            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
            showPromoterDialog();


        }
        else if (id == R.id.addLead) {
            startActivity(new Intent(MainActivity.this, AddPromoterLead.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }

        else if (id == R.id.addedLead) {
            startActivity(new Intent(MainActivity.this, PromoterAddedLead.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }
        else if (id == R.id.check_bid) {
            startActivity(new Intent(MainActivity.this, CheckBid.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }
        else if (id == R.id.bid_submission) {
            startActivity(new Intent(MainActivity.this, BidSubmission.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }
        else if (id == R.id.inbox) {
            startActivity(new Intent(MainActivity.this, Inbox.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }

        else if (id == R.id.notification) {
            showNotificationDialog();
//            startActivity(new Intent(MainActivity.this, Notification.class));
//            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }else if (id == R.id.feedback) {
            showFeedbackDialog();

        }else if (id == R.id.helpandsupport) {
            startActivity(new Intent(MainActivity.this, HelpAndSupport.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        }else if (id == R.id.contactUs) {
            startActivity(new Intent(MainActivity.this, ContactUs.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        }
        else if (id == R.id.share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey, I strongly recommend CivilDeal App for all your business needs. Click to download: https://play.google.com/store/apps/details?id=com.civildeal.civildeal");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
        }
        else if (id == R.id.logout) {
            showLogoutDialog();

        }else if (id == R.id.freelisting){

            startActivity(new Intent(MainActivity.this, FreeListing.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }else if (id == R.id.about){

            startActivity(new Intent(MainActivity.this, AboutCivilDeal.class));
            overridePendingTransition(R.anim.slide_left, R.anim.slide_right);

        }
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

            drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void showPromoterDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Switch As Promoter");
        dialog.setMessage("Do you want to switch as promoter");


//        LayoutInflater inflater = LayoutInflater.from(this);
//        View reg_layout = inflater.inflate(R.layout.notification_dialog,null);
////        final EditText editText = reg_layout.findViewById(R.id.feedback);
//
//        dialog.setView(reg_layout);

        //set button

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switchAsPromoter();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void switchAsPromoter() {
        Log.d("tag",""+vendor_id);
        
        Call<SwitchPromoterResponse> call = Api_Client
                .getInstance()
                .registerPromoter(vendor_id);
        call.enqueue(new Callback<SwitchPromoterResponse>() {
            @Override
            public void onResponse(Call<SwitchPromoterResponse> call, Response<SwitchPromoterResponse> response) {
                
                if (response.body().getMessage().equals("Registered Promoter Successfully")){


                    Toast.makeText(MainActivity.this, "Request sent to admin", Toast.LENGTH_SHORT).show();
                    Log.d("tag","Request "+response.body().getMessage());



                }
                else if (response.body().getMessage().equals("You are not approved from admin side")){

                    Toast.makeText(MainActivity.this, "Please wait for admin approval", Toast.LENGTH_SHORT).show();
                    Log.d("tag","approval "+response.body().getMessage());

                }else if (response.body().getMessage().equals("Now You are a Promoter")){
                    SwitchPromoterResponse switchPromoterResponse = response.body();

                    sharedPrefManager.savePromoterId(switchPromoterResponse.getPromoterId());

                    startActivity(new Intent(MainActivity.this, SwitchPromoter.class));
                    overridePendingTransition(R.anim.slide_left, R.anim.slide_right);
                    Log.d("tag","Promoter "+response.body().getMessage());
                }else {
                    Log.d("tag","fail "+response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SwitchPromoterResponse> call, Throwable t) {

                Log.d("tag","failuer "+t.getLocalizedMessage());

            }
        });
        
    }

    private void showNotificationDialog() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Notification");


        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.notification_dialog,null);
//        final EditText editText = reg_layout.findViewById(R.id.feedback);

        dialog.setView(reg_layout);

        //set button

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (TextUtils.isEmpty(editText.getText().toString())){
//                    Toast.makeText(MainActivity.this, "plz type ur feedback", Toast.LENGTH_SHORT).show();
//                    return;
//                }
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void updateCount()
    {
        count_no = countTv.getText().toString();
        x = Integer.parseInt(count_no);
        x++;
        count_no = x.toString();

        countTv.setText(count_no);

    }

    private void showFeedbackDialog(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Feedback form");
        dialog.setMessage("Provide us your valuable feedback");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_layout = inflater.inflate(R.layout.feedback_dialog,null);
        final EditText editText = reg_layout.findViewById(R.id.feedback);

        dialog.setView(reg_layout);

        //set button

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(MainActivity.this, "plz type ur feedback", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showLogoutDialog(){

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Log Out");
        dialog.setMessage("Are you sure you want to Log Out?");



        //set button

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//
                sharedPrefManager.logout();
                Intent intent = new Intent(MainActivity.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Location = sharedPrefForLocation.getSelectedLocation();
//                Log.e("tag","location "+Location);
                Toast.makeText(MainActivity.this, "You have been logout", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


//    @Override
//    public void onClickBuyNow(RecyclerView.ViewHolder vh, Object item, int pos) {
//
//        String x= String.valueOf(pos);
//
//        Toast.makeText(this, "item added"+pos, Toast.LENGTH_SHORT).show();
//        //countTv.setText(Integer.toString(pos));
//        countTv.setText(x);
//    }

    @Override
    protected void onRestart() {

//        countTv.setText(String.valueOf(Constant.count));

        super.onRestart();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        //  Toast.makeText(this, ""+regId, Toast.LENGTH_SHORT).show();

        Log.e("TAG", "Firebase reg id: " + regId);

    }

    @Override
    protected void onResume() {
        getCartList();
        invalidateOptionsMenu();
        name = sharedPrefManager.getUserName().toString();
        lastname = sharedPrefManager.getUserLastName().toString();
        Log.e("onResume","lastName "+lastname);
        Log.e("onResume","name "+name);
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onNightModeChanged(int mode) {
        super.onNightModeChanged(mode);
    }
}