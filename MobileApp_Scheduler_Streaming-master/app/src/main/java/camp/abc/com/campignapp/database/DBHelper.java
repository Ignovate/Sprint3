package camp.abc.com.campignapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import camp.abc.com.campignapp.object.Campign;
import camp.abc.com.campignapp.object.Compigncontent;
import camp.abc.com.campignapp.object.Content;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Campign.db";

    public static final String CAMPIGN_TABLE = "Campign_Table";

    public static final String CAMPIGN_ID = "Campign_Id";
    public static final String CAMPIGN_AD_SPACE = "Campign_AD_Space";
    public static final String CAMPIGN_DATE = "Campign_Date";
    public static final String CAMPIGN_HOUR = "Campign_Hour";
    public static final String CAMPIGN_SECONDS = "Campign_Seconds";
    public static final String CAMPIGN_LENGTH = "Campign_Length";

    public static final String CAMPIGN_CONTENT_TABLE = "Campign_Content_Table";

    public static final String CONTENT_ID = "Content_Id";
    public static final String CONTENT_URL = "Content_URL";
    public static final String CONTENT_TYPE = "Content_Type";
    public static final String CONTENT_LENGTH = "Content_Length";
    public static final String CONTENT_DESC = "Content_Desc";

    Context ctx;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + CAMPIGN_TABLE
                + " (id INTEGER PRIMARY KEY, " + CAMPIGN_ID + " text, "
                + CAMPIGN_AD_SPACE + " text, " + CAMPIGN_DATE
                + " TIMESTAMP, " + CAMPIGN_HOUR + " text, " + CAMPIGN_SECONDS
                + " text, " + CAMPIGN_LENGTH + " text)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + CAMPIGN_CONTENT_TABLE
                + " (id INTEGER PRIMARY KEY, " + CAMPIGN_ID + " text, "
                + CAMPIGN_AD_SPACE + " text, " + CONTENT_ID
                + " TIMESTAMP, " + CONTENT_URL + " text, " + CONTENT_TYPE
                + " text, " + CONTENT_LENGTH
                + " text, " + CONTENT_DESC + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CAMPIGN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CAMPIGN_CONTENT_TABLE);

    }

    public void insertCampignData(Campign campign) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAMPIGN_ID, campign.getId());
        contentValues.put(CAMPIGN_AD_SPACE, campign.getAdSpace());
        contentValues.put(CAMPIGN_DATE, campign.getDate());
        contentValues.put(CAMPIGN_HOUR, campign.getHour());
        contentValues.put(CAMPIGN_SECONDS, campign.getSeconds());
        contentValues.put(CAMPIGN_LENGTH, campign.getLength());

        db.insert(CAMPIGN_TABLE, null, contentValues);
        db.close();


    }

    public void insertContentData(Content content) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(CAMPIGN_ID, content.getCampignId());
        contentValues.put(CAMPIGN_AD_SPACE, content.getAdSpace());
        contentValues.put(CONTENT_ID, content.getContentId());
        contentValues.put(CONTENT_URL, content.getContentURL());
        contentValues.put(CONTENT_TYPE, content.getContentType());
        contentValues.put(CONTENT_LENGTH, content.getContentLength());
        contentValues.put(CONTENT_DESC, content.getContentDesc());

        db.insert(CAMPIGN_CONTENT_TABLE, null, contentValues);
        db.close();

    }


    public ArrayList<String> getCount() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CAMPIGN_TABLE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex(CAMPIGN_ID)));
            res.moveToNext();
        }
        if (res != null) {
            res.close();
        }

        Log.e("Count", ": " + array_list.size());

        return array_list;
    }

    public List<Compigncontent> getAllData() {

        List<Compigncontent> rmList = new ArrayList<>();
        String rawQuery = "SELECT Campign_Table.Campign_Id,Campign_Content_Table.Content_Id, Campign_Content_Table.Content_URL,Campign_Content_Table.Content_Type,"+
                "Campign_Content_Table.Content_Length,Campign_Content_Table.Content_Desc,"+
                "Campign_Table.Campign_Id,Campign_Table.Campign_AD_Space,Campign_Table.Campign_Date,"+
                "Campign_Table.Campign_Hour,"+
                "Campign_Table.Campign_Seconds,Campign_Table.Campign_Length FROM Campign_Content_Table"+
                " INNER JOIN Campign_Table "+"ON Campign_Table.Campign_Id = Campign_Content_Table.Campign_Id;";

     /*   String rawQuery = "SELECT CAMPIGN_CONTENT_TABLE.CONTENT_ID,CAMPIGN_CONTENT_TABLE.CAMPIGN_ID,CAMPIGN_CONTENT_TABLE.CONTENT_URL,CAMPIGN_CONTENT_TABLE.CONTENT_TYPE,CAMPIGN_CONTENT_TABLE.CONTENT_LENGTH,CAMPIGN_CONTENT_TABLE.CONTENT_DESC" +
                "CAMPIGN_TABLE.CAMPIGN_ID,CAMPIGN_TABLE.CAMPIGN_AD_SPACE,CAMPIGN_TABLE.CAMPIGN_DATE,CAMPIGN_TABLE.CAMPIGN_HOUR,CAMPIGN_TABLE.CAMPIGN_SECONDS,CAMPIGN_TABLE.CAMPIGN_LENGTH FROM CAMPIGN_CONTENT_TABLE,CAMPIGN_TABLE" +
                "INNER JOIN CAMPIGN_TABLE " + "ON CAMPIGN_TABLE.CAMPIGN_ID = CAMPIGN_CONTENT_TABLE.CAMPIGN_ID;";*/

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(rawQuery, null);
        if (c.moveToFirst()) {
            do {
                String camId = c.getString(c.getColumnIndex(CAMPIGN_ID));
                String canId = c.getString(c.getColumnIndex(CONTENT_ID));
                String camspace = c.getString(c.getColumnIndex(CAMPIGN_AD_SPACE));
                String camdate = c.getString(c.getColumnIndex(CAMPIGN_DATE));
                String camhour = c.getString(c.getColumnIndex(CAMPIGN_HOUR));
                String camsecond = c.getString(c.getColumnIndex(CAMPIGN_SECONDS));
                String camlen = c.getString(c.getColumnIndex(CAMPIGN_LENGTH));
                String conurl = c.getString(c.getColumnIndex(CONTENT_URL));
                String contype = c.getString(c.getColumnIndex(CONTENT_TYPE));
                String conlength = c.getString(c.getColumnIndex(CONTENT_LENGTH));
                String condes = c.getString(c.getColumnIndex(CONTENT_DESC));
                Compigncontent r = new Compigncontent();
                r.setCampignId(camId);
                r.setContentId(canId);
                r.setAdSpace(camspace);
                r.setCampignDate(camdate);
                r.setCampignHour(camhour);
                r.setCampignSeconds(Integer.parseInt(camsecond));
                r.setCampignLength(Integer.parseInt(camlen));
                r.setContentType(contype);
                r.setContentType(contype);
                r.setContentURL(conurl);
                r.setContentLength(conlength);
                r.setContentDesc(condes);

                rmList.add(r);
            } while (c.moveToNext());
        }

        db.close();
        return rmList;

    }

    public ArrayList<Campign> getCampignData() {

        ArrayList<Campign> campignList = new ArrayList<Campign>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CAMPIGN_TABLE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            String campignId = res.getString(res.getColumnIndex(CAMPIGN_ID));
            String adSpace = res.getString(res.getColumnIndex(CAMPIGN_AD_SPACE));
            String campignDate = res.getString(res.getColumnIndex(CAMPIGN_DATE));
            String campignHour = res.getString(res.getColumnIndex(CAMPIGN_HOUR));
            String campignSeconds = res.getString(res.getColumnIndex(CAMPIGN_SECONDS));
            String campignLength = res.getString(res.getColumnIndex(CAMPIGN_LENGTH));

            Campign campign = new Campign(campignId, adSpace, campignDate, campignHour, campignSeconds, campignLength);
            campignList.add(campign);

            res.moveToNext();
        }
        if (res != null) {
            res.close();
        }

        return campignList;

    }

    public ArrayList<Content> getContent(String campignId, String adSpace) {

        ArrayList<Content> contentList = new ArrayList<Content>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CAMPIGN_CONTENT_TABLE + " where " + CAMPIGN_ID + " = '" + campignId + "' and " + CAMPIGN_AD_SPACE + " = '" + adSpace + "'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            String contentId = res.getString(res.getColumnIndex(CONTENT_ID));
            String contentURL = res.getString(res.getColumnIndex(CONTENT_URL));
            String contentType = res.getString(res.getColumnIndex(CONTENT_TYPE));
            String contentLength = res.getString(res.getColumnIndex(CONTENT_LENGTH));
            String contentDesc = res.getString(res.getColumnIndex(CONTENT_DESC));

            Content campign = new Content(campignId, adSpace, contentId, contentURL, contentType, contentLength, contentDesc);
            contentList.add(campign);

            res.moveToNext();
        }
        if (res != null) {
            res.close();
        }

        return contentList;

    }
}
