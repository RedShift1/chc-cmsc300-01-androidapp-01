package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class MainActivity extends AppCompatActivity
{
    static final int REQUEST_CREATE_COURSE = 1;
    static final int REQUEST_VIEW_COURSE   = 2;
    static final int REQUEST_VIEW_STUDENTS   = 2;

    private DB db;
    private Courses coursesAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.db = new DB(this, null, null);

        this.coursesAdapter = new Courses(this, this.db.getCoursesCursor(), 0);

        ListView lvItems = (ListView) this.findViewById(R.id.lvCourses);
        lvItems.setEmptyView(this.findViewById(R.id.tvNoCourses));
        lvItems.setAdapter(this.coursesAdapter);

        this.setTitle("Courses");

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
        });

        /*
        Drawer menu stuff - causes NPE for now
        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        (this.getSupportActionBar()).setHomeButtonEnabled(true);

        this.mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        this.mDrawerToggle = new ActionBarDrawerToggle(
            this,
            this.mDrawerLayout,
            R.string.open,
            R.string.close
        );

        this.mDrawerLayout.setDrawerListener(this.mDrawerToggle);
        */
    }

    /*
    Drawer menu stuff - causes NPE for now
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.mainactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.students:
                openStudents();
                return true;
            case R.id.dueAssignments:
                openDueAssigments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openCreateCourse(View view)
    {
        Intent createCourseIntent = new Intent(this, CreateCourse.class);
        this.startActivityForResult(createCourseIntent, this.REQUEST_CREATE_COURSE);
    }

    public void openStudents()
    {
        Intent openStudentsIntent = new Intent(this, StudentsActivity.class);
        this.startActivityForResult(openStudentsIntent, this.REQUEST_VIEW_STUDENTS);
    }

    public void openDueAssigments()
    {
        Intent openDueAssigmentsIntent = new Intent(this, ViewDueAssignmentsActivity.class);
        this.startActivity(openDueAssigmentsIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.REQUEST_CREATE_COURSE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.coursesAdapter.swapCursor(this.db.getCoursesCursor());
            }
        }

        if(requestCode == this.REQUEST_VIEW_COURSE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.coursesAdapter.swapCursor(this.db.getCoursesCursor());
            }
        }
    }
}
