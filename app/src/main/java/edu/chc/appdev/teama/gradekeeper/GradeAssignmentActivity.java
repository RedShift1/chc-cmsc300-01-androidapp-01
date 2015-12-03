package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentsForAssignment;
import edu.chc.appdev.teama.gradekeeper.DB.Assignment;
import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.FormValidator.EditTextValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.FormValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.ITextValidator;
import edu.chc.appdev.teama.gradekeeper.TextValidators.NotGreaterThan;

public class GradeAssignmentActivity extends AppCompatActivity
{

    private DB db;
    private long id;

    private StudentsForAssignment studentsForAssignmentAdapter;

    private FormValidator validator;

    private Assignment thisAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gradeassignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);


        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        this.validator = new FormValidator();
        this.db = DB.getInstance(this);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        this.thisAssignment = null;

        try
        {
            this.thisAssignment = this.db.getAssignment(this.id);
            this.setTitle("Grade assignment");
            toolbar.setSubtitle(thisAssignment.getName());
        }
        catch(Exception ex)
        {
            Log.w("Gradekeeper", "No assignment found with ID " + this.id);
        }

        this.studentsForAssignmentAdapter = new StudentsForAssignment(
            this,
            this.db.getStudentsWithGradesForAssignment(this.id),
            0,
            this.validator,
            new ITextValidator[] { new NotGreaterThan(this.thisAssignment.getMaxgrade()) }
        );

        ListView lvGradesList = (ListView) this.findViewById(R.id.lvGradeAssignment_Students);
        lvGradesList.setAdapter(this.studentsForAssignmentAdapter);
    }

    public void deleteAssigmentFromDB(MenuItem menuItem)
    {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);

        confirm.setTitle("Delete");
        confirm.setMessage("Are you sure you want to delete this assignment?");
        confirm.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                GradeAssignmentActivity.this.db.deleteAssignment(GradeAssignmentActivity.this.id);

                (Toast.makeText(GradeAssignmentActivity.this, "Deleted!", Toast.LENGTH_LONG)).show();
                dialog.dismiss();
                GradeAssignmentActivity.this.setResult(Activity.RESULT_OK);
                GradeAssignmentActivity.this.finish();
            }
        });

        confirm.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        confirm.create();
        confirm.show();
    }

    public void saveAssignmentGrades(MenuItem menuItem)
    {
        if(!this.validator.isValid())
        {
            (Toast.makeText(this, "Form contains errors", Toast.LENGTH_LONG)).show();
            return;
        }

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
