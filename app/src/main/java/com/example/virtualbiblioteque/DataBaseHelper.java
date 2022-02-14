package com.example.virtualbiblioteque;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String BOOKS_TABLE = "BOOKS_TABLE";
    public static final String COLUMN_CATEGORY = "CATEGORY";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_AUTHOR = "AUTHOR";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_DISPONIBILITY = "DISPONIBILITY";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_ISBM = "ISBM";
    public static final String COLUMN_RETURN="RETURN";

      ContentValues cv=new ContentValues();

    public DataBaseHelper(@Nullable Context context) {
        super(context, "books.db", null, 1);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
           String createTableStatement= "CREATE TABLE " + BOOKS_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                   COLUMN_CATEGORY + " TEXT," + COLUMN_TITLE + " TEXT, " + COLUMN_AUTHOR + " TEXT,"+ COLUMN_ISBM+ " TEXT," + COLUMN_DESCRIPTION + " TEXT, " +COLUMN_RETURN + " TEXT, " + COLUMN_DISPONIBILITY + " BOOL )";
           db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOne(Book book){
        SQLiteDatabase db=this.getWritableDatabase();
        cv.put(COLUMN_CATEGORY,book.getCategory());
        cv.put(COLUMN_TITLE,book.getName());
        cv.put(COLUMN_AUTHOR,book.getAuthor());
        cv.put(COLUMN_DESCRIPTION,book.getDescription());
        cv.put(COLUMN_DISPONIBILITY,book.isDisponibility());
        cv.put(COLUMN_ISBM,book.getISBN());
        cv.put(COLUMN_RETURN,book.getReturnDate());
        long insert=  db.insert(BOOKS_TABLE,null,cv);
        if(insert==-1)
                return  false;
        else
                return  true;

    }

    public boolean isEmpty() {
        String queryString=" SELECT * FROM "+ BOOKS_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(queryString,null);
        if(cursor.moveToFirst()==false){
            cursor.close();
            db.close();
            return true;

        }
        cursor.close();
        db.close();
        return false;

    }
    public ArrayList<Book> getAllData(){
        ArrayList<Book>a=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String queryString=" SELECT * FROM "+ BOOKS_TABLE;
        Cursor cursor=db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
       while (!cursor.isLast()){
            String name = cursor.getString(2);
            String author = cursor.getString(3);
            String Description = cursor.getString(5);
            String ISBN = cursor.getString(4);
      boolean disponibility = cursor.getInt(7) == 1 ? true : false;
            String Category = cursor.getString(1);
            String id=cursor.getString(0);
            Book book = new Book(Category, name, Description, ISBN, author,id);
          book.setDisponibility(disponibility);
           book.setReturnDate(cursor.getString(6));
            a.add(book);

            cursor.moveToNext();
        }
            String name = cursor.getString(2);
            String author = cursor.getString(3);
            String Description = cursor.getString(5);
            String ISBN = cursor.getString(4);
          boolean disponibility = cursor.getInt(7) == 1 ? true : false;
            String Category = cursor.getString(1);
            String id=cursor.getString(0);
            Book book = new Book(Category, name, Description, ISBN, author,id);
            book.setReturnDate(cursor.getString(6));
         book.setDisponibility(disponibility);
            a.add(book);
        }
        cursor.close();
        db.close();
       return a;
    }

    public ArrayList<String>getNames(){
        SQLiteDatabase db=getReadableDatabase();
        ArrayList<String>a=new ArrayList<>();

        String queryString=" SELECT * FROM "+ BOOKS_TABLE;
        Cursor cursor=db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            while (!cursor.isLast()){
                String name = cursor.getString(2);
                a.add(name);
                cursor.moveToNext();
            }

        }
        cursor.close();
        db.close();
        return a;
    }
    public  Integer deleteData(Book book){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(BOOKS_TABLE,  "ID=?",new String[] {book.getId()});
    }
      public boolean updateData(Book book){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
          cv.put(COLUMN_CATEGORY,book.getCategory());
          cv.put(COLUMN_TITLE,book.getName());
          cv.put(COLUMN_AUTHOR,book.getAuthor());
          cv.put(COLUMN_DESCRIPTION,book.getDescription());
          cv.put(COLUMN_DISPONIBILITY,book.isDisponibility());
          cv.put(COLUMN_ISBM,book.getISBN());
          cv.put(COLUMN_RETURN,book.getReturnDate());
          db.update(BOOKS_TABLE, cv, "ID=?",new String[] {book.getId()});
          return true;
      }

    }



