package structis.com.reminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import model.Reminder;

/**
 * Created by Thao.nguyenduc on 3/4/2016.
 */
public class RemindersDbAdapter {
    //these are the column names
    public static final String COL_ID = "_id";
    public static final String COL_CONTENT = "content";
    public static final String COL_IMPORTANT = "important";
    //these are the corresponding indices
    public static final int INDEX_ID = 0;
    public static final int INDEX_CONTENT = INDEX_ID + 1;
    public static final int INDEX_IMPORTANT = INDEX_ID + 2;
    //used for logging
    private static final String TAG = "RemindersDbAdapter";
    private static final String DATABASE_NAME = "dba_remdrs";
    private static final String TABLE_NAME = "tbl_remdrs";
    private static final int DATABASE_VERSION = 1;
    //SQL statement used to create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " ( " +
                    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    COL_CONTENT + " TEXT, " +
                    COL_IMPORTANT + " INTEGER );";
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    public RemindersDbAdapter(Context context) {
        this.mCtx = context;
    }

    //open
    public void open() throws SQLException {
        mDbHelper = new DatabaseHelper(this.mCtx);
        mDb = mDbHelper.getWritableDatabase();

    }

    //close
    public void close() throws SQLException {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }


    public void createRemider(String name, boolean important) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CONTENT, name);
        contentValues.put(COL_IMPORTANT, important ? 1 : 0);
        mDb.insert(TABLE_NAME, null, contentValues);
    }

    //overloaded to take a reminder
    public long createReminder(String name, boolean important) {
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, name);
        values.put(COL_IMPORTANT, important ? 0 : 1);
        return mDb.insert(TABLE_NAME, null, values);
    }

    //read
    public Reminder fetchReminderById(int id) {
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_CONTENT, COL_IMPORTANT}, COL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new Reminder(cursor.getInt(INDEX_ID), cursor.getString(INDEX_CONTENT), cursor.getInt(INDEX_IMPORTANT));
    }
    
    public boolean hasReminders(String eventName){
        Cursor cursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_CONTENT, COL_IMPORTANT}, COL_CONTENT + "=?", new String[]{eventName}, null, null, null, null);
        boolean hasData = cursor.moveToFirst();
        return hasData;
            
    }

    //get all reminder

    public Cursor fetchAllReminders(){
        Cursor mCursor = mDb.query(TABLE_NAME, new String[]{COL_ID, COL_CONTENT, COL_IMPORTANT}, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return  mCursor;
    }

    //update reminder
    public void updateReminder(Reminder reminder){
        ContentValues values = new ContentValues();
        values.put(COL_CONTENT, reminder.getmContent());
        values.put(COL_IMPORTANT, reminder.getmImportant());
        mDb.update(TABLE_NAME,values,COL_ID + "=?", new String[]{String.valueOf(reminder.getmId())});
    }

    //delete
    public void deleteReminderById(int nId){
        mDb.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(nId)});
    }

    //delete all Reminders ()
    public void deleteAll(){
        mDb.delete(TABLE_NAME,null,null);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }


}
