package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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
 * Created by Glenn on 16/11/2015.
 */
public class Gradebooks extends CursorAdapter
{

    ColorGenerator colorGen;

    public Gradebooks(Context context, Cursor c, int flags)
    {
        super(context, c, flags);
        this.colorGen = ColorGenerator.MATERIAL;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_gradebook, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        String gradebookName = cursor.getString(cursor.getColumnIndex("name"));
        ((TextView) view.findViewById(R.id.tv_name)).
                setText(gradebookName);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(gradebookName.substring(0, 1).toUpperCase(), this.colorGen.getColor(gradebookName));

        ImageView image = (ImageView) view.findViewById(R.id.iv_name_icon);
        image.setImageDrawable(drawable);
    }
}
