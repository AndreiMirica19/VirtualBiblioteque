package com.example.virtualbiblioteque;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.IOException;
import java.util.Calendar;

public class EditInfo  extends AppCompatActivity {
    private EditText author;
    private  EditText title;
    private EditText description;
    private  EditText Isbn;
    private CheckBox check;
    private Button btn;
    private  Button btngalery;
    private RadioGroup radioGroup;
    private RadioButton selectedRadioButton;
    private  Bitmap image;
    private Uri selectedImage;
    private ImageView selectedImageView;
    private static  final int GALERY_REQUEST=100;
    private static  final int CAMERA_REQUEST=200;
    private boolean remove;
    private  Button det;
    private TextView returnDate;
    private DatePickerDialog.OnDateSetListener DataSetListener;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editinfo);
        Intent intent = getIntent();
        Book b=intent.getParcelableExtra("book");
        String id=intent.getStringExtra("id");
        //Toast.makeText(this,id, Toast.LENGTH_LONG).show();
        author=findViewById(R.id.text1);
        author.setText(b.getAuthor());
        title=findViewById(R.id.text2);
        title.setText(b.getName());
        description=findViewById(R.id.text3);
        description.setText(b.getDescription());
        Isbn=findViewById(R.id.text4);
        Isbn.setText(b.getISBN());
        check=findViewById(R.id.radio);
        check.setChecked(b.isDisponibility());
        btn=findViewById(R.id.btn_submit);
        radioGroup=findViewById(R.id.group);
        btngalery=findViewById(R.id.btn_galery);
        det = findViewById(R.id.delete);
        if(b.getCategory().equals("Self Improvment")){
            radioGroup.check(R.id.option1);
        }
        else
        if(b.getCategory().equals("Biography")){
            radioGroup.check(R.id.option2);
        }
        if(b.getCategory().equals("Romanian Classic")){
            radioGroup.check(R.id.option3);
        }


        det.setOnClickListener(view->{
         Intent check = new Intent(this, MainActivity.class);
         check.putExtra("book", b);
           check.putExtra("id",id);
            remove=true;
           check.putExtra("remove",remove);
           setResult(MainActivity.RESULT_OK, check);
           finish();
        });
        btngalery.setOnClickListener(view->{
            OpenGallery();
        });
       // Toast.makeText(this,b.getReturnDate(),Toast.LENGTH_SHORT).show();
        returnDate=(TextView)findViewById(R.id.date);
        returnDate.setText(b.getReturnDate());
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
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedRbText = selectedRadioButton.getText().toString();
                book.setCategory(selectedRbText);
                Intent check = new Intent(this, MainActivity.class);
                check.putExtra("book", book);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //   image.compress(Bitmap.CompressFormat.PNG, 10, stream);
                byte[] bytes = stream.toByteArray();
                check.putExtra("id",id);
                remove=false;
                check.putExtra("remove",remove);
                //check.putExtra("BMP",bytes);
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
