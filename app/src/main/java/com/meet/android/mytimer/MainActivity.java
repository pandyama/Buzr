package com.meet.android.mytimer;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static java.time.LocalDateTime.now;

public class MainActivity extends FragmentActivity {

    final Context context = this;

    Button From;
    Button To;
    Button Interval;

    TextView e1;
    TextView e2;
    TextView e3;

    int fromHour;
    int fromMin;
    int toHour;
    int toMin;

    private static long fromMillis;
    private static long toMillis;
    private static long currentMillis;

    private static String IntervalTime;
    private static long NumberIntervalTime = 0;

    private static boolean flag;
    private static boolean oneFlag;
    private static boolean currentDay = false;
    private static boolean currentDay2 = false;
    private static boolean currentDay3 = false;
    private static boolean currentDay4 = false;
    private static boolean currentDay5 = false;
    private static boolean currentDay6 = false;
    private static boolean currentDay7 = false;

    Calendar calendar = Calendar.getInstance();
    int currentHour = calendar.get( Calendar.HOUR_OF_DAY );
    int currentMinute = calendar.get( Calendar.MINUTE );
    int day;

    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cb5;
    CheckBox cb6;
    CheckBox cb7;

    View.OnClickListener checkBoxListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        currentMillis = currentHour*3600*1000;
        currentMillis = currentMillis + (currentMinute*60000);
        day = calendar.get(Calendar.DAY_OF_WEEK);

        From = findViewById( R.id.button );
        To   = findViewById( R.id.button2 );
        Interval = findViewById(R.id.button3);

        e1   = findViewById( R.id.textView);
        e2   = findViewById( R.id.textView2);

        cb1  = findViewById(R.id.checkBox1);
        cb2  = findViewById(R.id.checkBox2);
        cb3  = findViewById(R.id.checkBox3);
        cb4  = findViewById(R.id.checkBox4);
        cb5  = findViewById(R.id.checkBox5);
        cb6  = findViewById(R.id.checkBox6);
        cb7  = findViewById(R.id.checkBox7);



        Interval.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptView = li.inflate(R.layout.prompts,null);

                AlertDialog.Builder adb = new AlertDialog.Builder(context);

                adb.setView(promptView);

                final EditText userInput = promptView.findViewById(R.id.editText1);
                e3 = findViewById(R.id.textView3);

                adb.setCancelable(false)
                        .setPositiveButton( "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //EditText userInput = findViewById(R.id.editText1);
                                        e3.setText(userInput.getText());
                                        IntervalTime = String.valueOf(userInput.getText());
                                        NumberIntervalTime = Long.parseLong(IntervalTime);
                                        checkStatus();
                                    }
                                } )
                        .setNegativeButton( "Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                } );
                AlertDialog adb1 = adb.create();
                adb1.show();
            }
        } );

        //CheckBox Listener
        checkBoxListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb1.isChecked() || cb2.isChecked() || cb3.isChecked() || cb4.isChecked() || cb5.isChecked() || cb6.isChecked() || cb7.isChecked()){
                    oneFlag = true;
                    if(day == 1 && cb1.isChecked()){
                        currentDay = true;
                        checkStatus();
                    }
                    else{
                        if(currentDay) {
                            stopAlarm();
                        }
                    }

                    if(day == 2 && cb2.isChecked()){
                        currentDay2 = true;
                        checkStatus();
                    }
                    else{
                        if(currentDay2) {
                            stopAlarm();
                        }
                    }

                    if(day == 3 && cb3.isChecked()){
                        currentDay3 = true;
                        checkStatus();
                    }
                    else{
                        if(currentDay3) {
                            stopAlarm();
                        }
                    }

                    if(day == 4 && cb4.isChecked()){
                        currentDay4 = true;
                        checkStatus();
                    }
                    else{
                        if(currentDay4) {
                            stopAlarm();
                        }
                    }

                    if(day == 5 && cb5.isChecked()){
                        currentDay5 = true;
                        checkStatus();
                    }
                    else{
                        if(currentDay5) {
                            stopAlarm();
                        }
                    }

                    if(day == 6 && cb6.isChecked()){
                        currentDay6 = true;
                        checkStatus();
                    }
                    else{
                        if(currentDay6) {
                            stopAlarm();
                        }
                    }

                    if(day == 7 && cb7.isChecked()){
                        currentDay7 = true;
                        checkStatus();
                    }
                    else{
                        if(currentDay7) {
                            stopAlarm();
                        }
                    }
                }
                else{
                    oneFlag = false;
                    stopAlarm();
                }
            }
        };

        cb1.setOnClickListener(checkBoxListener);
        cb2.setOnClickListener(checkBoxListener);
        cb3.setOnClickListener(checkBoxListener);
        cb4.setOnClickListener(checkBoxListener);
        cb5.setOnClickListener(checkBoxListener);
        cb6.setOnClickListener(checkBoxListener);
        cb7.setOnClickListener(checkBoxListener);

        //From and To Buttons on click listener
        From.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog;

                dialog = new TimePickerDialog( MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        fromHour = hour;
                        fromMin = minute;

                        fromMillis = hour * 3600 * 1000;
                        fromMillis = fromMillis + (minute * 60000);

                        if (hour > 12 && minute < 10) {
                            hour = hour - 12;
                            e1.setText( hour + ":0" + minute );
                        } else if (hour > 12 && minute > 10) {
                            hour = hour - 12;
                            e1.setText( hour + ":" + minute );
                        } else if (hour < 12 && minute < 10) {
                            e1.setText( hour + ":0" + minute );
                        } else {
                            e1.setText( hour + ":" + minute );
                        }
                        checkStatus();
                    }
                }, 0, 0, false );

                dialog.show();
            }
        } );

        To.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog;

                dialog = new TimePickerDialog( MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        toHour = hour;
                        toMin = minute;

                        toMillis = hour*3600*1000;
                        toMillis = toMillis + (minute*60000);

                        if (hour > 12 && minute < 10) {
                            hour = hour - 12;
                            e2.setText( hour + ":0" + minute );

                        } else if (hour > 12 && minute > 10) {
                            hour = hour - 12;
                            e2.setText( hour + ":" + minute );
                        } else if (hour < 12 && minute < 10) {
                            e2.setText( hour + ":0" + minute );
                        } else {
                            e2.setText( hour + ":" + minute );
                        }
                        checkStatus();
                    }
                }, 0, 0, false );
                dialog.show();
            }
        } );

    }

    public static boolean getData(){
        return MainActivity.flag;
    }

    public static long getFrom(){
        return MainActivity.fromMillis;
    }

    public static long getTo(){
        return MainActivity.toMillis;
    }

    public static long getCurrentMillis(){
        return MainActivity.currentMillis;
    }

    public static long getInterval(){
        return NumberIntervalTime;
    }


// Check Status called at every trigger based on From and To button or Days selection
    private void checkStatus(){

        if((fromMillis <= currentMillis && toMillis >= currentMillis) && oneFlag && NumberIntervalTime > 0){
            startAlarm();
        }
        else{
            stopAlarm();
        }
    }

    private void stopAlarm(){
        //NumberIntervalTime = 0;
        MainActivity.flag = false;
    }

    private void startAlarm(){
        MainActivity.flag = true;
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent ami = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pmi = PendingIntent.getBroadcast(MainActivity.this,0, ami,0);

        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+(NumberIntervalTime*1000),pmi);

    }

}

