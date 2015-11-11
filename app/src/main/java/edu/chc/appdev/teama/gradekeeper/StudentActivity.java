package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Assignments;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Students;
import edu.chc.appdev.teama.gradekeeper.DB.Course;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class StudentActivity extends AppCompatActivity {

    static final int REQUEST_ADD_STUDENT = 1;
    static final int REQUEST_ADD_ASSIGNMENT = 2;

    private DB db;
    private Assignments assignmentsAdapter;
    private Students studentsAdapter;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_student);

        this.db = new DB(this, null, null);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        try
        {
            Course thisCourse = this.db.getCourse(this.id);
            this.setTitle("View Student");
        }
        catch(Exception ex)
        {
            Log.w("Gradekeeper", "No student found with ID " + this.id);
        }

        this.assignmentsAdapter = new Assignments(this, this.db.getAssignmentsForCourse(this.id), 0);
        this.studentsAdapter = new Students(this, this.db.getStudentsForCourseCursor(this.id), 0);

        /*ListView lvAssignments = (ListView) this.findViewById(R.id.lv_assignments);
        lvAssignments.setAdapter(this.assignmentsAdapter);*/

        //TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(this.getSupportFragmentManager(), CourseActivity.this);

        /*Bundle bundle = new Bundle();
        bundle.putLong("_id", this.id);*/

        /*FragmentAssignments assignmentsTab = new FragmentAssignments();
        assignmentsTab.setAssignmentsAdapter(this.assignmentsAdapter);
        adapter.addTabFragment(assignmentsTab);

        FragmentStudents studentsTab = new FragmentStudents();
        studentsTab.setStudentsAdapter(this.studentsAdapter);
        adapter.addTabFragment(studentsTab);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);*/
    }


    public void deleteStudentFromDB(MenuItem menuItem)
    {
        this.db.deleteStudent(this.id);

        (Toast.makeText(this, "Deleted!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
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

    /*public void openAddStudent(View view)
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.REQUEST_ADD_ASSIGNMENT)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.assignmentsAdapter.swapCursor(this.db.getAssignmentsForCourse(this.id));
            }
        }

        if(requestCode == this.REQUEST_ADD_STUDENT)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.studentsAdapter.swapCursor(this.db.getStudentsForCourseCursor(this.id));
            }
        }
    }*/
}