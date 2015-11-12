package edu.chc.appdev.teama.gradekeeper.DB;

/**
 * Created by Glenn on 2/11/2015.
 */
public class Assignment
{
    private long id;
    private long courseId;
    private String name;
    private String dueDate;
    private float maxgrade;

    public Assignment(long id, long courseId, String name, String dueDate, float maxgrade)
    {
        this.id         = id;
        this.courseId   = courseId;
        this.name       = name;
        this.dueDate    = dueDate;
        this.maxgrade   = maxgrade;
    }

    public long getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getCourseId()
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

    public String getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(String dueDate)
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
