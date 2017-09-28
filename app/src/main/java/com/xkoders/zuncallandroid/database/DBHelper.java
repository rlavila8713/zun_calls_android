package com.xkoders.zuncallandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.xkoders.zuncallandroid.datasource.ScriptUsersZuncall;
import com.xkoders.zuncallandroid.models.Call;
import com.xkoders.zuncallandroid.models.ZunCallUser;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "zuncall.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "calls_table";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String NAME = "name";
    private static final String COST = "cost";
    private static final String DURATION = "duration";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_NAME + " (id integer primary key, " + PHONE_NUMBER + " text," +
                NAME + " text, " + COST + " text," + DURATION + " text)");

        db.execSQL(ScriptUsersZuncall.SCRIPT_CREAR_TABLA);

        int length = ScriptUsersZuncall.SCRIPT_INSERT_CREATE.length;
        for (int i = 0; i < length; i++) {
            String sql = ScriptUsersZuncall.SCRIPT_INSERT_CREATE[i];
            db.execSQL(sql);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertCall(Call call) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mContentValues = new ContentValues();
        try {
            mContentValues.put(PHONE_NUMBER, call.getDigitos());
            mContentValues.put(NAME, call.getName());
            mContentValues.put(COST, call.getCost());
            mContentValues.put(DURATION, call.getDuration());
            db.insert(TABLE_NAME, null, mContentValues);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Call> getAllCalls() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Call> mCalls = new ArrayList<Call>();

        try {
            Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                Call call = new Call();
                call.setIdCall(c.getInt(c.getColumnIndex("idCall")));
                call.setDigitos(c.getString(c.getColumnIndex("phoneNumber")));
                call.setName(c.getString(c.getColumnIndex("name")));
                call.setCost(c.getString(c.getColumnIndex("cost")));
                call.setDuration(c.getString(c.getColumnIndex("duration")));
                mCalls.add(call);
                c.moveToNext();
            }
            c.close();
            return mCalls;
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<ZunCallUser> getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ZunCallUser> mZunCallUserList = new ArrayList<ZunCallUser>();
        try {
            Cursor c = db.rawQuery("select * from " + ScriptUsersZuncall.TABLA_NAME, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ZunCallUser object = new ZunCallUser();
                object.setId(c.getInt(c.getColumnIndex("id")));
                object.setId_seco(c.getInt(c.getColumnIndex("id_seco")));
                object.setEmail(c.getString(c.getColumnIndex("email")));
                object.setPassword(c.getString(c.getColumnIndex("password")));
                object.setUsername(c.getString(c.getColumnIndex("username")));
                object.setAvatar(c.getString(c.getColumnIndex("avatar")));
                object.setName(c.getString(c.getColumnIndex("name")));
                mZunCallUserList.add(object);
                c.moveToNext();
            }
            c.close();
            return mZunCallUserList;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateProfile(ZunCallUser value) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id_user = value.getId();
        ContentValues mContentValues = new ContentValues();
        try {
            mContentValues.put(ScriptUsersZuncall.NAME, value.getName());
            mContentValues.put(ScriptUsersZuncall.EMAIL, value.getEmail());
            mContentValues.put(ScriptUsersZuncall.USERNAME, value.getUsername());
            mContentValues.put(ScriptUsersZuncall.PASSWORD, value.getPassword());
            db.update(ScriptUsersZuncall.TABLA_NAME, mContentValues, ScriptUsersZuncall.ID + "= ?", new String[]{String.valueOf(id_user)});
        } catch (SQLiteException sqle) {
            return false;
        }
        return true;
    }

    public boolean deleteAllUserExceptActual(ZunCallUser value) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id_user = value.getId();
        try {
            db.delete(ScriptUsersZuncall.TABLA_NAME, ScriptUsersZuncall.ID + "<> ?", new String[]{String.valueOf(id_user)});
        } catch (SQLiteException sqle) {
            return false;
        }
        return true;
    }
}
