package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class AddStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        this.setTitle("Add Student");
        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

        long studentId = db.addStudent(
                ((EditText) this.findViewById(R.id.txtName)).getText().toString());

        // NEED ACTUAL COURSEID
        long courseId = 1;

        db.addStudentToCourse(studentId, courseId);

        /*db.addCourse(
                ((EditText) this.findViewById(R.id.txtName)).getText().toString(),
                ((EditText) this.findViewById(R.id.txtCode)).getText().toString(),
                ((EditText) this.findViewById(R.id.txtDescription)).getText().toString()
        );*/

        (Toast.makeText(this, "Added!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }
}