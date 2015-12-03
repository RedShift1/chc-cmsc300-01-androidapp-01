package edu.chc.appdev.teama.gradekeeper.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import edu.chc.appdev.teama.gradekeeper.FormValidator.EditTextValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.FormValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.ITextValidator;
import edu.chc.appdev.teama.gradekeeper.R;

/**
 * Created by Glenn on 11/11/2015.
 */
public class StudentsForAssignment extends CursorAdapter
{

    private FormValidator validator;
    private ITextValidator[] etGradeValidators;

    public StudentsForAssignment(Context context, Cursor c, int flags, FormValidator validator, ITextValidator[] etGradeValidators)
    {
        super(context, c, flags);

        this.validator = validator;
        this.etGradeValidators = etGradeValidators;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        return LayoutInflater.from(context).inflate(R.layout.li_gradeassignment_student, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        ((TextView) view.findViewById(R.id.li_student_name)).
                setText(cursor.getString(cursor.getColumnIndex("name")));

        EditText etGrade = (EditText) view.findViewById(R.id.etGrade);

        etGrade.setText(cursor.getString(cursor.getColumnIndex("grade")));

        this.validator.addField(new EditTextValidator(etGrade, this.etGradeValidators));
    }
}
