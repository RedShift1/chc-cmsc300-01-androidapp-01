package edu.chc.appdev.teama.gradekeeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.ListView;

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

        this.db = new DB(this, null, null);

        this.dueAssignmentsAdapter = new DueAssignments(this.db.getCoursesCursor(), this, this.db);

        ExpandableListView lvItems = (ExpandableListView) this.findViewById(R.id.lvDueAssignments);

        lvItems.setAdapter(this.dueAssignmentsAdapter);

        for(int i = 0; i < lvItems.getExpandableListAdapter().getGroupCount(); i++)
        {
            lvItems.expandGroup(i);
        }

        // TODO: view due dates

    }

    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
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
