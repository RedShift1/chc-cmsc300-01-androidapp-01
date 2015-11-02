package edu.chc.appdev.teama.gradekeeper.DB;

/**
 * Created by Glenn on 2/11/2015.
 */
public class Student
{
    private long id;
    private String name;

    public Student(long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
