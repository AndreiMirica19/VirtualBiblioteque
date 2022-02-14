    package com.example.virtualbiblioteque;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

    public class addBook extends AppCompatActivity {
        private EditText author;
        private  EditText title;
        private EditText description;
        private  EditText Isbn;
        private  CheckBox check;
        private Button btn;
        private  Button btngalery;
        private RadioGroup radioGroup;
        private   RadioButton selectedRadioButton;
        private  Bitmap image;
   private Uri selectedImage;
    private ImageView selectedImageView;
    private static  final int GALERY_REQUEST=100;
    private static  final int CAMERA_REQUEST=200;
        private TextView returnDate;
        private DatePickerDialog.OnDateSetListener DataSetListener;
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbook);
        Intent intent = getIntent();
        author=findViewById(R.id.text1);
        title=findViewById(R.id.text2);
        description=findViewById(R.id.text3);
        Isbn=findViewById(R.id.text4);
        check=findViewById(R.id.radio);
        btn=findViewById(R.id.btn_submit);
        radioGroup=findViewById(R.id.group);
        btngalery=findViewById(R.id.btn_galery);
        selectedImageView=findViewById(R.id.imgView);
        btngalery.setOnClickListener(view->{
            OpenGallery();

        });
       returnDate=(TextView)findViewById(R.id.date);
       returnDate.setOnClickListener(view->{
           Calendar cal=Calendar.getInstance();
           int year=cal.get(Calendar.YEAR);
           int month=cal.get(Calendar.MONTH);
           int day=cal.get(Calendar.DAY_OF_MONTH);
           DatePickerDialog dialog=new DatePickerDialog(this,
                   android.R.style.Theme_Holo_Light_Dialog,DataSetListener,year,month,day);
           dialog.show();
       });
            DataSetListener=new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month+=1;
                    String date=month+"/"+dayOfMonth+"/"+year;
                    returnDate.setText(date);
                }
            };


      btn.setOnClickListener(view->{
          Book book=new Book();
          book.setName(title.getText().toString());
          book.setAuthor(author.getText().toString());
          book.setDescription(description.getText().toString());
          book.setISBN(Isbn.getText().toString());
          if(check.isChecked()) {
              book.setDisponibility(true);
              book.setReturnDate("available");
          }
          else
          {

              book.setDisponibility(false);
              book.setReturnDate(returnDate.getText().toString());
          }
          selectedImageView.invalidate();
          int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
          if (selectedRadioButtonId != -1) {
              selectedRadioButton = findViewById(selectedRadioButtonId);
              String selectedRbText = selectedRadioButton.getText().toString();
              book.setCategory(selectedRbText);
              Intent check = new Intent(this, MainActivity.class);
             /* ByteArrayOutputStream stream = new ByteArrayOutputStream();
              image.compress(Bitmap.CompressFormat.PNG, 10, stream);
              byte[] bytes = stream.toByteArray();
              book.setBytes(bytes);*/


                MainActivity.bitmap=image;
              check.putExtra("book", book);
              setResult(MainActivity.RESULT_OK, check);
              finish();
          }
          else{

              Toast.makeText(this,"you must select the category",Toast.LENGTH_LONG).show();
          }

      });
    }
    public void OpenGallery(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),GALERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALERY_REQUEST&&resultCode==RESULT_OK){
            try{
                 selectedImage=data.getData();
               // InputStream imageStream=getContentResolver().openInputStream(selectedImage);

                image=MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                selectedImageView.setImageBitmap(image);
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
        if(requestCode==CAMERA_REQUEST&&resultCode==RESULT_OK){
            Bundle extras=data.getExtras();
           image=(Bitmap)extras.get("data");

            selectedImageView.setImageBitmap(image);
        }
    }
    public void OpenCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,CAMERA_REQUEST);
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context,addBook.class);
    }
        public  byte[] getBytes() {
            if(image!=null) {


                ByteArrayOutputStream stream;
                stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 0, stream);
                return stream.toByteArray();
            }
            else return null;
        }
    }
