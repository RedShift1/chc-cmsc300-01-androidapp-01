package edu.chc.appdev.teama.gradekeeper;

/**
 * Created by Glenn on 5/11/2015.
 */
public class Utils
{
    public static String dayNumToName(int num)
    {
        switch(num)
        {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }
}
