package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.util.Log;
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
public class StudentGrades extends CursorTreeAdapter
{

    ColorGenerator colorGen;

    private DB db;

    private long studentId;

    public StudentGrades(Cursor cursor, Context context, DB db, long studentId)
    {
        super(cursor, context);
        this.db = db;
        this.colorGen = ColorGenerator.MATERIAL;
        this.studentId = studentId;
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild)
    {
        /*SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, y", Locale.getDefault());
        String dueDate = sdf.format(new Date(cursor.getLong(cursor.getColumnIndex("duedate"))));

        ((TextView) view.findViewById(R.id.tvAssignmentName)).
                setText(cursor.getString(cursor.getColumnIndex("assignment_name")));
        ((TextView) view.findViewById(R.id.tvGradebookName)).
                setText("Gradebook: " + cursor.getString(cursor.getColumnIndex("gradebook_name")));
        ((TextView) view.findViewById(R.id.tvDueDate)).
                setText(dueDate);*/


        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, y", Locale.getDefault());
        String dueDate = sdf.format(new Date(cursor.getLong(cursor.getColumnIndex("duedate"))));

        String assignmentName = cursor.getString(cursor.getColumnIndex("name"));

        String grade = cursor.getString(cursor.getColumnIndex("grade"));
        if (grade == null) {
            grade = "-";
        }

        String maxGrade = cursor.getString(cursor.getColumnIndex("maxgrade"));

        ((TextView) view.findViewById(R.id.li_assignment_name)).
                setText(assignmentName);
        ((TextView) view.findViewById(R.id.li_assignment_duedate)).
                setText(dueDate);
        ((TextView) view.findViewById(R.id.li_assignment_grade)).
                setText(grade + "/" + maxGrade);
    }

    @Override
    protected View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_student_assignment, parent, false);
    }

    @Override
    protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.lgh_viewstudent, parent, false);
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor)
    {
        return this.db.getAssignmentsForStudentGradebook(studentId, groupCursor);
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded)
    {
        String name = cursor.getString(cursor.getColumnIndex("name"));
        ((TextView) view.findViewById(R.id.tvGradebookName)).
                setText(name);

        String courseName = cursor.getString(cursor.getColumnIndex("course_name"));
        ((TextView) view.findViewById(R.id.tvCourseName)).
                setText(courseName);

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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        View view = super.getGroupView(groupPosition, isExpanded, convertView, parent);
        TextView tvNumAssigmentsDue = (TextView) view.findViewById(R.id.tvNumAssignments);
        int childrenCount = this.getChildrenCount(groupPosition);
        if(childrenCount == 0)
        {
            ((ImageView) view.findViewById(R.id.iv_expand_icon)).setImageResource(android.R.color.transparent);
            tvNumAssigmentsDue.setText("No assignments in gradebook");
        }
        else
        {
            tvNumAssigmentsDue.setText(childrenCount + " assignments in gradebook");
        }

        return view;
    }
}
