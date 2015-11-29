package edu.chc.appdev.teama.gradekeeper.TextValidators;

import edu.chc.appdev.teama.gradekeeper.ITextValidator;

/**
 * Created by Glenn on 29/11/2015.
 */
public class NotEmpty implements ITextValidator
{
    @Override
    public Boolean isValid(String text)
    {
        return text.length() > 0;
    }

    @Override
    public String getReason()
    {
        return "Cannot be empty";
    }
}
