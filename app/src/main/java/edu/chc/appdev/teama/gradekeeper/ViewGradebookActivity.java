package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Assignments;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Students;
import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentsFromAll;
import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.DB.Gradebook;

public class ViewGradebookActivity extends AppCompatActivity {

    public final int REQUEST_GRADEACTIVITY = 0;
    static final int REQUEST_ADD_STUDENT = 1;
    static final int REQUEST_ADD_ASSIGNMENT = 2;

    private DB db;
    private Assignments assignmentsAdapter;
    private StudentsFromAll studentsAdapter;

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_gradebook);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);



        this.db = DB.getInstance(this);

        Bundle extras = this.getIntent().getExtras();

        this.id = extras.getLong("_id");

        try
        {
            Gradebook thisGradebook = this.db.getGradebook(this.id);
            this.setTitle("Gradebook");
            toolbar.setSubtitle(thisGradebook.getName());


        }
        catch(Exception ex)
        {
            Log.w("Gradekeeper", "No course found with ID " + this.id);
        }

        this.assignmentsAdapter = new Assignments(this, this.db.getAssignmentsForGradebook(this.id), 0);
        this.studentsAdapter = new StudentsFromAll(this, this.db.getStudentsForGradebook(this.id), 0);

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(this.getSupportFragmentManager(), ViewGradebookActivity.this);

        FragmentAssignments assignmentsTab = new FragmentAssignments();
        assignmentsTab.setAssignmentsAdapter(this.assignmentsAdapter);
        adapter.addTabFragment(assignmentsTab);

        FragmentStudents studentsTab = new FragmentStudents();
        studentsTab.setStudentsAdapter(this.studentsAdapter);
        adapter.addTabFragment(studentsTab);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);



    }

    public synchronized void refreshStudents() {
        this.studentsAdapter.changeCursor(this.db.getStudentsForGradebook(this.id));
        studentsAdapter.notifyDataSetChanged();
        //listParent.invalidateViews();
        //listParent.scrollBy(0, 0);
    }

    public synchronized void refreshAssignments() {
        this.assignmentsAdapter.changeCursor(this.db.getAssignmentsForGradebook(this.id));
        assignmentsAdapter.notifyDataSetChanged();
        //listParent.invalidateViews();
        //listParent.scrollBy(0, 0);
    }

    public void deleteStudent(View view) {
        LinearLayout linearParent = ((LinearLayout) ((RelativeLayout) view.getParent()).getParent());
        ListView listParent = (ListView) linearParent.getParent();
        int n = ((Integer) listParent.indexOfChild(linearParent));
        Cursor cursor = ((Cursor) listParent.getAdapter().getItem(n));
        CursorAdapter adapter = ((CursorAdapter) listParent.getAdapter());
        int studentId = cursor.getInt(cursor.getColumnIndex("_id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));

        this.db.removeStudentFromGradebook(this.id, studentId);

        Toast.makeText(ViewGradebookActivity.this, "Deleted " + name + "!", Toast.LENGTH_LONG).show();

        refreshStudents();
    }

    public void removeStudentFromGradebook(long studentId)
    {
        this.db.removeStudentFromGradebook(this.id, studentId);
        this.studentsAdapter.swapCursor(this.db.getStudentsForGradebook(this.id));
    }


    public void deleteGradebookFromDB(MenuItem menuItem)
    {
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);

        confirm.setTitle("Delete");
        confirm.setMessage("Are you sure you want to delete this gradebook?");
        confirm.setPositiveButton("Delete", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                ViewGradebookActivity.this.db.deleteGradebook(ViewGradebookActivity.this.id);

                (Toast.makeText(ViewGradebookActivity.this, "Deleted!", Toast.LENGTH_LONG)).show();
                dialog.dismiss();
                ViewGradebookActivity.this.setResult(Activity.RESULT_OK);
                ViewGradebookActivity.this.finish();
            }
        });

        confirm.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        confirm.create();
        confirm.show();

        /*
        this.db.deleteGradebook(this.id);

        (Toast.makeText(this, "Deleted!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.viewgradebook, menu);
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

    public void openAddStudent(View view)
    {
        Intent addStudentIntent = new Intent(this, AddStudent.class);
        addStudentIntent.putExtra("_id", this.id);
        this.startActivityForResult(addStudentIntent, this.REQUEST_ADD_STUDENT);
    }

    public void openAddAssignment(View view)
    {
        Intent addAssignmentIntent = new Intent(this, AddAssignmentActivity.class);
        addAssignmentIntent.putExtra("_id", this.id);
        this.startActivityForResult(addAssignmentIntent, this.REQUEST_ADD_ASSIGNMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == this.REQUEST_ADD_ASSIGNMENT)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.assignmentsAdapter.swapCursor(this.db.getAssignmentsForGradebook(this.id));
            }
        }

        if(requestCode == this.REQUEST_ADD_STUDENT)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                this.studentsAdapter.swapCursor(this.db.getStudentsForGradebook(this.id));
            }
        }

        if (requestCode == this.REQUEST_GRADEACTIVITY) {
            refreshAssignments();
        }
    }
}