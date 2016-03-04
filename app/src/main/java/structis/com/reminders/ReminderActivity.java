package structis.com.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReminderActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mListView = (ListView) findViewById(R.id.reminders_lst_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.reminders_rows, R.id.row_text,
                new String[]{"first record", "second record", "third record"}        );
        mListView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_new:
                // create new Reminder
                Log.d(getLocalClassName(),"create new Remidner");
                return true;
            case R.id.action_exit:
                //exit
                finish();
                return false;
            default:
                return false;
        }
    }
}
