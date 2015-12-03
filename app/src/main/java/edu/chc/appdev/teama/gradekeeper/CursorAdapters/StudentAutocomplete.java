package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import edu.chc.appdev.teama.gradekeeper.DB.DB;

/**
 * Created by Glenn on 9/11/2015.
 */
public class StudentAutocomplete extends CursorAdapter
{

    private DB db;

    public StudentAutocomplete(Context context, Cursor c, int flags, DB db)
    {
        super(context, c, flags);
        this.db = db;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ((TextView) view.findViewById(android.R.id.text1)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint)
    {
        if (this.getFilterQueryProvider() != null)
        {
            return this.getFilterQueryProvider().runQuery(constraint);
        }

        String args = "";

        if (constraint != null)
        {
            args = "%" + constraint.toString() + "%";
        }

        return this.db.getStudentsCursor(args);
    }

    @Override
    public CharSequence convertToString(Cursor cursor)
    {
        return cursor.getString(cursor.getColumnIndex("name"));
    }
}
