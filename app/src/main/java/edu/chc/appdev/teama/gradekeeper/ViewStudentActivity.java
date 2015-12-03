package edu.chc.appdev.teama.gradekeeper;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.DueAssignments;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentAssignments;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentGrades;
import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.DB.Student;

public class ViewStudentActivity extends AppCompatActivity
{

    private long id;

    private DB db;
    private StudentGrades assignmentAdapter;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_view_student);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.db = DB.getInstance(this);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        try
        {
            Student thisStudent = this.db.getStudent(this.id);
            this.setTitle("View student");
            toolbar.setSubtitle(thisStudent.getName());
        }
        catch(Exception ex)
        {
            Log.w("Gradekeeper", "No student found with ID " + this.id + ": " + ex.getMessage());
        }

       this.assignmentAdapter = new StudentGrades(this.db.getGradebooksForStudent(this.id), this,
               this.db, this.id);

        ExpandableListView lvItems = (ExpandableListView) this.findViewById(R.id.lvStudentAssignments);

        lvItems.setAdapter(this.assignmentAdapter);

        for(int i = 0; i < lvItems.getExpandableListAdapter().getGroupCount(); i++)
        {
            lvItems.expandGroup(i);
        }

    }

    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
