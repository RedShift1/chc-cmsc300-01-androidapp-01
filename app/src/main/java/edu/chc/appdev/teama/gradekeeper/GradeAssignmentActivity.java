package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentsForAssignment;
import edu.chc.appdev.teama.gradekeeper.DB.Assignment;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class GradeAssignmentActivity extends AppCompatActivity
{

    private DB db;
    private long id;

    private StudentsForAssignment studentsForAssignmentAdapter;

    private Assignment thisAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradeassignment);

        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        this.db = new DB(this, null, null);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");


        this.studentsForAssignmentAdapter = new StudentsForAssignment(this, this.db.getStudentsForAssignment(this.id), 0);

        ListView lvGradesList = (ListView) this.findViewById(R.id.lvGradeAssignment_Students);
        lvGradesList.setAdapter(this.studentsForAssignmentAdapter);

        try
        {
            thisAssignment = this.db.getAssignment(this.id);
            this.setTitle(thisAssignment.getName());
        }
        catch(Exception ex)
        {
            Log.w("Gradekeeper", "No assignment found with ID " + this.id);
        }
    }

    public void deleteAssigmentFromDB(MenuItem menuItem)
    {
        this.db.deleteAssignment(this.id);

        (Toast.makeText(this, "Deleted!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }

    public void saveAssignmentGrades(MenuItem menuItem) {
        //this.studentsForAssignmentAdapter.
        //this.db.saveAssignmentGrades(thisAssignment.getId(), , );

        (Toast.makeText(this, "Saved!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.gradeassignmentactivity, menu);
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

}
