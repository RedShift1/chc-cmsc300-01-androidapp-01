package edu.chc.appdev.teama.gradekeeper.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Glenn on 29/10/2015.
 */
public class DB extends SQLiteOpenHelper
{

    private static final int DATA_VERSION = 1;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE courses\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    code VARCHAR(20) NOT NULL,\n" +
                "    description TEXT NULL\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE assignments\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    course_id INTEGER NOT NULL REFERENCES courses (id),\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    duedate DATE,\n" +
                "    maxgrade REAL\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE students\n" +
                "(\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name VARCHAR(100) NOT NULL\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE students_courses\n" +
                "(\n" +
                "    course_id INTEGER NOT NULL,\n" +
                "    student_id INTEGER NOT NULL,\n" +
                "    PRIMARY KEY(course_id,student_id)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE assignment_grades\n" +
                "(\n" +
                "    assignment_id INTEGER NOT NULL,\n" +
                "    student_id INTEGER NOT NULL REFERENCES students (id),\n" +
                "    grade REAL,\n" +
                "    PRIMARY KEY(assignment_id,student_id)\n" +
                ");\n" +
                "\n";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public long addCourse(String name, String code, String description)
    {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("code", code);
        values.put("description", description);

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insertOrThrow("courses", null, values);

        db.close();

        return id;
    }

    /**
     * Get a list of all courses
     * @param sortField Sorted by this field
     * @return Array of Course objects
     */
    public Course[] getCourses(String sortField)
    {
        // TODO: implement sorting
        Course[] resultSet;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM courses", null);
        resultSet = new Course[c.getCount()];
        int i = 0;
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            resultSet[i++] = new Course(
                c.getInt(c.getColumnIndex("id")),
                c.getString(c.getColumnIndex("name")),
                c.getString(c.getColumnIndex("code")),
                c.getString(c.getColumnIndex("description"))
            );

            c.moveToNext();
        }

        return resultSet;
    }
}
