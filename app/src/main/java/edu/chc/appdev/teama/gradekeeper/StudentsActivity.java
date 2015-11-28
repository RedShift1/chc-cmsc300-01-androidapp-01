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
import android.widget.EditText;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Students;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentsFromAll;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class StudentsActivity extends AppCompatActivity implements IFilterTextChangedListener
{
    static final int REQUEST_VIEW_COURSES = 1;

    private DB db;
    private StudentsFromAll studentsAdapter;

    private StringBuilder studentNameLike;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.db = new DB(this, null, null);

        this.studentsAdapter = new StudentsFromAll(this, this.db.getAllStudents(), 0);

        ListView lvItems = (ListView) this.findViewById(R.id.lvStudents);

        lvItems.setAdapter(this.studentsAdapter);

        this.setTitle("Students");

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

        this.studentNameLike = new StringBuilder("%");
        SlidingMenu menu = (SlidingMenu) this.findViewById(R.id.slidingmenulayout);
        ((EditText) menu.findViewById(R.id.etStudentName)).
                addTextChangedListener(new SQLFilterTextChanged(this.studentNameLike, this));
    }

    @Override
    public void FilterTextChanged()
    {
        this.studentsAdapter.swapCursor(
            this.db.getStudentsCursor(
                this.studentNameLike.toString()
            )
        );
    }

    public void toggleLeftMenu(MenuItem menuItem)
    {
        ((SlidingMenu) this.findViewById(R.id.slidingmenulayout)).toggle();
    }

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
            case R.id.courses:
                openCourses();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openCourses()
    {
        Intent openCoursesIntent = new Intent(this, MainActivity.class);
        this.startActivityForResult(openCoursesIntent, this.REQUEST_VIEW_COURSES);
    }
}
