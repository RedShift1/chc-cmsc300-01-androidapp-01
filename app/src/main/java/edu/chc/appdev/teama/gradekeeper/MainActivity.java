package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.content.Intent;
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
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class MainActivity extends AppCompatActivity implements IFilterTextChangedListener
{
    static final int REQUEST_CREATE_COURSE = 1;
    static final int REQUEST_VIEW_COURSE   = 2;
    static final int REQUEST_VIEW_STUDENTS   = 2;

    private DB db;
    private Courses coursesAdapter;

    private StringBuilder courseNameLike;
    private StringBuilder courseCodeLike;
    private StringBuilder courseDescriptionLike;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        this.db = new DB(this, null, null);

        this.coursesAdapter = new Courses(this, this.db.getCoursesCursor("%", "%", "%"), 0);

        ListView lvItems = (ListView) this.findViewById(R.id.lvCourses);
        lvItems.setEmptyView(this.findViewById(R.id.tvNoCourses));
        lvItems.setAdapter(this.coursesAdapter);

        this.setTitle("Courses");

        this.courseNameLike = new StringBuilder("%");
        this.courseCodeLike = new StringBuilder("%");
        this.courseDescriptionLike = new StringBuilder("%");

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

        SlidingMenu menu = (SlidingMenu) this.findViewById(R.id.slidingmenulayout);

        ((EditText) menu.findViewById(R.id.etCourseCode)).
            addTextChangedListener(new SQLFilterTextChanged(this.courseCodeLike, this));

        ((EditText) menu.findViewById(R.id.etCourseName)).
            addTextChangedListener(new SQLFilterTextChanged(this.courseNameLike, this));

        ((EditText) menu.findViewById(R.id.etCourseDescription)).
            addTextChangedListener(new SQLFilterTextChanged(this.courseDescriptionLike, this));

        ((EditText) menu.findViewById(R.id.etSearch)).
            addTextChangedListener(new SQLFilterTextChanged(this.search, this));
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
            case R.id.students:
                openStudents();
                return true;
            case R.id.dueAssignments:
                openDueAssignments();
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
        Intent openStudentsIntent = new Intent(this, ViewStudentsActivity.class);
        this.startActivityForResult(openStudentsIntent, this.REQUEST_VIEW_STUDENTS);
    }

    public void openDueAssignments()
    {
        Intent openDueAssigmentsIntent = new Intent(this, ViewDueAssignmentsActivity.class);
        this.startActivity(openDueAssigmentsIntent);
    }

    protected void updateCoursesView()
    {
        if(this.search.toString().equals("%"))
        {
            this.coursesAdapter.swapCursor(
                this.db.getCoursesCursor(
                    this.courseNameLike.toString(),
                    this.courseCodeLike.toString(),
                    this.courseDescriptionLike.toString()
                )
            );
        }
        else
        {
            this.coursesAdapter.swapCursor(
                this.db.getCoursesCursor(
                    this.search.toString()
                )
            );
        }
    }

    @Override
    public void FilterTextChanged()
    {
        this.updateCoursesView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.REQUEST_CREATE_COURSE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.updateCoursesView();
            }
        }

        if(requestCode == this.REQUEST_VIEW_COURSE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.updateCoursesView();
            }
        }
    }
}
