package structis.com.reminders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.Event;
import services.EventServiceImpl;

/**
 * Created by Thao.nguyenduc on 3/7/2016.
 */
public class AddingReminderActivity extends AppCompatActivity {
    private EditText mEventName;
    private EditText mEventDate;
    private EditText mEventStartTime;
    private EditText mEventOrganizer;
    private EditText mEventLocation;
    private final static List<Event> listEvents = new EventServiceImpl().getAll();
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_reminder);
        this.mEventName = (EditText) findViewById(R.id.editText_event_name);
        this.mEventLocation = (EditText) findViewById(R.id.editText_event_loc);
        this.mEventDate = (EditText) findViewById(R.id.txtDate);
        this.mEventStartTime = (EditText) findViewById(R.id.txtStartTime);
        this.mEventOrganizer = (EditText) findViewById(R.id.editText_event_organizer);

        findViewById(R.id.btn_addNew).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Event evt = new Event();
                evt.setEventName(mEventName.getText().toString());
                evt.setEventLocation(mEventLocation.getText().toString());
                evt.setEventDate(mEventDate.getText().toString());
                evt.setEventStartDate(mEventStartTime.getText().toString());
                evt.setEventOrganizer(mEventOrganizer.getText().toString());

                if (isValidEvent(evt)){
                    AlertDialog alertDialog = builderAlertDialog(evt);
                }
            }
        });
    }

        private AlertDialog builderAlertDialog(final Event evt) {
            AlertDialog alertDialog = new AlertDialog.Builder(AddingReminderActivity.this).create();
            alertDialog.setTitle("Event Information");
            alertDialog.setMessage(evt.toString());

            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resetField();
                            listEvents.add(evt);
                            dialog.dismiss();

                        }
                    });
            alertDialog.show();
            return alertDialog;
        }

        private void resetField() {
            this.mEventName.setText("");
            this.mEventLocation.setText("");
            this.mEventDate.setText("");
            this.mEventStartTime.setText("");
            this.mEventOrganizer.setText("");
        }

        private boolean isValidEvent(Event evt){
            boolean isValid = true;
            //TODO check event name is not null and unique
            if (StringUtils.isEmpty(evt.getEventName()) ) {
              this.mEventName.setError("Event Name must be required");
                isValid = false;
            }else if(isExist(evt.getEventName()))  {
                this.mEventName.setError("Event Name is existed. Please select another name");
                isValid = false;
            }

            if (StringUtils.isEmpty(evt.getEventLocation())){
                this.mEventLocation.setError("Event Location must be required");
                isValid = false;
            }else if(isExist(evt.getEventName()))  {
                this.mEventLocation.setError("Event Location is existed. Please select another location");
                isValid = false;
            }

            if (StringUtils.isEmpty(evt.getEventDate())){
                this.mEventDate.setError("Event Date must be required");
                isValid = false;
            }
            if (StringUtils.isEmpty(evt.getEventOrganizer())){
                this.mEventOrganizer.setError("Event Organizer must be required");
                isValid = false;
            }
            return isValid;
        }

    private boolean isExist(final String criteria) {
        boolean hasEvt = false;
        for (Event evt :listEvents) {
            if (criteria.equalsIgnoreCase(evt.getEventName())){
                hasEvt = true;
                break;
            }
        }
        return  hasEvt;
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");

    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"timePicker");

    }

    public void updateEventDate(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        this.mEventDate.setText(new StringBuilder().append(dayOfMonth).append("-").append(monthOfYear).append("-").append(year));
    }

    public void updateEventTime(TimePicker view, int hour, int minute){
        this.mEventStartTime.setText(new StringBuilder().append(hour).append(":").append(minute));
    }

    public static  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use currrent date as the default in the date picke
            final Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            AddingReminderActivity addingReminderActivity = (AddingReminderActivity) getActivity();
            addingReminderActivity.updateEventDate( view,  year,  monthOfYear+1,  dayOfMonth);
        }
    }

    public static  class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //Use currrent date as the default in the date picke
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            AddingReminderActivity addingReminderActivity = (AddingReminderActivity) getActivity();
            addingReminderActivity.updateEventTime( view,  hourOfDay,  minute);
        }
    }



}


