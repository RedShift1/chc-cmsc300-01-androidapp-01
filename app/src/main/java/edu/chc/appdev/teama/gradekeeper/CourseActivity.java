package edu.chc.appdev.teama.gradekeeper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Assignments;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.DB.Assignment;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class CourseActivity extends AppCompatActivity {

    static final int REQUEST_ADD_STUDENT = 1;
    static final int REQUEST_ADD_ASSIGNMENT = 2;

    private DB db;
    private Assignments assignmentsAdapter;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_course);

        this.db = new DB(this, null, null);

        /*this.coursesAdapter = new Courses(this, this.db.getCoursesCursor(), 0);

        ListView lvItems = (ListView) this.findViewById(R.id.lvCourses);

        lvItems.setAdapter(this.coursesAdapter);*/

        // Should be done dynamically


        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");
        this.setTitle("x");

        this.assignmentsAdapter = new Assignments(this, this.db.getAssignmentsForCourse(this.id), 0);

        ListView lvAssignments = (ListView) this.findViewById(R.id.lv_assignments);
        lvAssignments.setAdapter(this.assignmentsAdapter);




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




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.courseactivity, menu);
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

    public void openAddStudent(View view)
    {
        Intent addStudentIntent = new Intent(this, AddStudent.class);
        addStudentIntent.putExtra("_id", this.id);
        this.startActivityForResult(addStudentIntent, this.REQUEST_ADD_STUDENT);
    }

    public void openAddAssignment(View view)
    {
        Intent addAssignmentIntent = new Intent(this, AddAssignment.class);
        addAssignmentIntent.putExtra("_id", this.id);
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