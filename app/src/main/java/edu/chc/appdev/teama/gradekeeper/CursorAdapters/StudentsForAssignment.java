package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import edu.chc.appdev.teama.gradekeeper.R;

/**
 * Created by Glenn on 11/11/2015.
 */
public class StudentsForAssignment extends CursorAdapter
{

    public StudentsForAssignment(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_gradeassignment_student, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ((TextView) view.findViewById(R.id.li_student_name)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.etGrade)).
            setText(cursor.getString(cursor.getColumnIndex("grade")));
    }
}
