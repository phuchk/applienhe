package com.example.linh.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.linh.model.Contact;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "contact.db";
    private static final int DB_VERSION = 1;

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE contact(" + "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "phone text,name TEXT,img blod,timelastuse text,datelastuse TEXT,islove text)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    private byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    private Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public Contact getById(int id) {
        Contact c = new Contact();
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("contact",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id1 = rs.getInt(0);
            String phone = rs.getString(1);
            String name = rs.getString(2);
            Bitmap img = getImage(rs.getBlob(3));
            String timelastuse = rs.getString(4);
            String datelastuse = rs.getString(5);
            String islove = rs.getString(6);
            c = new Contact(id, phone, name, img, timelastuse, datelastuse, islove);
        }
        return c;
    }

    public long addItem(Contact i) {
        ContentValues values = new ContentValues();
        values.put("phone", i.getPhone());
        values.put("name", i.getName());
        byte[] img = new byte[0];
        values.put("img", getBytes(i.getImg()));
        values.put("timelastuse", i.getTimelastuse());
        values.put("datelastuse", i.getDatelastuse());
        values.put("islove", i.getIsLove());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("contact", null, values);
    }

    public List<Contact> getAll() {
        List<Contact> list = new ArrayList<>();
        String order = "name ASC";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("contact",
                null, null, null,
                null, null, order);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String phone = rs.getString(1);
            String name = rs.getString(2);
            Bitmap img = getImage(rs.getBlob(3));
            String timelastuse = rs.getString(4);
            String datelastuse = rs.getString(5);
            String islove = rs.getString(6);
            list.add(new Contact(id, phone, name, img, timelastuse, datelastuse, islove));
        }
        return list;
    }

    public List<Contact> getLove() {
        List<Contact> list = new ArrayList<>();
        String whereClause = "islove = ?";
        String[] whereArgs = {"1"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("contact",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String phone = rs.getString(1);
            String name = rs.getString(2);
            Bitmap img = getImage(rs.getBlob(3));
            String timelastuse = rs.getString(4);
            String datelastuse = rs.getString(5);
            String islove = rs.getString(6);
            list.add(new Contact(id, phone, name, img, timelastuse, datelastuse, islove));
        }
        return list;
    }

    public int updateLove(Contact i) {
        ContentValues values = new ContentValues();
        values.put("islove", i.getIsLove());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("contact",
                values, whereClause, whereArgs);
    }

    public int updateContact(Contact i) {
        ContentValues values = new ContentValues();
        values.put("name", i.getName());
        values.put("phone", i.getPhone());
        byte[] img = new byte[0];
        values.put("img", getBytes(i.getImg()));
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("contact",
                values, whereClause, whereArgs);
    }

    public int delete(Contact c) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(c.getId())};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("contact",
                whereClause, whereArgs);
    }

    public List<Contact> search(String key) {
        List<Contact> list = new ArrayList<>();
        String whereClause = "phone like  ? or name like ?";
        String[] whereArgs = {"%" + key + "%", "%" + key + "%"};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("contact",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String phone = rs.getString(1);
            String name = rs.getString(2);
            Bitmap img = getImage(rs.getBlob(3));
            String timelastuse = rs.getString(4);
            String datelastuse = rs.getString(5);
            String islove = rs.getString(6);
            list.add(new Contact(id, phone, name, img, timelastuse, datelastuse, islove));
        }
        return list;
    }

    public int updateLast(Contact i) {
        ContentValues values = new ContentValues();
        values.put("timelastuse", i.getTimelastuse());
        values.put("datelastuse", i.getDatelastuse());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("contact",
                values, whereClause, whereArgs);
    }


    public List<Contact> getAllByTime() {
        List<Contact> list = new ArrayList<>();
        String order = "timelastuse DESC";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor rs = sqLiteDatabase.query("contact",
                null, null, null,
                null, null, order);
        while ((rs != null) && (rs.moveToNext())) {
            int id = rs.getInt(0);
            String phone = rs.getString(1);
            String name = rs.getString(2);
            Bitmap img = getImage(rs.getBlob(3));
            String timelastuse = rs.getString(4);
            String datelastuse = rs.getString(5);
            String islove = rs.getString(6);
            if (timelastuse.length() > 0)
                list.add(new Contact(id, phone, name, img, timelastuse, datelastuse, islove));
        }
        return list;
    }

}
