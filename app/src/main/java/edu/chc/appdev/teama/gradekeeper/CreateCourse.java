package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.jjobes.slidedaytimepicker.SlideDayTimeListener;
import com.github.jjobes.slidedaytimepicker.SlideDayTimePicker;

import java.util.ArrayList;

import edu.chc.appdev.teama.gradekeeper.ArrayAdapters.CourseMeetings;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class CreateCourse extends AppCompatActivity
{

    private SlideDayTimeListener listener;
    private ArrayList<String[]> meetingTimes;
    private CourseMeetings meetingTimesAdapter;

    public CreateCourse()
    {
        this.meetingTimes = new ArrayList<String[]>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        this.setTitle("Add course");
        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        listener = new SlideDayTimeListener() {

            @Override
            public void onDayTimeSet(int day, int hour, int minute)
            {
                // Do something with the day, hour and minute
                // the user has selected.
                CreateCourse.this.addMeetingTime(day, hour, minute);
            }

            @Override
            public void onDayTimeCancel()
            {
                // The user has canceled the dialog.
                // This override is optional.
            }
        };

        this.meetingTimesAdapter = new CourseMeetings(this, this.meetingTimes);
        ((ListView) this.findViewById(R.id.lvMeetings)).setAdapter(this.meetingTimesAdapter);
    }

    public void addMeetingTime(int day, int hour, int minute)
    {
        String[] item = {String.valueOf(day), hour + ":" + minute};
        this.meetingTimes.add(item);
        this.meetingTimesAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.createcourse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return true;
        }
    }

    public void showDateTimePicker(View view)
    {
        new SlideDayTimePicker.Builder(getSupportFragmentManager())
                .setListener(this.listener)
                .setInitialDay(1)
                .setInitialHour(13)
                .setInitialMinute(30)
                .setIs24HourTime(true)
                        //.setCustomDaysArray(getResources().getStringArray(R.array.days_of_week))
                        //.setTheme(SlideDayTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();
    }

    public void addCourseToDb(MenuItem menuItem)
    {
        DB db = new DB(this, null, null);

        db.addCourse(
            ((EditText) this.findViewById(R.id.txtName)).getText().toString(),
            ((EditText) this.findViewById(R.id.txtCode)).getText().toString(),
            ((EditText) this.findViewById(R.id.txtDescription)).getText().toString()
        );

        (Toast.makeText(this, "Added!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }
}
