package structis.com.reminders;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ReminderActivity extends AppCompatActivity {

    ListView mListView;
    private RemindersDbAdapter mDbAdapter;
    private RemindersCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mListView = (ListView) findViewById(R.id.reminders_lst_view);
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.reminders_rows, R.id.row_text,
                new String[]{"first record", "second record", "third record"}        );
        mListView.setAdapter(adapter);*/
        mListView.setDivider(null);
        mDbAdapter = new RemindersDbAdapter(this);
        mDbAdapter.open();
        //from column define in the Db
        String[] from = new String[]{
                RemindersDbAdapter.COL_CONTENT
        };
        int[] to = new int[]{R.id.row_text};
        if (savedInstanceState == null) {
            //Clear all data
          /*  mDbAdapter.deleteAll();
            //Add some data
            insertSomeReminders();*/
        }
        Cursor cursor = mDbAdapter.fetchAllReminders();
        mCursorAdapter = new RemindersCursorAdapter(
                //context
                ReminderActivity.this,
                //the layout of the rows
                R.layout.reminders_rows,
                //cursor
                cursor,
                //from column defined in the db
                from,
                //to ids of the views in the layout
                to,
                0);
        mListView.setAdapter(mCursorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReminderActivity.this);
                ListView modeListView = new ListView(ReminderActivity.this);
                String[] mode = new String[]{"Edit Reminder","Delete Reminder"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(ReminderActivity.this,R.layout.simple_list_item_1,R.id.simple_row_text,mode);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            Toast.makeText(ReminderActivity.this, "edit "
                                    + position, Toast.LENGTH_SHORT).show();
                            Intent in=new Intent(ReminderActivity.this,AddingReminderActivity.class);
                            startActivity(in);
//delete reminder
                        } else {
                            Toast.makeText(ReminderActivity.this, "delete "
                                    + position, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

            }
        });

    }

    private void insertSomeReminders() {
        mDbAdapter.createReminder("Buy Learn Android Studio", true);
        mDbAdapter.createReminder("Send Dad birthday gift", false);
        mDbAdapter.createReminder("Dinner at the Gage on Friday", false);
        mDbAdapter.createReminder("String squash racket", false);
        mDbAdapter.createReminder("Shovel and salt walkways", false);
        mDbAdapter.createReminder("Prepare Advanced Android syllabus", true);
        mDbAdapter.createReminder("Buy new office chair", false);
        mDbAdapter.createReminder("Call Auto-body shop for quote", false);
        mDbAdapter.createReminder("Renew membership to club", false);
        mDbAdapter.createReminder("Buy new Galaxy Android phone", true);
        mDbAdapter.createReminder("Sell old Android phone - auction", false);
        mDbAdapter.createReminder("Buy new paddles for kayaks", false);
        mDbAdapter.createReminder("Call accountant about tax returns", false);
        mDbAdapter.createReminder("Buy 300,000 shares of Google", false);
        mDbAdapter.createReminder("Call the Dalai Lama back", true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                // create new Reminder
                Log.d(getLocalClassName(), "create new Remidner");
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
