package edu.chc.appdev.teama.gradekeeper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.DB.Course;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class MainActivity extends AppCompatActivity
{
    static final int REQUEST_CREATE_COURSE = 1;
    static final int REQUEST_VIEW_COURSE   = 2;

    private DB db;
    private Courses coursesAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.db = new DB(this, null, null);

        this.coursesAdapter = new Courses(this, this.db.getCoursesCursor(), 0);

        ListView lvItems = (ListView) this.findViewById(R.id.lvCourses);

        lvItems.setAdapter(this.coursesAdapter);

        this.setTitle("Courses");
        /*ActionBar actionBar = getActionBar();
        actionBar.setTitle("Courses");

        actionBar.show();*/

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent viewCourse = new Intent(MainActivity.this, CourseActivity.class);
                viewCourse.putExtra("_id", id);

                //Toast.makeText(MainActivity.this, ((Cursor) parent.getItemAtPosition(position)).getString(2), Toast.LENGTH_LONG).show();

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


    public void openCreateCourse(View view)
    {
        Intent createCourseIntent = new Intent(this, CreateCourse.class);
        this.startActivityForResult(createCourseIntent, this.REQUEST_CREATE_COURSE);
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
