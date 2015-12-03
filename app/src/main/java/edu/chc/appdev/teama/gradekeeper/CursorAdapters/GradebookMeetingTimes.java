package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import edu.chc.appdev.teama.gradekeeper.R;
import edu.chc.appdev.teama.gradekeeper.Utils;

/**
 * Created by Glenn on 2/12/2015.
 */
public class GradebookMeetingTimes extends CursorAdapter
{
    public GradebookMeetingTimes(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_gradebook_view_meetingtime, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ((TextView) view.findViewById(R.id.tvDay)).
            setText(Utils.dayNumToName(cursor.getInt(cursor.getColumnIndex("meeting_day"))));
        ((TextView) view.findViewById(R.id.tvTime)).
            setText(cursor.getString(cursor.getColumnIndex("meeting_time")));
    }
}
