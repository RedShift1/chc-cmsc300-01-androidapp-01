package edu.chc.appdev.teama.gradekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.DueAssignments;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class ViewDueAssignmentsActivity extends AppCompatActivity
{

    private DB db;
    private DueAssignments dueAssignmentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_due_assignments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("Due assignments");
        toolbar.setSubtitle("Due today and in the future");

        this.db = DB.getInstance(this);

        this.dueAssignmentsAdapter = new DueAssignments(this.db.getCoursesCursor(), this, this.db);

        ExpandableListView lvItems = (ExpandableListView) this.findViewById(R.id.lvDueAssignments);

        lvItems.setAdapter(this.dueAssignmentsAdapter);

        for(int i = 0; i < lvItems.getExpandableListAdapter().getGroupCount(); i++)
        {
            lvItems.expandGroup(i);
        }

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
