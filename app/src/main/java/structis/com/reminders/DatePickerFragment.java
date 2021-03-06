package structis.com.reminders;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by Thao.nguyenduc on 4/8/2016.
 */
public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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

    }
}
