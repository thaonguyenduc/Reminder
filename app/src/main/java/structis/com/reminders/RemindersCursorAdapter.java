package structis.com.reminders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Thao.nguyenduc on 3/7/2016.
 */
public class RemindersCursorAdapter extends SimpleCursorAdapter {

    public RemindersCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.colImp = cursor.getColumnIndexOrThrow(RemindersDbAdapter.COL_IMPORTANT);
            viewHolder.listTab = view.findViewById(R.id.row_tab);
            view.setTag(viewHolder);
        }
        if (cursor.getInt(viewHolder.colImp) > 0) {
            viewHolder.listTab.setBackgroundColor(context.getResources().getColor(R.color.orange));
        } else {
            viewHolder.listTab.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }

    static class ViewHolder {
        //store the column index
        int colImp;
        //store the view
        View listTab;
    }
}
