package edu.chc.appdev.teama.gradekeeper;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AddGradebookActivity extends AppCompatActivity
{
    private long id;

    private FormValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gradebook);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);


        (this.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        Bundle extras = this.getIntent().getExtras();
        this.id = extras.getLong("_id");

        this.validator = new FormValidator();
        this.validator.addField(
            new EditTextValidator(
                (EditText) this.findViewById(R.id.etName),
                new ITextValidator[] { new NotEmpty() }
            )
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        this.getMenuInflater().inflate(R.menu.addgradebook, menu);
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


    public void addGradebookToDB(MenuItem menuItem)
    {
        if(!this.validator.isValid())
        {
            (Toast.makeText(this, "Form contains errors", Toast.LENGTH_LONG)).show();
            return;
        }

        DB db = new DB(this, null, null);
        db.addGradebookToCourse(this.id,
                ((EditText) this.findViewById(R.id.etName)).getText().toString());

        this.setResult(Activity.RESULT_OK);

        this.finish();
    }
}
