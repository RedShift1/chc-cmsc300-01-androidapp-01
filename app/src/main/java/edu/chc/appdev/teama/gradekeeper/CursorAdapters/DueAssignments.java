package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.CursorTreeAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.R;

/**
 * Created by Glenn on 24/11/2015.
 */
public class DueAssignments extends CursorTreeAdapter
{

    ColorGenerator colorGen;

    private DB db;

    public DueAssignments(Cursor cursor, Context context, DB db)
    {
        super(cursor, context);
        this.db = db;
        this.colorGen = ColorGenerator.MATERIAL;
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, y", Locale.getDefault());
        String dueDate = sdf.format(new Date(cursor.getLong(cursor.getColumnIndex("duedate"))));

        ((TextView) view.findViewById(R.id.tvAssignmentName)).
                setText(cursor.getString(cursor.getColumnIndex("assignment_name")));
        ((TextView) view.findViewById(R.id.tvGradebookName)).
                setText("Gradebook: " + cursor.getString(cursor.getColumnIndex("gradebook_name")));
        ((TextView) view.findViewById(R.id.tvDueDate)).
                setText(dueDate);

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
        String name = cursor.getString(cursor.getColumnIndex("name"));
        ((TextView) view.findViewById(R.id.tvCourseName)).
                setText(name);

        if(name.length() > 0)
        {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(name.substring(0, 1).toUpperCase(), this.colorGen.getColor(name));

            ImageView image = (ImageView) view.findViewById(R.id.iv_name_icon);
            image.setImageDrawable(drawable);
        }

        ImageView expand = (ImageView) view.findViewById(R.id.iv_expand_icon);

        if(isExpanded)
        {
            expand.setImageResource(R.drawable.chevron_up);
        }
        else
        {
            expand.setImageResource(R.drawable.chevron_down);
        }
    }
}
