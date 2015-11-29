package edu.chc.appdev.teama.gradekeeper.FormValidator;

/**
 * Created by Glenn on 29/11/2015.
 */
public interface ITextValidator
{
    Boolean isValid(String text);
    String getReason();
}
