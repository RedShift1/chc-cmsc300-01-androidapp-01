package edu.chc.appdev.teama.gradekeeper;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Glenn on 27/11/2015.
 */
public class SQLFilterTextChanged implements TextWatcher
{
    private StringBuilder strToChange;
    private IFilterTextChangedListener obj;


    public SQLFilterTextChanged(StringBuilder strToChange, IFilterTextChangedListener obj)
    {
        this.strToChange = strToChange;
        this.obj = obj;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        if(s.length() > 0)
        {
            this.strToChange.replace(0, this.strToChange.length(), "%" + s + "%");
        }
        else
        {
            this.strToChange.replace(0, this.strToChange.length(), "%");
        }

        this.obj.FilterTextChanged();
    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }
}
