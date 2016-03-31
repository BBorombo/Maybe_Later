package borombo.com.maybelater;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView, timeView;
    private EditText msg;
    private int year, month, day, hour, min;

    AlarmManager am;

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showDate(year,monthOfYear,dayOfMonth);
        }
    };

    TimePickerDialog.OnTimeSetListener t =new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            min = minute;
            showTime(hourOfDay, minute);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        msg = (EditText) findViewById(R.id.edit);

        Button setDate = (Button) findViewById(R.id.buttonDate);
        Button setTime = (Button) findViewById(R.id.buttonTime);
        Button setNotif = (Button) findViewById(R.id.notif);


        dateView = (TextView) findViewById(R.id.date);
        timeView = (TextView) findViewById(R.id.time);
        calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        showDate(year, month+1, day);
        showTime(hour,min);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity.this,t, hour, min, true).show();
            }
        });
        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, d, year, month, day).show();
            }
        });
        setNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterAlarme(1,year,month,day,hour,min);
            }
        });

    }

    private void showDate(int year, int month, int day) {
        dateView.setText(day + "/" + month + "/" + year);
    }

    private void showTime(int hour, int min) {
        String m = String.valueOf(min);
        if (min < 10)
            m = "0" + min;
        timeView.setText(hour + "h" + m );
    }

    public void ajouterAlarme(int id, int year, int month, int day, int hour, int minute)
    {
        Calendar cal = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 1);
        cal.set(year, month, day, hour, minute,0);
        Date d = new Date(cal.getTimeInMillis());
        Date d_ = new Date(c.getTimeInMillis());

        Log.d("Message", String.valueOf(msg.getText()));

        Log.d("SET ", d.toString());
        Log.d("NORMAL ", d_.toString());
//        cal.add(Calendar.SECOND, 10);
        Intent intent = new Intent(this, TimeAlarm.class);
        intent.putExtra("MESSAGE", msg.getText());
        PendingIntent operation = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_ONE_SHOT);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), operation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
