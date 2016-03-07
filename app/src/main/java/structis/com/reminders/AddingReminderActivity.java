package structis.com.reminders;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Thao.nguyenduc on 3/7/2016.
 */
public class AddingReminderActivity extends AppCompatActivity {
    private EditText eventName;
    private CheckBox chkImportant;
    private RemindersDbAdapter mDbAdapter;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_reminder);
        eventName = (EditText) findViewById(R.id.editText_event_name);
        mDbAdapter = new RemindersDbAdapter(AddingReminderActivity.this);
        mDbAdapter.open();
        chkImportant = (CheckBox) findViewById(R.id.chkImportant);

        findViewById(R.id.btn_addNew).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String nameOfEvent = eventName.getText().toString();
                if (!isValidEvent(nameOfEvent)) {
                    eventName.setError("Invalid Event Name");
                }else{
                    //TODO insert event into db
                    mDbAdapter.createRemider(nameOfEvent,chkImportant.isChecked());
                    Intent in = new Intent(AddingReminderActivity.this, ReminderActivity.class);
                    startActivity(in);
                }

            }
        });
    }
        // validating email id
        private boolean isValidEmail(String email) {
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

        // validating password with retype password
        private boolean isValidPassword(String pass) {
            if (pass != null && pass.length() > 6) {
                return true;
            }
            return false;
        }

        private boolean isValidEvent(String eventName){
            boolean isValid = true;
            //TODO check event name is not null and unique
            if (StringUtils.isEmpty(eventName) || mDbAdapter.hasReminders(eventName)) {
                isValid = false;
            }
            return isValid;
        }
    }


