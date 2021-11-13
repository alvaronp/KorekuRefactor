package es.unex.giiis.koreku.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.koreku.Perfil;

public final class PerfilCRUD {

    private KorekuDbHelper mDbHelper;
    private static PerfilCRUD mInstance;

    private PerfilCRUD(Context context) {
        mDbHelper = new KorekuDbHelper(context);
    }

    public static PerfilCRUD getInstance(Context context){
        if (mInstance == null)
            mInstance = new PerfilCRUD(context);

        return mInstance;
    }


    public List<Perfil> getAll(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                DBContract.Perfil._ID,
                DBContract.Perfil.COLUMN_NAME_TITLE,
                DBContract.Perfil.COLUMN_NAME_DATE,
                DBContract.Perfil.COLUMN_NAME_PHONE,
                DBContract.Perfil.COLUMN_NAME_MAIL,
                DBContract.Perfil.COLUMN_NAME_IMAGE
        };

        String selection = null;
        String[] selectionArgs = null;

        String sortOrder = null;

        Cursor cursor = db.query(
                DBContract.Perfil.TABLE_NAME,           // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );


        ArrayList<Perfil> items = new ArrayList<>();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                items.add(getPerfilFromCursor(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public long insert(Perfil perfil){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DBContract.Perfil._ID, perfil.getId());
        values.put(DBContract.Perfil.COLUMN_NAME_TITLE, perfil.getTitle());
        values.put(DBContract.Perfil.COLUMN_NAME_PHONE, perfil.getPhone());
        values.put(DBContract.Perfil.COLUMN_NAME_MAIL, perfil.getMail());
        values.put(DBContract.Perfil.COLUMN_NAME_IMAGE, perfil.getImage());
        values.put(DBContract.Perfil.COLUMN_NAME_DATE, Perfil.FORMAT.format(perfil.getDate()));


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DBContract.Perfil.TABLE_NAME, null, values);

        return newRowId;
    }

    public void deleteAll() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Define 'where' part of query.
        String selection = null;
        // Specify arguments in placeholder order.
        String[] selectionArgs = null;

        // Issue SQL statement.
        db.delete(DBContract.Perfil.TABLE_NAME, selection, selectionArgs);
    }

    public int updateImage(long ID, String image) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Log.d("ConsolasCRUD","Item ID: "+ID);

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DBContract.Perfil.COLUMN_NAME_IMAGE, image);

        // Which row to update, based on the ID
        String selection = DBContract.Perfil._ID + " = ?";
        String[] selectionArgs = { Long.toString(ID) };

        int count = db.update(
                DBContract.Perfil.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public void close(){
        if (mDbHelper!=null) mDbHelper.close();
    }

    public static Perfil getPerfilFromCursor(Cursor cursor) {

        long ID = cursor.getInt(cursor.getColumnIndex(DBContract.Perfil._ID));
        String title = cursor.getString(cursor.getColumnIndex(DBContract.Perfil.COLUMN_NAME_TITLE));
        String date = cursor.getString(cursor.getColumnIndex(DBContract.Perfil.COLUMN_NAME_DATE));
        String phone = cursor.getString(cursor.getColumnIndex(DBContract.Perfil.COLUMN_NAME_PHONE));
        String mail = cursor.getString(cursor.getColumnIndex(DBContract.Perfil.COLUMN_NAME_MAIL));
        String image = cursor.getString(cursor.getColumnIndex(DBContract.Perfil.COLUMN_NAME_IMAGE));

        Perfil item = new Perfil(ID,title,date,phone,mail,image);

        Log.d("PerfilCRUD",item.toLog());

        return item;
    }

}
