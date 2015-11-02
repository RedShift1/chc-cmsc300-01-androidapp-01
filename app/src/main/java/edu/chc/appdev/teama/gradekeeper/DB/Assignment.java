package edu.chc.appdev.teama.gradekeeper.DB;

/**
 * Created by Glenn on 2/11/2015.
 */
public class Assignment
{
    private int id;
    private int courseId;
    private String name;
    private long dueDate;
    private float maxgrade;

    public Assignment(int id, int courseId, String name, long dueDate, float maxgrade)
    {
        this.id         = id;
        this.courseId   = courseId;
        this.name       = name;
        this.dueDate    = dueDate;
        this.maxgrade   = maxgrade;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getCourseId()
    {
        return courseId;
    }

    public void setCourseId(int courseId)
    {
        this.courseId = courseId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public long getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(long dueDate)
    {
        this.dueDate = dueDate;
    }

    public float getMaxgrade()
    {
        return maxgrade;
    }

    public void setMaxgrade(float maxgrade)
    {
        this.maxgrade = maxgrade;
    }
}
