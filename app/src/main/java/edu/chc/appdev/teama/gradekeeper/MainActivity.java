package edu.chc.appdev.teama.gradekeeper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class MainActivity extends AppCompatActivity
{
    static final int REQUEST_CREATE_COURSE = 1;
    static final int REQUEST_VIEW_COURSE   = 2;

    private DB db;
    private Courses coursesAdapter;

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
                startActivityForResult(viewCourse, MainActivity.REQUEST_VIEW_COURSE);

                // Get the item for passing it on to the new activity:
                // MainActivity.this.coursesAdapter.getItem(position);
            }
        });
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
    }
}
