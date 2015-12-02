package edu.chc.appdev.teama.gradekeeper.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.chc.appdev.teama.gradekeeper.CursorAdapters.Students;

/**
 * Created by Glenn on 29/10/2015.
 */
public class DB extends SQLiteOpenHelper {

    private static final int DATA_VERSION = 5;

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
    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, "gradekeeper.sqlite", factory, DB.DATA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCourses = "CREATE TABLE courses\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    code VARCHAR(20) NOT NULL,\n" +
                "    description TEXT NULL\n" +
                ");";

        String sqlGradebooks = "CREATE TABLE gradebooks\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    course_id INTEGER NOT NULL REFERENCES courses (_id),\n" +
                "    name VARCHAR(100) NOT NULL" +
                ");";

        String sqlAssignments = "CREATE TABLE assignments\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    gradebook_id INTEGER NOT NULL REFERENCES gradebooks (_id),\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    duedate INTEGER NOT NULL,\n" +
                "    maxgrade REAL\n" +
                ");";

        String sqlStudents = "CREATE TABLE students\n" +
                "(\n" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "    name VARCHAR(100) NOT NULL UNIQUE\n" +
                ");\n";

        String sqlGradebookStudents = "CREATE TABLE gradebook_students\n" +
                "(\n" +
                "    gradebook_id INTEGER NOT NULL REFERENCES gradebooks (_id),\n" +
                "    student_id INTEGER NOT NULL REFERENCES students (_id),\n" +
                "    PRIMARY KEY(gradebook_id,student_id)\n" +
                ");";

        String sqlAssignmentGrades = "CREATE TABLE assignment_grades\n" +
                "(\n" +
                "    assignment_id INTEGER NOT NULL REFERENCES assignments(_id),\n" +
                "    student_id INTEGER NOT NULL REFERENCES students(_id),\n" +
                "    grade REAL,\n" +
                "    PRIMARY KEY(assignment_id, student_id)\n" +
                ");";

