package com.example.jetpack;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.jetpack.fragment.HomeFragment;
import com.example.jetpack.fragment.MenuFragment;
import com.example.jetpack.fragment.UserFragment;
import com.example.jetpack.util.ImageActivity;
import com.jaeger.library.StatusBarUtil;
import com.luck.picture.lib.tools.SPUtils;

import java.util.Observable;
import java.util.Observer;


/**
 * 
 */
public class MainActivity extends AppCompatActivity {
    private Activity myActivity;
    private TextView tvTitle;
    private LinearLayout llContent;
    private RadioButton rbHome;
    private RadioButton rbUpload;
    private RadioButton rbUser;
    final Fragment[] fragments = new Fragment[]{null, null,null};//存放Fragment
    public static Integer userId;
    public static TextView tvSave;
    public RelativeLayout titleParent;
    public static Observer observer;


    @Override
    public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            myActivity = this;
            setContentView(R.layout.activity_main);
            tvTitle = (TextView) findViewById(R.id.title);
            llContent = (LinearLayout) findViewById(R.id.ll_main_content);
            rbHome = (RadioButton) findViewById(R.id.rb_main_home);
            rbUpload = (RadioButton) findViewById(R.id.rb_main_upload);
            rbUser = (RadioButton) findViewById(R.id.rb_main_user);
            tvSave = findViewById(R.id.save);
            userId = getIntent().getIntExtra("userId", 0);

        initView();
        setViewListener();

        
        observer = (o, arg) -> {
            int color = (int) arg;
            StatusBarUtil.setColor(MainActivity.this, ContextCompat.getColor(myActivity, color), 0);
            titleParent.setBackgroundColor(ContextCompat.getColor(myActivity,color));
        };
        int cacheColor = SPUtils.getInstance().getInt("color");
        if (cacheColor != -1) {
            observer.update(new Observable(), cacheColor);
        }

    }
    private void setViewListener() {
        rbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText("Home");
                switchFragment(0);
                tvSave.setVisibility(View.GONE);
            }
        });
        rbUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText("Upload");
                switchFragment(1);
                tvSave.setVisibility(View.GONE);
            }
        });
        rbUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTitle.setText("My Profile");
                switchFragment(2);
                tvSave.setVisibility(View.VISIBLE);
            }
        });


    }

    private void initView() {
        
        Drawable iconHome=getResources().getDrawable(R.drawable.selector_main_rb_home);
        iconHome.setBounds(0,0,68,68);
        rbHome.setCompoundDrawables(null,iconHome,null,null);
        rbHome.setCompoundDrawablePadding(5);
        Drawable iconUpload=getResources().getDrawable(R.drawable.selector_main_rb_upload);
        iconUpload.setBounds(0,0,68,68);
        rbUpload.setCompoundDrawables(null,iconUpload,null,null);
        rbUpload.setCompoundDrawablePadding(5);
        Drawable iconUser=getResources().getDrawable(R.drawable.selector_main_rb_user);
        iconUser.setBounds(0,0,68,68);
        rbUser.setCompoundDrawables(null,iconUser,null,null);
        rbUser.setCompoundDrawablePadding(5);
        switchFragment(0);
        rbHome.setChecked(true);
    }

    private void switchFragment(int fragmentIndex) {
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (fragments[fragmentIndex] == null) {
            switch (fragmentIndex) {
                case 0:
                    fragments[fragmentIndex] = new HomeFragment();
                    break;
                case 1:
                    fragments[fragmentIndex] = new MenuFragment();
                    break;
                case 2:
                    fragments[fragmentIndex] = new UserFragment();
                    break;
            }
            transaction.add(R.id.ll_main_content, fragments[fragmentIndex]);

        }

        for (int i = 0; i < fragments.length; i++) {
            if (fragmentIndex != i && fragments[i] != null) {
                transaction.hide(fragments[i]);
            }
        }
        transaction.show(fragments[fragmentIndex]);

        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
        }

        return false;
    }

    private long time = 0;

    public void exit() {
        if (System.currentTimeMillis() - time > 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(myActivity,"Click again to exit the app", Toast.LENGTH_LONG).show();
        } else {
            finish();
        }

    }

    // item details method made by Yuxiang
    public void car_detailActivity(View view) {
        Intent intent = new Intent(this, activity_car_detail.class);
        startActivity(intent);
    }
    //item details method made by Yuxiang
    public void stack_detailActivity(View view) {
        Intent intent = new Intent(this, activity_stack_detail.class);
        startActivity(intent);
    }
    //Top right back button on post page
    public void back(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void openImageActivity(View view) {
        Intent intent = new Intent (this, ImageActivity.class);
        startActivity(intent);
    }
}
