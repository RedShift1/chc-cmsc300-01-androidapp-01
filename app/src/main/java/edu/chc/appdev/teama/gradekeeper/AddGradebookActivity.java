package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import edu.chc.appdev.teama.gradekeeper.FormValidator.EditTextValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.FormValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.ITextValidator;
import edu.chc.appdev.teama.gradekeeper.TextValidators.NotEmpty;

public class AddGradebookActivity extends AppCompatActivity
{
    private long id;

    private FormValidator validator;
    private SlideDayTimeListener listener;
    private ArrayList<String[]> meetingTimes;
    private CourseMeetings meetingTimesAdapter;

    public AddGradebookActivity()
    {
        this.meetingTimes = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gradebook);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);


        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Bundle extras = this.getIntent().getExtras();
        this.id = extras.getLong("_id");

        this.validator = new FormValidator();
        this.validator.addField(
            new EditTextValidator(
                (EditText) this.findViewById(R.id.etName),
                new ITextValidator[]{new NotEmpty()}
            )
        );


        this.listener = new SlideDayTimeListener() {

            @Override
            public void onDayTimeSet(int day, int hour, int minute)
            {
                AddGradebookActivity.this.addMeetingTime(day, hour, minute);
            }
        };

        this.meetingTimesAdapter = new CourseMeetings(this, this.meetingTimes);
        ((ListView) this.findViewById(R.id.lvMeetings)).setAdapter(this.meetingTimesAdapter);
    }

    public void addMeetingTime(int day, int hour, int minute)
    {
        String[] item =
        {
            String.valueOf(day),
            String.format("%02d:%02d", hour, minute)
        };
        this.meetingTimes.add(item);
        this.meetingTimesAdapter.notifyDataSetChanged();
    }


    public void showDateTimePicker(View view)
    {
        new SlideDayTimePicker.Builder(this.getSupportFragmentManager())
            .setListener(this.listener)
            .setInitialDay(1)
            .setInitialHour(13)
            .setInitialMinute(30)
            .setIs24HourTime(true)
            .build()
            .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.addgradebook, menu);
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


    public void addGradebookToDB(MenuItem menuItem)
    {
        if(!this.validator.isValid())
        {
            (Toast.makeText(this, "Form contains errors", Toast.LENGTH_LONG)).show();
            return;
        }

        DB db = DB.getInstance(this);
        long gradebookId = db.addGradebookToCourse(this.id,
                ((EditText) this.findViewById(R.id.etName)).getText().toString());

        for(String[] meetingTime : this.meetingTimes)
        {
            db.addMeetingTimeToGradebook(
                gradebookId,
                Integer.parseInt(meetingTime[0]),
                meetingTime[1]
            );
        }

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }
}
