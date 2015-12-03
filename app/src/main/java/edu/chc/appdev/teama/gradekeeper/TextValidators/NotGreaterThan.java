package edu.chc.appdev.teama.gradekeeper.TextValidators;

import edu.chc.appdev.teama.gradekeeper.FormValidator.ITextValidator;

/**
 * Created by Glenn on 3/12/2015.
 */
public class NotGreaterThan implements ITextValidator
{

    private double max;

    public NotGreaterThan(double max)
    {
        this.max = max;
    }

    @Override
    public Boolean isValid(String text)
    {
        try
        {
            double current = Double.parseDouble(text);
            if(current > this.max)
            {
                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return true;
    }

    @Override
    public String getReason()
    {
        return "Value cannot be larger than " + this.max;
    }
}
