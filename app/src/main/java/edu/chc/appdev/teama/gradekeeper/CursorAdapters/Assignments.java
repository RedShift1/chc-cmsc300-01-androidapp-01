package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.chc.appdev.teama.gradekeeper.R;

/**
 * Created by Glenn on 9/11/2015.
 */
public class Assignments extends CursorAdapter
{

    public Assignments(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_assignment, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, y", Locale.getDefault());
        String dueDate = sdf.format(new Date(cursor.getLong(cursor.getColumnIndex("duedate"))));

        ((TextView) view.findViewById(R.id.li_assigment_name)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.li_assigment_duedate)).
                setText(dueDate);
        ((TextView) view.findViewById(R.id.li_assigment_maxgrade)).
                setText(cursor.getString(cursor.getColumnIndex("maxgrade")));
    }
}
