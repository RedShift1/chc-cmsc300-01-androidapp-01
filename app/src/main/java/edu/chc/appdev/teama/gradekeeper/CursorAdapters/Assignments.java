package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.chc.appdev.teama.gradekeeper.R;

/**
 * Created by Glenn on 9/11/2015.
 */
public class Assignments extends CursorAdapter
{

    ColorGenerator colorGen;

    public Assignments(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
        this.colorGen = ColorGenerator.MATERIAL;
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

        String assignmentName = cursor.getString(cursor.getColumnIndex("name"));

        ((TextView) view.findViewById(R.id.li_assignment_name)).
                setText(assignmentName);
        ((TextView) view.findViewById(R.id.li_assignment_duedate)).
                setText(dueDate);
        ((TextView) view.findViewById(R.id.li_assignment_maxgrade)).
                setText(cursor.getString(cursor.getColumnIndex("maxgrade")));

        if(assignmentName.length() > 0)
        {
            TextDrawable drawable = TextDrawable.builder()
                .buildRound(assignmentName.substring(0, 1).toUpperCase(), this.colorGen.getColor(assignmentName));

            ImageView image = (ImageView) view.findViewById(R.id.iv_name_icon);
            image.setImageDrawable(drawable);
        }
    }
}
