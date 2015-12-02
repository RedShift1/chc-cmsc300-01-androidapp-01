package edu.chc.appdev.teama.gradekeeper;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.StudentsFromAll;

public class FragmentStudents extends ListFragment implements ITabbedFragment
{

    static final int CTX_MENU_REMOVE_STUDENT = 0;

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
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        this.registerForContextMenu(this.getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        Cursor student = (Cursor) this.getListAdapter().getItem(info.position);
        menu.setHeaderTitle(student.getString(student.getColumnIndex("name")));
        menu.add(0, this.CTX_MENU_REMOVE_STUDENT, 0, "Remove from gradebook");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch(item.getItemId())
        {
            case CTX_MENU_REMOVE_STUDENT:
                ((ViewGradebookActivity) this.getContext()).removeStudentFromGradebook(menuinfo.id);
        }

        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_students, container, false);
    }

    public void setStudentsAdapter(StudentsFromAll adapter)
    {
        this.setListAdapter(adapter);
    }

    @Override
    public String getName()
    {
        return "Students";
    }
}
