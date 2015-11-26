package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;

import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.R;

/**
 * Created by Glenn on 24/11/2015.
 */
public class DueAssignments extends CursorTreeAdapter
{

    private DB db;

    public DueAssignments(Cursor cursor, Context context, DB db)
    {
        super(cursor, context);
        this.db = db;
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild)
    {
        ((TextView) view.findViewById(R.id.tvAssignmentName)).
                setText(cursor.getString(cursor.getColumnIndex("assignment_name")));
        ((TextView) view.findViewById(R.id.tvGradebookName)).
                setText(cursor.getString(cursor.getColumnIndex("gradebook_name")));
        ((TextView) view.findViewById(R.id.tvCourseName)).
                setText(cursor.getString(cursor.getColumnIndex("course_name")));
    }

    @Override
    protected View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_dueassignment, parent, false);
    }

    @Override
    protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.lgh_dueassignment, parent, false);
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor)
    {
        return this.db.getDueAssignmentsForCourse(groupCursor);
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded)
    {
        ((TextView) view.findViewById(R.id.tvCourseName)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
    }
}
