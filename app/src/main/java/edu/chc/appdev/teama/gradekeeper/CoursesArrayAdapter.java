package edu.chc.appdev.teama.gradekeeper;

/**
 * Created by Dylan on 11/3/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CoursesArrayAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final String[][] values;

    public CoursesArrayAdapter(Context context, String[][] values) {
        super(context, R.layout.courselistlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View courseView = inflater.inflate(R.layout.courselistlayout, parent, false);
        TextView courseNameView = (TextView) courseView.findViewById(R.id.coursename);
        TextView courseCodeView = (TextView) courseView.findViewById(R.id.coursecode);
        TextView courseDescriptionView = (TextView) courseView.findViewById(R.id.coursedescription);
        courseNameView.setText(values[position][0]);
        courseCodeView.setText(values[position][1]);
        courseDescriptionView.setText(values[position][2]);
        // Change the icon for Windows and iPhone
        /*String s = values[position];
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }*/

        return courseView;
    }
}
