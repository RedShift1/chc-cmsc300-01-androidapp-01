package edu.chc.appdev.teama.gradekeeper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Assignments;

public class FragmentAssignments extends ListFragment implements ITabbedFragment
{
    private ViewGradebookActivity parentActivity;

    public final int REQUEST_GRADEACTIVITY = 0;

    public FragmentAssignments()
    {
        // Required blank constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent gradeAssignment = new Intent(this.getActivity(), GradeAssignmentActivity.class);
        gradeAssignment.putExtra("_id", id);
        this.startActivityForResult(gradeAssignment, this.REQUEST_GRADEACTIVITY);
    }

    public void setAssignmentsAdapter(Assignments adapter)
    {
        this.setListAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_assignments, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public String getName()
    {
        return "Assignments";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == this.REQUEST_GRADEACTIVITY)
        {
            ((ViewGradebookActivity) this.getContext()).refreshAssignments();
        }
    }
}
