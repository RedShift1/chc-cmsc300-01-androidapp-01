package edu.chc.appdev.teama.gradekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.DB.Student;

public class ViewStudentActivity extends AppCompatActivity
{

    private DB db;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_view_student);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.db = new DB(this, null, null);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        try
        {
            Student thisStudent = this.db.getStudent(this.id);
            this.setTitle("View student");
            toolbar.setSubtitle(thisStudent.getName());
        }
        catch(Exception ex)
        {
            Log.w("Gradekeeper", "No student found with ID " + this.id + ": " + ex.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
