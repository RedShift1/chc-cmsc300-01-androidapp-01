package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class CreateCourse extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        this.setTitle("Add course");
    }

    public void addCourseToDb(View view)
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
