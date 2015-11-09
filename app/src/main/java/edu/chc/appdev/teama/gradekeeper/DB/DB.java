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

    private static final int DATA_VERSION = 3;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     */
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory)
    {
        super(context, "gradekeeper.sqlite", factory, DB.DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sqlCourses = "CREATE TABLE courses\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    code VARCHAR(20) NOT NULL,\n" +
                "    description TEXT NULL\n" +
                ");";

        String sqlAssignments = "CREATE TABLE assignments\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    course_id INTEGER NOT NULL REFERENCES courses (_id),\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    duedate VARCHAR(100),\n" +
                "    maxgrade REAL\n" +
                ");";

        String sqlStudents = "CREATE TABLE students\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name VARCHAR(100) NOT NULL UNIQUE\n" +
                ");\n";

        String sqlStudentsCourses = "CREATE TABLE students_courses\n" +
                "(\n" +
                "    course_id INTEGER NOT NULL REFERENCES courses(_id),\n" +
                "    student_id INTEGER NOT NULL REFERENCES students(_id),\n" +
                "    PRIMARY KEY(course_id,student_id)\n" +
                ");";

        String sqlAssignmentGrades = "CREATE TABLE assignment_grades\n" +
                "(\n" +
                "    assignment_id INTEGER NOT NULL REFERENCES assignments(_id),\n" +
                "    student_id INTEGER NOT NULL REFERENCES students(_id),\n" +
                "    grade REAL,\n" +
                "    PRIMARY KEY(assignment_id, student_id)\n" +
                ");";

        db.execSQL(sqlCourses);
        db.execSQL(sqlAssignments);
        db.execSQL(sqlStudents);
        db.execSQL(sqlStudentsCourses);
        db.execSQL(sqlAssignmentGrades);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS courses");
        db.execSQL("DROP TABLE IF EXISTS assignments");
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS students_courses");
        db.execSQL("DROP TABLE IF EXISTS assignment_grades");
        this.onCreate(db);
    }


    // *** Students ***

    /**
     * Add a student
     * @param name Name of the student
     * @return ID of the newly inserted row
     */
    public long addStudent(String name)
    {
        ContentValues values = new ContentValues();
        values.put("name", name);

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insertOrThrow("students", null, values);

        db.close();

        return id;
    }

    /**
     * Delete a student
     * @param id ID of the student
     */
    public void deleteStudent(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("students", "_id=" + id, null);
    }

    /**
     * Add an existing student to a course
     * @param studentId ID of the student
     * @param courseId ID of the course
     * @return ID of the newly inserted row
     */
    public long addStudentToCourse(long studentId, long courseId)
    {
        ContentValues values = new ContentValues();
        values.put("student_id", studentId);
        values.put("course_id", courseId);

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insertOrThrow("students_courses", null, values);

        db.close();

        return id;
    }

    /**
     * Get a list of students for a course
     * @param courseId ID of the course
     * @return Array of students
     */
    public Student[] getStudentsForCourse(long courseId)
    {
        Student[] resultSet;

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM students ";
        sql += "INNER JOIN students_courses ON students.id = students_courses.student_id ";
        sql += "WHERE students_courses.course_id = " + courseId;

        Cursor c = db.rawQuery(sql, null);
        resultSet = new Student[c.getCount()];
        int i = 0;
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            resultSet[i++] = new Student(
                    c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("name"))
            );

            c.moveToNext();
        }

        return resultSet;
    }


    // *** Assignments ***

    /**
     * Delete an assignment
     * @param id ID of the assignment
     */
    public void deleteAssignment(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("assignments", "_id=" + id, null);
    }

    /**
     * Add an assignment to a course
     * @param course_id ID of the course
     * @param name Name of the assignment
     * @param duedate Due date in text
     * @param maxgrade Maximum grade that can be offered
     * @return ID of the newly inserted assignment
     */
    public long addAssignmentToCourse(long course_id, String name, String duedate, float maxgrade)
    {
        ContentValues values = new ContentValues();
        values.put("course_id", course_id);
        values.put("name", name);
        values.put("duedate", duedate);
        values.put("maxgrade", maxgrade);

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insertOrThrow("assignments", null, values);

        db.close();

        return id;
    }

    public Cursor getAssignmentsForCourse(long courseId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM assignments WHERE course_id = " + courseId, null);
    }

    // *** Courses ***

    /**
     * Delete a course
     * @param id
     */
    public void deleteCourse(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("courses", "_id=" + id, null);
    }

    /**
     * Add a course
     * @param name
     * @param code
     * @param description
     * @return Id of the inserted course
     */
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
                c.getInt(c.getColumnIndex("_id")),
                c.getString(c.getColumnIndex("name")),
                c.getString(c.getColumnIndex("code")),
                c.getString(c.getColumnIndex("description"))
            );

            c.moveToNext();
        }

        return resultSet;
    }

    public Cursor getCoursesCursor()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM courses", null);
    }

    public Course getCourse(long id) throws Exception
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM courses WHERE _id = " + id, null);

        if(c.getCount() == 1)
        {
            c.moveToFirst();
            return new Course(
                    c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("code")),
                    c.getString(c.getColumnIndex("description"))
            );
        }
        else if(c.getCount() == 0)
        {
            throw new Exception("No row found");
        }
        else
        {
            throw new Exception("More than one row found");
        }
    }
}
