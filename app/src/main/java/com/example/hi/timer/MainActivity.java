package com.example.hi.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    //生命两个按钮控件
    private Button startbutton = null;
    private Button endbutton = null;
    private Button pause = null;
    private EditText hour = null;
    private EditText minute = null;
    private EditText second = null;
    private boolean only = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //根据控件的id得到代表控件的对象，并为这两个按钮设置监听器
        startbutton = (Button) findViewById(R.id.startbutton);
        endbutton = (Button) findViewById(R.id.endbutton);
        pause = (Button) findViewById(R.id.pause);
        hour = (EditText) findViewById(R.id.hour);
        minute = (EditText) findViewById(R.id.minute);
        second = (EditText) findViewById(R.id.second);
        startbutton.setOnClickListener(new startbuttonListener());
        endbutton.setOnClickListener(new endbuttonListener());
        pause.setOnClickListener(new pauseListener());
        hour.setOnTouchListener(new hourListener());
        minute.setOnTouchListener(new minuteListener());
        second.setOnTouchListener(new secondListener());
        hour.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        minute.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        second.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    class hourListener implements OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            if(hour.getText().toString().equals("0")){
                hour.setText("");
            }
            if(minute.getText().length()==0){
                minute.setText("0");
            }
            if(second.getText().length()==0){
                second.setText("0");
            }
            return false;
        }
    }

    class minuteListener implements OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            if(hour.getText().length()==0){
                hour.setText("0");
            }
            if(minute.getText().toString().equals("0")){
                minute.setText("");
            }
            if(second.getText().length()==0){
                second.setText("0");
            }
            return false;
        }
    }

    class secondListener implements OnTouchListener {
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            if(hour.getText().length()==0){
                hour.setText("0");
            }
            if(minute.getText().length()==0){
                minute.setText("0");
            }
            if(second.getText().toString().equals("0")){
                second.setText("");
            }
            return false;
        }
    }

    class startbuttonListener implements OnClickListener {
        public void onClick(View v) {
        // TODO Auto-generated method stub
            if(only == false) {
                if(hour.getText().length()==0){
                    hour.setText("0");
                }
                if(minute.getText().length()==0){
                    minute.setText("0");
                }
                if(second.getText().length()==0){
                    second.setText("0");
                }
                Second = Integer.parseInt(second.getText().toString());
                Minute = Integer.parseInt(minute.getText().toString());
                Hour = Integer.parseInt(hour.getText().toString());
                if (Second < 0 || Second > 59) {
                    Toast.makeText(MainActivity.this, "输入有误", Toast.LENGTH_LONG).show();
                } else {
                    Seconds = Second;
                }
                if (Minute < 0 || Minute > 59) {
                    Toast.makeText(MainActivity.this, "输入有误", Toast.LENGTH_LONG).show();
                } else {
                    Minutes = Minute;
                }
                if (Hour < 0 || Hour > 23) {
                    Toast.makeText(MainActivity.this, "输入有误", Toast.LENGTH_LONG).show();
                } else {
                    Hours = Hour;
                }
                if (Hours!=0||Minutes!=0||Seconds!=0) {
                    //调用Handler的post方法，将要执行的线程对象添加到队列当中
                    handler.post(updateThread);
                    startbutton.setText("暂停");
                    endbutton.setText("结束");
                    hour.setEnabled(false);
                    minute.setEnabled(false);
                    second.setEnabled(false);
                    only = true;
                }
            }else{
                handler.removeCallbacks(updateThread);
                startbutton.setText("继续");
                hour.setEnabled(true);
                minute.setEnabled(true);
                second.setEnabled(true);
                only = false;
            }
        }
    }

    class endbuttonListener implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            handler.removeCallbacks(updateThread);
            hour.setText("0");
            minute.setText("0");
            second.setText("0");
            hour.setEnabled(true);
            minute.setEnabled(true);
            second.setEnabled(true);
            startbutton.setText("开始");
            endbutton.setText("清空");
            only = false;
        }
    }

    class pauseListener implements OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            finish();
        }
    }

    //创建一个Handler对象
    Handler handler = new Handler();
    int Hours, Minutes, Seconds, Hour, Minute, Second;
    //将要执行的操作写在线程对象的run方法当中
    Runnable updateThread = new Runnable() {
        public void run() {
        // TODO Auto-generated method stub
            //判断时间，并进行加减
            if (Seconds > 0) {
                Seconds -= 1;
            } else if (Minutes > 0) {
                Minutes -= 1;
                Seconds = 59;
            } else if (Hours > 0) {
                Hours -= 1;
                Minutes = 59;
                Seconds = 59;
            }
            //设置文本框为显示时间
            hour.setText(Hours + "");
            minute.setText(Minutes + "");
            second.setText(Seconds + "");
            //操作完成后始终保持光标在最后一位
//            hour.setSelection(hour.getText().length());
//            minute.setSelection(minute.getText().length());
//            second.setSelection(second.getText().length());
            //在run方法内部post或postDelayed方法
            handler.postDelayed(updateThread, 1000);
            if (Hours==0&&Minutes==0&&Seconds==0) {
                handler.removeCallbacks(updateThread);
                hour.setEnabled(true);
                minute.setEnabled(true);
                second.setEnabled(true);
                only = false;
            }
        }
    };
}