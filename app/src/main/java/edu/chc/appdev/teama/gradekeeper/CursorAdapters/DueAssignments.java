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

    /**
     * Constructor. The adapter will call {@link Cursor#requery()} on the cursor whenever
     * it changes so that the most recent data is always displayed.
     *
     * @param cursor  The cursor from which to get the data for the groups.
     * @param context
     */
    public DueAssignments(Cursor cursor, Context context, DB db)
    {
        super(cursor, context);
        this.db = db;
    }

    /*
    public DueAssignments(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_dueassignment, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ((TextView) view.findViewById(R.id.tvAssignmentName)).
                setText(cursor.getString(cursor.getColumnIndex("assignment_name")));
        ((TextView) view.findViewById(R.id.tvGradebookName)).
                setText(cursor.getString(cursor.getColumnIndex("gradebook_name")));
        ((TextView) view.findViewById(R.id.tvCourseName)).
                setText(cursor.getString(cursor.getColumnIndex("course_name")));
    }*/

    /**
     * Bind an existing view to the child data pointed to by cursor
     *
     * @param view        Existing view, returned earlier by newChildView
     * @param context     Interface to application's global information
     * @param cursor      The cursor from which to get the data. The cursor is
     *                    already moved to the correct position.
     * @param isLastChild Whether the child is the last child within its group.
     */
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

    /**
     * Makes a new child view to hold the data pointed to by cursor.
     *
     * @param context     Interface to application's global information
     * @param cursor      The cursor from which to get the data. The cursor is
     *                    already moved to the correct position.
     * @param isLastChild Whether the child is the last child within its group.
     * @param parent      The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    protected View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_dueassignment, parent, false);
    }

    /**
     * Makes a new group view to hold the group data pointed to by cursor.
     *
     * @param context    Interface to application's global information
     * @param cursor     The group cursor from which to get the data. The cursor is
     *                   already moved to the correct position.
     * @param isExpanded Whether the group is expanded.
     * @param parent     The parent to which the new view is attached to
     * @return The newly created view.
     */
    @Override
    protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.lgh_dueassignment, parent, false);
    }

    /**
     * Gets the Cursor for the children at the given group. Subclasses must
     * implement this method to return the children data for a particular group.
     * <p/>
     * If you want to asynchronously query a provider to prevent blocking the
     * UI, it is possible to return null and at a later time call
     * {@link #setChildrenCursor(int, Cursor)}.
     * <p/>
     * It is your responsibility to manage this Cursor through the Activity
     * lifecycle. It is a good idea to use {@link Activity#managedQuery} which
     * will handle this for you. In some situations, the adapter will deactivate
     * the Cursor on its own, but this will not always be the case, so please
     * ensure the Cursor is properly managed.
     *
     * @param groupCursor The cursor pointing to the group whose children cursor
     *                    should be returned
     * @return The cursor for the children of a particular group, or null.
     */
    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor)
    {
        return this.db.getDueAssignmentsForCourse(groupCursor);
    }

    /**
     * Bind an existing view to the group data pointed to by cursor.
     *
     * @param view       Existing view, returned earlier by newGroupView.
     * @param context    Interface to application's global information
     * @param cursor     The cursor from which to get the data. The cursor is
     *                   already moved to the correct position.
     * @param isExpanded Whether the group is expanded.
     */
    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded)
    {
        ((TextView) view.findViewById(R.id.tvCourseName)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
    }
}
