package edu.chc.appdev.teama.gradekeeper;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*String[] myStringArray={"A","B","C"};
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myStringArray);
        ListView myList=
                (ListView) findViewById(R.id.courseListView);
        myList.setAdapter(myAdapter);*/
        String[][] testArray = {{"Intro to Java", "CMSC 101", "Teaches fundamentals..."},
                {"English Composition", "ENG 101", "A 100-level class that..."}};
        CoursesArrayAdapter adapter = new CoursesArrayAdapter(this, testArray);
        setListAdapter(adapter);
    }

    public void openCreateCourse(View view)
    {
        Intent createCourseIntent = new Intent(this, CreateCourse.class);
        this.startActivity(createCourseIntent);
    }
}
