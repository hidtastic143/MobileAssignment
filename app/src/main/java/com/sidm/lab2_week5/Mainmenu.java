package com.sidm.lab2_week5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Mainmenu extends Activity implements OnClickListener{

    private Button btn_start;
    //private Button btn_help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainmenu);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);

//        btn_help = (Button)findViewById(R.id.btn_help);
//        btn_help.setOnClickListener(this);
    }
    public void onClick(View v) {
        Intent intent = new Intent();

        if (v == btn_start)
        {
            intent.setClass(this, Gamepage.class);
        }
//        else if(v == btn_help)
//        {
//            //intent.setClass(this, Helppage.class);
//        }

        startActivity(intent);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
