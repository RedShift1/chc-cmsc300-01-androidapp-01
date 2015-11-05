package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Courses;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB db = new DB(this, null, null);

        ListView lvItems = (ListView) this.findViewById(R.id.lvCourses);

        lvItems.setAdapter(new Courses(this, db.getCoursesCursor(), 0));
    }

    public void openCreateCourse(View view)
    {
        Intent createCourseIntent = new Intent(this, CreateCourse.class);
        this.startActivity(createCourseIntent);
    }
}
