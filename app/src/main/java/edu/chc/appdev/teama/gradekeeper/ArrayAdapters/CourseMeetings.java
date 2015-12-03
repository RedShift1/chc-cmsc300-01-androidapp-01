package edu.chc.appdev.teama.gradekeeper.ArrayAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.chc.appdev.teama.gradekeeper.R;
import edu.chc.appdev.teama.gradekeeper.Utils;

/**
 * Created by Glenn on 5/11/2015.
 */
public class CourseMeetings extends ArrayAdapter<String[]>
{
    private final Context context;
    private final ArrayList<String[]> values;

    public CourseMeetings(Context context, ArrayList<String[]> values) {
        super(context, R.layout.li_gradebook_add_meetingtime, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View meetingView = inflater.inflate(R.layout.li_gradebook_add_meetingtime, parent, false);

        ((TextView) meetingView.findViewById(R.id.tvDay)).
                setText(Utils.dayNumToName(Integer.parseInt(this.values.get(position)[0])));
        ((TextView) meetingView.findViewById(R.id.tvTime)).
                setText(this.values.get(position)[1]);

        return meetingView;
    }
}
