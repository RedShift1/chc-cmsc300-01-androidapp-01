package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class AddAssignment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        this.setTitle("Add Assignment");
        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

    public void addCourseToDb(MenuItem menuItem)
    {
        DB db = new DB(this, null, null);

        // NEED ACTUAL COURSEID
        long courseId = 1;

        // There probably needs more conversion for the date
        db.addAssignmentToCourse(courseId,
                ((EditText) this.findViewById(R.id.txtName)).getText().toString(),
                Long.parseLong(((EditText) this.findViewById(R.id.txtDate)).getText().toString()),
                Float.parseFloat(((EditText) this.findViewById(R.id.txtPoints)).getText().toString()));

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