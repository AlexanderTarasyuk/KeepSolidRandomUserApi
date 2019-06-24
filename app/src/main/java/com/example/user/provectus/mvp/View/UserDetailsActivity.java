package com.example.user.provectus.mvp.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.user.provectus.datamanager.remoteData.constants.Constants;
import com.example.user.provectus.datamanager.model.UserItem;
import com.example.user.provectus.R;
import com.example.user.provectus.manager.DateManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.Objects.*;

public class UserDetailsActivity extends AppCompatActivity {
    private static final int CALL_PERMISSION = 100;
    private UserItem userItem;

    @BindView(R.id.cv_user_big_image) CircleImageView circleImageViewContactAvatar;
    @BindView(R.id.tv_user_pib) TextView textViewContactFullNAME;
    @BindView(R.id.tv_user_age) TextView textViewContactAge;
    @BindView(R.id.apc_user_phone) AppCompatEditText appCompatEditTextContactPhone;
    @BindView(R.id.apc_user_mail) AppCompatEditText appCompatEditTextContactMail;
    @BindView(R.id.apc_user_birthday) AppCompatEditText appCompatEditTextContactBirthday;
    @BindView(R.id.btn_call) Button buttonCall;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private Bundle bundle;

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
//        init_view();
        setSupportActionBar(toolbar);

        requireNonNull(getSupportActionBar()).setTitle(R.string.user_info);
        requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        userItem = getIntent().getParcelableExtra(Constants.USER_KEY);
        if (userItem != null) {
            Glide
                    .with(this)
                    .load(userItem.picture.large)
                    .apply(Constants.GLIDE_OPTIONS)
                    .into(circleImageViewContactAvatar);

            textViewContactFullNAME.setText(userItem.name.first + " " + userItem.name.last);
            textViewContactAge.setText(userItem.dob.getAge() + " years old");

            appCompatEditTextContactMail.setText(userItem.email);
            appCompatEditTextContactBirthday.setText(new DateManager().getRightDate(userItem.dob.getDate()));
            appCompatEditTextContactPhone.setText(userItem.phone);

            buttonCall.setOnClickListener(view -> callByNumber(userItem.phone));
        }
    }


    private void callByNumber(String tell) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
            } else {
                startCall(tell);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void startCall(String tell) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + tell));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSION) {
            startCall(userItem.phone);
        }
    }

    private void init_view() {
        circleImageViewContactAvatar = findViewById(R.id.cv_user_big_image);
        textViewContactFullNAME = findViewById(R.id.tv_user_pib);
        textViewContactAge = findViewById(R.id.tv_user_age);
        appCompatEditTextContactPhone = findViewById(R.id.apc_user_phone);
        appCompatEditTextContactMail = findViewById(R.id.apc_user_mail);
        appCompatEditTextContactBirthday = findViewById(R.id.apc_user_birthday);
        buttonCall = findViewById(R.id.btn_call);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        bundle.putParcelable(getString(R.string.save), userItem);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userItem = savedInstanceState.getParcelable(getString(R.string.save));
        if (userItem != null) {
            Glide
                    .with(this)
                    .load(userItem.picture.large)
                    .apply(Constants.GLIDE_OPTIONS)
                    .into(circleImageViewContactAvatar);

            textViewContactFullNAME.setText(userItem.name.first + " " + userItem.name.last);
            textViewContactAge.setText(userItem.dob.getAge() + " years old");

            appCompatEditTextContactMail.setText(userItem.email);
            appCompatEditTextContactBirthday.setText(new DateManager().getRightDate(userItem.dob.getDate()));
            appCompatEditTextContactPhone.setText(userItem.phone);

            buttonCall.setOnClickListener(view -> callByNumber(userItem.phone));
        }
    }
}
