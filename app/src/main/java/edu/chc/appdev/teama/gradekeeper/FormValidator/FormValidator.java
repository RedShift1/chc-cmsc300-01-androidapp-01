package edu.chc.appdev.teama.gradekeeper.FormValidator;

import java.util.ArrayList;

/**
 * Created by Glenn on 29/11/2015.
 */
public class FormValidator
{
    private ArrayList<EditTextValidator> fields;

    public FormValidator()
    {
        this.fields = new ArrayList<>();
    }

    public void addField(EditTextValidator field)
    {
        this.fields.add(field);
    }

    public boolean isValid()
    {
        boolean isValid = true;
        for(EditTextValidator field : this.fields)
        {
            if(!field.isValidAndProvideUserFeedback())
            {
                isValid = false;
            }
        }

        return isValid;
    }
}
