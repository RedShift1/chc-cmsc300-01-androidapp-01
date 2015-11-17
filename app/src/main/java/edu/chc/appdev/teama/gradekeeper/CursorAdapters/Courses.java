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

import edu.chc.appdev.teama.gradekeeper.R;

/**
 * Created by Glenn on 4/11/2015.
 */
public class Courses extends CursorAdapter
{

    ColorGenerator colorGen;


    /**
     * Recommended constructor.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter; may
     *                be any combination of {@link #FLAG_AUTO_REQUERY} and
     *                {@link #FLAG_REGISTER_CONTENT_OBSERVER}.
     */
    public Courses(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
        this.colorGen = ColorGenerator.MATERIAL;
    }

    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_course, parent, false);
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {

        String courseName = cursor.getString(cursor.getColumnIndex("name"));

        ((TextView) view.findViewById(R.id.coursename)).
                setText(courseName);
        ((TextView) view.findViewById(R.id.coursecode)).
                setText(cursor.getString(cursor.getColumnIndex("code")));
        ((TextView) view.findViewById(R.id.coursedescription)).
                setText(cursor.getString(cursor.getColumnIndex("description")));

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(courseName.substring(0, 1).toUpperCase(), this.colorGen.getColor(courseName));

        ImageView image = (ImageView) view.findViewById(R.id.iv_name_icon);
        image.setImageDrawable(drawable);
    }
}