        db.execSQL(sqlCourses);
        db.execSQL(sqlGradebooks);
        db.execSQL(sqlAssignments);
        db.execSQL(sqlStudents);
        db.execSQL(sqlGradebookStudents);
        db.execSQL(sqlAssignmentGrades);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS courses");
        db.execSQL("DROP TABLE IF EXISTS gradebooks");
        db.execSQL("DROP TABLE IF EXISTS assignments");
        db.execSQL("DROP TABLE IF EXISTS students");
        db.execSQL("DROP TABLE IF EXISTS students_courses");
        db.execSQL("DROP TABLE IF EXISTS gradebook_students");
        db.execSQL("DROP TABLE IF EXISTS assignment_grades");
        this.onCreate(db);
    }

    public Cursor getGradebooksForCourse(long id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM gradebooks WHERE course_id = " + id, null);
    }

    public long addGradebookToCourse(long courseId, String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("course_id", courseId);
        values.put("name", name);
        long id = db.insertOrThrow("gradebooks", null, values);

        db.close();

        return id;
    }

    public Cursor getAssignmentsForGradebook(long gradebookId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM assignments WHERE gradebook_id = " + gradebookId, null);
    }

    public Cursor getStudentsForGradebook(long gradebookId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM students ";
        sql += "INNER JOIN gradebook_students ON students._id = gradebook_students.student_id ";
        sql += "WHERE gradebook_students.gradebook_id = " + gradebookId;

        return db.rawQuery(sql, null);
    }

    public void deleteGradebook(long gradebookId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("gradebooks", "_id=" + gradebookId, null);
        db.close();
    }

    public long addAssignmentToGradebook(long gradebookId, String name, long duedate, float maxgrade)
    {
        ContentValues values = new ContentValues();
        values.put("gradebook_id", gradebookId);
        values.put("name", name);
        values.put("duedate", duedate);
        values.put("maxgrade", maxgrade);

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insertOrThrow("assignments", null, values);

        db.close();

        return id;
    }

    public long addStudentToGradebook(long gradebookId, String name) throws Exception
    {
        ContentValues values = new ContentValues();

        long studentId = this.getExistingStudentIdOrCreateNewId(name);

        SQLiteDatabase db = this.getWritableDatabase();

        values.put("gradebook_id", gradebookId);
        values.put("student_id", studentId);

        long id = db.insertOrThrow("gradebook_students", null, values);

        db.close();

        return id;
    }

    public void removeStudentFromGradebook(long gradebookId, long studentId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("gradebook_students", "gradebook_id = " + gradebookId + " AND student_id = " + studentId, null);
    }

    public Gradebook getGradebook(long gradebookId) throws Exception
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM gradebooks WHERE _id = " + gradebookId, null);

        if (c.getCount() == 1)
        {
            c.moveToFirst();
            return new Gradebook(
                    c.getLong(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("name"))
            );
        }
        else if (c.getCount() == 0)
        {
            throw new Exception("No row found");
        }
        else
        {
            throw new Exception("More than one row found");
        }
    }

    public long getExistingStudentIdOrCreateNewId(String name) throws Exception
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT _id FROM students WHERE name = '" + name + "'", null);

        if(c.getCount() == 1)
        {
            c.moveToFirst();
            long id = c.getLong(c.getColumnIndex("_id"));
            db.close();
            return id;
        }
        else if(c.getCount() == 0)
        {
            return this.addStudent(name);
        }
        else
        {
            throw new Exception("More than one row found");
        }
    }


    public Cursor getStudentsForAssignment(long assignmentId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT students._id, students.name AS name, ";
        sql += "assignments._id AS assignment_id, students._id AS student_id FROM students ";
        sql += "INNER JOIN gradebook_students ON gradebook_students.student_id = students._id ";
        sql += "INNER JOIN assignments ON gradebook_students.gradebook_id = assignments.gradebook_id ";
        sql += "WHERE assignments._id = " + assignmentId;
        return db.rawQuery(sql, null);
    }

    public void saveAssignmentGrades(long assignment_id, long[] student_ids, double[] grades)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < student_ids.length; i++)
        {
            ContentValues values = new ContentValues();
            values.put("assignment_id", assignment_id);
            values.put("student_id", student_ids[i]);
            values.put("grade", grades[i]);
            if (getAssignmentGrade(assignment_id, student_ids[i]).getCount() > 0)
            {
                db.update("assignment_grades", values, "assignment_id="+assignment_id + " AND " +
                        "student_id=" + student_ids[i], null);
            }
            else
            {
                long id = db.insertOrThrow("assignment_grades", null, values);
            }
        }
        db.close();
    }

    public void setAssignmentGradeForStudent(long assignmentId, long studentId, double grade) throws Exception
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("assignment_id", assignmentId);
        values.put("student_id", studentId);
        values.put("grade", grade);
        if(this.getAssignmentGradeForStudent(assignmentId, studentId) != null)
        {
            db.update(
                "assignment_grades",
                values,
                String.format("assignment_id = %d AND student_id = %d", assignmentId, studentId),
                null
            );
        }
        else
        {
            db.insertOrThrow("assignment_grades", null, values);
        }

        db.close();
    }

    private Double getAssignmentGradeForStudent(long assignmentId, long studentId) throws Exception
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.query(
            "assignment_grades",
            new String[] {"grade"},
            String.format("assignment_id = %d AND student_id = %d", assignmentId, studentId),
            null, null, null, null
        );

        if (c.getCount() == 1)
        {
            c.moveToFirst();
            Double result = c.getDouble(c.getColumnIndex("grade"));
            c.close();
            return result;
        }
        else if (c.getCount() == 0)
        {
            c.close();
            return null;
        }
        else
        {
            throw new Exception("More than one row found");
        }
    }

    public Cursor getDueAssignments()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT assignments._id, " +
            "assignments.name AS assignment_name, " +
            "assignments.duedate, " +
            "gradebooks.name AS gradebook_name, " +
            "courses.name AS course_name " +
            "FROM assignments " +
            "INNER JOIN gradebooks ON assignments.gradebook_id = gradebooks._id " +
            "INNER JOIN courses ON gradebooks.course_id = courses._id " +
            "WHERE STRFTIME('%Y-%m-%d', assignments.duedate / 1000, 'unixepoch') >= STRFTIME('%Y-%m-%d', 'now') " +
            "ORDER BY courses.code DESC, duedate ASC";
        return db.rawQuery(sql, null);
    }

    public Cursor getDueAssignmentsForCourse(Cursor courseCursor)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT assignments._id, " +
                "assignments.name AS assignment_name, " +
                "assignments.duedate, " +
                "gradebooks.name AS gradebook_name, " +
                "courses.name AS course_name " +
                "FROM assignments " +
                "INNER JOIN gradebooks ON assignments.gradebook_id = gradebooks._id " +
                "INNER JOIN courses ON gradebooks.course_id = courses._id " +
                "WHERE STRFTIME('%Y-%m-%d', assignments.duedate / 1000, 'unixepoch') >= STRFTIME('%Y-%m-%d', 'now') " +
                "AND courses._id = " + courseCursor.getInt(courseCursor.getColumnIndex("_id")) + " " +
                "ORDER BY duedate ASC";
        return db.rawQuery(sql, null);
    }

    // *** Students ***

    public Cursor getStudentsCursor() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM students", null);
    }

    public Cursor getStudentsCursor(String nameLike)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM students WHERE name LIKE ?", new String[] {nameLike});
    }

    /**
     * Add a student
     *
     * @param name Name of the student
     * @return ID of the newly inserted row
     */
    public long addStudent(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insertOrThrow("students", null, values);

        db.close();

        return id;
    }

    /**
     * Delete a student
     *
     * @param id ID of the student
     */
    public void deleteStudent(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("students", "_id=" + id, null);
    }

    /**
     * Add an existing student to a course
     *
     * @param studentId ID of the student
     * @param courseId  ID of the course
     * @return ID of the newly inserted row
     */
    public long addStudentToCourse(long studentId, long courseId) {
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
     *
     * @param courseId ID of the course
     * @return Array of students
     */
    public Student[] getStudentsForCourse(long courseId) {
        Student[] resultSet;

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM students ";
        sql += "INNER JOIN students_courses ON students.id = students_courses.student_id ";
        sql += "WHERE students_courses.course_id = " + courseId;

        Cursor c = db.rawQuery(sql, null);
        resultSet = new Student[c.getCount()];
        int i = 0;
        c.moveToFirst();
        while (!c.isAfterLast()) {
            resultSet[i++] = new Student(
                    c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("name"))
            );

            c.moveToNext();
        }

        return resultSet;
    }

    public Cursor getAllStudents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM students ";

        return db.rawQuery(sql, null);
    }

    public Cursor getStudentsForCourseCursor(long courseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM students ";
        sql += "INNER JOIN students_courses ON students._id = students_courses.student_id ";
        sql += "WHERE students_courses.course_id = " + courseId;

        return db.rawQuery(sql, null);
    }


    // *** Assignments ***

    /**
     * Delete an assignment
     *
     * @param id ID of the assignment
     */
    public void deleteAssignment(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("assignments", "_id=" + id, null);
    }

    public Assignment getAssignment(long id) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM assignments WHERE _id = " + id, null);

        if (c.getCount() == 1) {
            c.moveToFirst();
            return new Assignment(
                    c.getLong(c.getColumnIndex("_id")),
                    c.getLong(c.getColumnIndex("gradebook_id")),
                    c.getString(c.getColumnIndex("name")),
                    c.getString(c.getColumnIndex("duedate")),
                    c.getFloat(c.getColumnIndex("maxgrade"))
            );
        } else if (c.getCount() == 0) {
            throw new Exception("No row found");
        } else {
            throw new Exception("More than one row found");
        }
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
    public void deleteCourse(long id)
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

    public Cursor getCoursesCursor(String nameLike, String codeLike, String descriptionLike)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(
            "SELECT * FROM courses WHERE name LIKE ? AND code LIKE ? AND description LIKE ?",
            new String[] {nameLike, codeLike, descriptionLike}
        );
    }

    public Cursor getCoursesCursor(String search)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(
            "SELECT * FROM courses WHERE name LIKE ? OR code LIKE ? OR description LIKE ?",
            new String[] {search, search, search}
        );
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

    public Cursor getAssignmentGrade(long assignmentId, long studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM assignment_grades WHERE assignment_id = " + assignmentId +
                " AND student_id = " + studentId, null);
    }

    public Student getStudent(long studentId) {
        Student result;

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM students ";
        sql += "WHERE _id = " + studentId;

        Cursor c = db.rawQuery(sql, null);
        //result = new Student[c.getCount()];
        int i = 0;
        c.moveToFirst();
        //while (!c.isAfterLast()) {
            result = new Student(
                    c.getInt(c.getColumnIndex("_id")),
                    c.getString(c.getColumnIndex("name"))
            );

            //c.moveToNext();
        //}

        return result;
    }

    public Cursor getAssignmentsForStudent(long studentId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //return db.rawQuery("SELECT * FROM assignments WHERE student_id = " + studentId, null);
        //Cursor c =  db.rawQuery("SELECT * FROM assignment_grades WHERE student_id = " + studentId, null);

        String sql = "SELECT * FROM assignments WHERE gradebook_id IN ";
        sql += "(SELECT gradebook_id from gradebook_students WHERE student_id = ";
        sql += studentId + ")";

        Cursor c =  db.rawQuery(sql, null);

       /* Cursor c = db.rawQuery(sql, null);
        result = new Student[c.getCount()];
        int i = 0;
        c.moveToFirst();
        while (!c.isAfterLast()) {
        result = new Student(
                c.getInt(c.getColumnIndex("_id")),
                c.getString(c.getColumnIndex("name"))
        );

        c.moveToNext();
        }*/

        return c;
    }

    public Cursor getGrade(long studentId, long assignmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM assignment_grades WHERE student_id = " + studentId +
                " AND assignment_id = " + assignmentId, null);
    }
}
