package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class CourseActivity extends AppCompatActivity {

    static final int REQUEST_ADD_STUDENT = 1;
    static final int REQUEST_ADD_ASSIGNMENT = 2;

    private DB db;
    //private Courses coursesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        this.db = new DB(this, null, null);

        /*this.coursesAdapter = new Courses(this, this.db.getCoursesCursor(), 0);

        ListView lvItems = (ListView) this.findViewById(R.id.lvCourses);

        lvItems.setAdapter(this.coursesAdapter);*/

        // Should be done dynamically
        this.setTitle("Intro to Java");

        /*lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent viewCourse = new Intent(MainActivity.this, CourseActivity.class);
                startActivity(viewCourse);

                // Get the item for passing it on to the new activity:
                MainActivity.this.coursesAdapter.getItem(position);
            }
        });*/
    }

    public void openAddStudent(View view)
    {
        Intent addStudentIntent = new Intent(this, AddStudent.class);
        this.startActivityForResult(addStudentIntent, this.REQUEST_ADD_STUDENT);
    }

    public void openAddAssignment(View view)
    {
        Intent addAssignmentIntent = new Intent(this, AddAssignment.class);
        this.startActivityForResult(addAssignmentIntent, this.REQUEST_ADD_ASSIGNMENT);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        /*if(requestCode == this.REQUEST_CREATE_COURSE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.coursesAdapter.swapCursor(this.db.getCoursesCursor());
            }
        }*/
   // }
}