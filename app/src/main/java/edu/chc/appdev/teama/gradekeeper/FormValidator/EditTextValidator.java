package edu.chc.appdev.teama.gradekeeper.FormValidator;

import android.widget.EditText;


/**
 * Created by Glenn on 29/11/2015.
 */
public class EditTextValidator
{
    private EditText editText;
    private ITextValidator[] validators;

    public EditTextValidator(EditText editText, ITextValidator[] validators)
    {
        this.editText = editText;
        this.validators = validators;
    }

    public boolean isValidAndProvideUserFeedback()
    {
        for(ITextValidator validator : this.validators)
        {
            if(!validator.isValid(this.editText.getText().toString()))
            {
                this.editText.setError(validator.getReason());
                return false;
            }
        }

        return true;
    }
}
