package structis.com.reminders;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
