package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentsForAssignment;
import edu.chc.appdev.teama.gradekeeper.DB.Assignment;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class GradeAssignmentActivity extends AppCompatActivity
{

    private DB db;
    private long id;

    private StudentsForAssignment studentsForAssignmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradeassignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);


        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        this.db = DB.getInstance(this);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");


        this.studentsForAssignmentAdapter = new StudentsForAssignment(this, this.db.getStudentsWithGradesForAssignment(this.id), 0);

        ListView lvGradesList = (ListView) this.findViewById(R.id.lvGradeAssignment_Students);
        lvGradesList.setAdapter(this.studentsForAssignmentAdapter);

        try
        {
            Assignment thisAssignment = this.db.getAssignment(this.id);
            this.setTitle("Grade assignment");
            toolbar.setSubtitle(thisAssignment.getName());
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

    public void saveAssignmentGrades(MenuItem menuItem)
    {

        Cursor cursor = this.studentsForAssignmentAdapter.getCursor();

        ListView lvGradesList = (ListView) this.findViewById(R.id.lvGradeAssignment_Students);

        cursor.moveToFirst();

        int i = 0;
        while (!cursor.isAfterLast()) {
            View linearGrades = lvGradesList.getChildAt(i);
            EditText etGrade = (EditText) linearGrades.findViewById(R.id.etGrade);
            if(!etGrade.getText().toString().equals(""))
            {
                try
                {
                    Double grade = Double.parseDouble(etGrade.getText().toString());
                    db.setAssignmentGradeForStudent(this.id, cursor.getLong(cursor.getColumnIndex("student_id")), grade);
                }
                catch (Exception ex)
                {
                    Toast.makeText(this, "An error occured while updating: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    return;
                }
            }
            i++;
            cursor.moveToNext();
        }

        cursor.close();

        (Toast.makeText(this, "Saved!", Toast.LENGTH_LONG)).show();
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
