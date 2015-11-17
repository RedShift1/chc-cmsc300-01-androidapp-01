package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class AddAssignmentActivity extends AppCompatActivity {

    private long id;
    private TextView txtDueDate;
    private Calendar dueDateCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        this.setTitle("Add Assignment");
        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        this.txtDueDate = (TextView) this.findViewById(R.id.txtDueDate);


        this.dueDateCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                dueDateCalendar.set(Calendar.YEAR, year);
                dueDateCalendar.set(Calendar.MONTH, monthOfYear);
                dueDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDueDate();
            }

        };


        txtDueDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(
                        AddAssignmentActivity.this,
                        date,
                        dueDateCalendar.get(Calendar.YEAR),
                        dueDateCalendar.get(Calendar.MONTH),
                        dueDateCalendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }


    private void updateDueDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, y", Locale.getDefault());
        txtDueDate.setText(sdf.format(this.dueDateCalendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.addassignment, menu);
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

    public void addAssignmentToDb(MenuItem menuItem)
    {
        DB db = new DB(this, null, null);

        Log.w("Test", ((TextView) this.findViewById(R.id.txtDueDate)).getText().toString());

        // There probably needs more conversion for the date
        db.addAssignmentToGradebook(
            this.id,
            ((EditText) this.findViewById(R.id.txtName)).getText().toString(),
            this.txtDueDate.getText().toString(),
            Float.parseFloat(((EditText) this.findViewById(R.id.txtPoints)).getText().toString())
        );

        (Toast.makeText(this, "Added!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }
}