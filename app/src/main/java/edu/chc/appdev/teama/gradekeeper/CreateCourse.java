package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import edu.chc.appdev.teama.gradekeeper.DB.DB;
import edu.chc.appdev.teama.gradekeeper.FormValidator.EditTextValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.FormValidator;
import edu.chc.appdev.teama.gradekeeper.FormValidator.ITextValidator;
import edu.chc.appdev.teama.gradekeeper.TextValidators.NotEmpty;

public class CreateCourse extends AppCompatActivity
{

    private FormValidator validator;

    public CreateCourse()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);


        this.setTitle("Add course");
        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        this.validator = new FormValidator();
        this.validator.addField(
            new EditTextValidator(
                (EditText) this.findViewById(R.id.txtName),
                new ITextValidator[] { new NotEmpty() }
            )
        );
        this.validator.addField(
            new EditTextValidator(
                (EditText) this.findViewById(R.id.txtCode),
                new ITextValidator[]{new NotEmpty()}
            )
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.createcourse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return true;
        }
    }


    public void addCourseToDb(MenuItem menuItem)
    {
        if(!this.validator.isValid())
        {
            (Toast.makeText(this, "Form contains errors", Toast.LENGTH_LONG)).show();
            return;
        }

        DB db = DB.getInstance(this);

        db.addCourse(
            ((EditText) this.findViewById(R.id.txtName)).getText().toString(),
            ((EditText) this.findViewById(R.id.txtCode)).getText().toString(),
            ((EditText) this.findViewById(R.id.txtDescription)).getText().toString()
        );

        (Toast.makeText(this, "Added!", Toast.LENGTH_LONG)).show();

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }
}
