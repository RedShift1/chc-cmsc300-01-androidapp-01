package edu.chc.appdev.teama.gradekeeper;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Students;

public class FragmentStudents extends ListFragment implements ITabbedFragment
{
    public FragmentStudents()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_students, container, false);
    }

    public void setStudentsAdapter(Students adapter)
    {
        this.setListAdapter(adapter);
    }

    @Override
    public String getName()
    {
        return "Students";
    }
}
