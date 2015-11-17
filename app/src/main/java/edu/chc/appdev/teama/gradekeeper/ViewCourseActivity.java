package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Gradebooks;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class ViewCourseActivity extends AppCompatActivity
{
    private final int REQUEST_ADDGRADEBOOK = 0;
    private final int REQUEST_VIEWGRADEBOOK = 1;

    private DB db;

    private Gradebooks gradebooksAdapter;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        this.db = new DB(this, null, null);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        this.gradebooksAdapter = new Gradebooks(this, this.db.getGradebooksForCourse(this.id), 0);


        ListView lvGradebookList = (ListView) this.findViewById(R.id.lv_gradebooks);
        lvGradebookList.setAdapter(this.gradebooksAdapter);


        lvGradebookList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent viewGradebook = new Intent(ViewCourseActivity.this, ViewGradebookActivity.class);
                viewGradebook.putExtra("_id", id);

                startActivityForResult(viewGradebook, ViewCourseActivity.this.REQUEST_VIEWGRADEBOOK);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent addGradebookIntent = new Intent(ViewCourseActivity.this, AddGradebookActivity.class);
                addGradebookIntent.putExtra("_id", ViewCourseActivity.this.id);
                startActivityForResult(addGradebookIntent, ViewCourseActivity.this.REQUEST_ADDGRADEBOOK);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.REQUEST_ADDGRADEBOOK)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.gradebooksAdapter.swapCursor(this.db.getGradebooksForCourse(this.id));
            }
        }
    }

    public void deleteCourseFromDB(MenuItem menuItem)
    {
        this.db.deleteCourse(this.id);

        (Toast.makeText(this, "Deleted!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.viewcourse, menu);
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
}
