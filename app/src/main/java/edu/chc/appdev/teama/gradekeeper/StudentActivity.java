package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentAssignments;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Students;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentsFromAll;
import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.DB.Gradebook;
import edu.chc.appdev.teama.gradekeeper.DB.Student;

public class StudentActivity extends AppCompatActivity
{
    private long id;

    private DB db;
    private StudentAssignments assignmentAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        this.db = new DB(this, null, null);

        this.assignmentAdapter = new StudentAssignments(this, this.db.getAssignmentsForStudent(this.id), 0);

        ListView lvItems = (ListView) this.findViewById(R.id.lvStudentAssignments);

        lvItems.setAdapter(this.assignmentAdapter);

        try
        {
            Student thisStudent = this.db.getStudent(this.id);
            this.setTitle(thisStudent.getName());
        }
        catch(Exception ex)
        {
            Log.w("Gradekeeper", "No student found with ID " + this.id);
        }

        /*lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent viewCourse = new Intent(MainActivity.this, ViewCourseActivity.class);
                viewCourse.putExtra("_id", id);

                startActivityForResult(viewCourse, MainActivity.REQUEST_VIEW_COURSE);

                // Get the item for passing it on to the new activity:
                // MainActivity.this.coursesAdapter.getItem(position);
            }
        });*/
    }
}
