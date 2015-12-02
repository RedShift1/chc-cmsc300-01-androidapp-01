package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentAutocomplete;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class AddStudent extends AppCompatActivity {

    private DB db;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);


        this.db = DB.getInstance(this);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        this.setTitle("Add Student");
        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        AutoCompleteTextView atvStudent = (AutoCompleteTextView) this.findViewById(R.id.atvStudent);
        atvStudent.setAdapter(new StudentAutocomplete(this, db.getStudentsCursor(), 0, db));
        atvStudent.setThreshold(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.addstudent, menu);
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

    public void addStudentToDb(MenuItem menuItem)
    {
        DB db = new DB(this, null, null);

        try
        {
            db.addStudentToGradebook(
                this.id,
                ((EditText) this.findViewById(R.id.atvStudent)).getText().toString()
            );

            (Toast.makeText(this, "Added!", Toast.LENGTH_LONG)).show();

            this.setResult(Activity.RESULT_OK);

            this.finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            (Toast.makeText(this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_LONG)).show();
        }
    }
}