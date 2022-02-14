package com.example.virtualbiblioteque;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import static android.util.Base64.encodeToString;
import static java.text.DateFormat.DEFAULT;
import static java.util.Base64.*;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.Toast;

public class Book implements Parcelable  {
    private String name;
    private String author;
    private String Description;
    private String ISBN;
    private boolean disponibility;
    private Bitmap image;
    private String Category;
    private ByteArrayOutputStream stream;
    private   String id;
    private String Id;
    private byte[] bytes;
    private String returnDate;
    private String path;
    public String getCategory() {
        return Category;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        Id=id;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public Book(String category,String name, String description, String ISBN, String Author) {
        Category=category;
        this.name = name;
        Description = description;
        this.ISBN = ISBN;
        author=Author;
        returnDate="";
        path=null;
    }
    public Book(String category,String name, String description, String ISBN, String Author,String id) {
        Category=category;
        this.name = name;
        Description = description;
        this.ISBN = ISBN;
        author=Author;
        this.id=id;
        returnDate="";
        path=null;
    }
    public Bitmap getBtm(){
        return image;
    }
    public Book(){

    }
    public boolean isNUll(){
        if(image==null)
            return  true;
        else
            return false;
    }

    public  byte[] getBytes() {
        if(image!=null) {



           stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 0, stream);
            return stream.toByteArray();
        }
        else return null;
    }


    public Bitmap getImage(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }



    protected Book(Parcel in) {

        name = in.readString();
        author = in.readString();
        Description = in.readString();
        ISBN = in.readString();
        disponibility = in.readByte() != 0;
        returnDate=in.readString();
        Category=in.readString();
        id=in.readString();
    /*    bytes = new byte[in.readInt()];
           in.readByteArray(bytes);*/
           path=in.readString();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isDisponibility() {
        return disponibility;
    }

    public void setDisponibility(boolean disponibility) {
        this.disponibility = disponibility;

    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeString(name);
        dest.writeString(author);
        dest.writeString(Description);
        dest.writeString(ISBN);
        dest.writeByte((byte) (disponibility ? 1 : 0));
        dest.writeString(returnDate);
        dest.writeString(Category);
        dest.writeValue(id);

        //dest.writeByteArray(bytes);



    }
}
