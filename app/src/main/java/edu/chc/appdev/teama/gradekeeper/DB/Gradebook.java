package edu.chc.appdev.teama.gradekeeper.DB;

/**
 * Created by Glenn on 16/11/2015.
 */
public class Gradebook
{
    private long id;
    private String name;

    public Gradebook(long id, String name)
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

