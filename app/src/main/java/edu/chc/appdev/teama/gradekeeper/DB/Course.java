package edu.chc.appdev.teama.gradekeeper.DB;

/**
 * Created by Glenn on 29/10/2015.
 */
public class Course
{
    private int id;
    private String name;
    private String code;
    private String description;

    public Course(int id, String name, String code, String description)
    {
        this.id             = id;
        this.name           = name;
        this.code           = code;
        this.description    = description;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
