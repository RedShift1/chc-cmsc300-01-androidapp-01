package edu.chc.appdev.teama.gradekeeper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Assignments;
import edu.chc.appdev.teama.gradekeeper.DB.Assignment;
import edu.chc.appdev.teama.gradekeeper.DB.DB;

public class FragmentAssignments extends ListFragment implements ITabbedFragment
{
    public FragmentAssignments()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
}
