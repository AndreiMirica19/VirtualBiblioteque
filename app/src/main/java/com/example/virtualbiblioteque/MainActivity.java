package com.example.virtualbiblioteque;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Book> Self = new ArrayList<>();
   private ArrayList<Book>Bio=new ArrayList<>();
   private ArrayList<Book>cls=new ArrayList<>();
  protected static ArrayList<Book> books=new ArrayList<>();

   private DataBaseHelper dataBaseHelper=new DataBaseHelper(MainActivity.this);
    private LinearLayout selfHelpBook;
   private LinearLayout biograpy;
    private LinearLayout romanian;
   private int index;
   protected static Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selfHelpBook = (LinearLayout) findViewById(R.id.l1);
        biograpy=(LinearLayout)findViewById(R.id.l2);
        romanian=(LinearLayout)findViewById(R.id.l3);

        Intent intent = new Intent(this,DisplayInfo.class);
         /*createbtn(this,selfHelpBook,Self);
         createbtn(this,biograpy,Bio);
         createbtn(this,romanian,cls);*/
        this.deleteDatabase(" dataBaseHelper");
        Button btn1 = new Button(this);
       Button btn2=findViewById(R.id.btn_submit2);
        btn2.setOnClickListener(view->{

            Intent intent2=new Intent(this,Show.class);
            startActivity(intent2);
        });
        Button btnAdd=findViewById(R.id.btn_submit);
     btnAdd.setOnClickListener(view->{
         Intent intentAdd=addBook.makeIntent(MainActivity.this);

         startActivityForResult(intentAdd,19);

      });
       AddDbElems(dataBaseHelper);
       Button btnsearch=findViewById(R.id.Search);
       btnsearch.setOnClickListener(view->{
           EditText editText=findViewById(R.id.text1);
          Boolean c=false;
           for(int i=0;i<books.size();i++){

              if(books.get(i).getName().equalsIgnoreCase(editText.getText().toString())){
                   Intent intentSearch = new Intent(this, DisplayInfo.class);
                   intentSearch.putExtra("book", books.get(i));
                   startActivity(intentSearch);
                   c=true;
               }

            }
           if(c==false){
               Toast.makeText(this,"The book doesn't exist",Toast.LENGTH_LONG).show();

           }
       });
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

       super.onActivityResult(requestCode, resultCode, data);
       if (requestCode == 19) {
           if (resultCode == RESULT_OK) {

               Book b = (data.getParcelableExtra("book"));
               String path= Environment.getExternalStorageDirectory() + b.getPath() + ".png";
              /* Toast.makeText(this, b.getBytes().length, Toast.LENGTH_LONG).show();*/


              LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
               ImageButton btnTag = new ImageButton(this);
               btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
               btnTag.setImageBitmap(bitmap);
              addbtn(b);
          refresh();
           AddDbElems(dataBaseHelper);
           }
       }
       if (requestCode == 21) {
           if (resultCode == RESULT_OK) {

               Book b = (data.getParcelableExtra("book"));
               String id = data.getStringExtra("id");
               b.setId(id);
               Boolean r = data.getBooleanExtra("remove", false);
               if (r == true) {
                   int bb = dataBaseHelper.deleteData(b);
                   refresh();
                   AddDbElems(dataBaseHelper);
               } else {
                   if (b.getCategory().equalsIgnoreCase("Self Improvment"))
                       Self.set(index, b);
                   if (b.getCategory().equalsIgnoreCase("Biography"))
                       Bio.set(index, b);
                   if (b.getCategory().equalsIgnoreCase("Romanian Classic"))
                       cls.set(index, b);
                //   Toast.makeText(this, b.getId(), Toast.LENGTH_LONG).show();
                   boolean bb = dataBaseHelper.updateData(b);
                   refresh();
                   AddDbElems(dataBaseHelper);
               }
           }
       }
   }
 void refresh(){
     LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);

         ((LinearLayout) l1).removeAllViews();
     LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);

         ((LinearLayout) l2).removeAllViews();
     LinearLayout l3 = (LinearLayout) findViewById(R.id.l3);

         ((LinearLayout) l3).removeAllViews();
     Self.clear();
     Bio.clear();
     cls.clear();
      books.clear();
 }
   @SuppressLint("ResourceAsColor")
   void addbtn(Book b){
       if(b.getCategory().equals("Self Improvment")) {
           Self.add(b);
           LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
           MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
           btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
         btnTag.setText(Self.get(Self.size() - 1).getName());
           btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_200));
           boolean bb = dataBaseHelper.addOne(Self.get(Self.size() - 1));
           l1.addView(btnTag);
           String id=b.getId();
           //if(b.isNUll())
             // Toast.makeText(this,"UnSuccess",Toast.LENGTH_SHORT).show();
          // btnTag.setImageBitmap(Self.get(Self.size() - 1).getBtm());
           Intent intent = new Intent(this, DisplayInfo.class);
           btnTag.setOnClickListener(view -> {
               intent.putExtra("book", b);
               startActivity(intent);
           });
           btnTag.setOnLongClickListener(view -> {

               Intent intentEdit = new Intent(this, EditInfo.class);
               intentEdit.putExtra("book", b);
               intentEdit.putExtra("id",id);
               startActivityForResult(intentEdit,21);
               index=(Self.size() - 1);
               return true;
           });
       }
       else
       if(b.getCategory().equals("Biography")) {
           Bio.add(b);
           LinearLayout l1 = (LinearLayout) findViewById(R.id.l2);
           MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
           btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
           btnTag.setText(Bio.get(Bio.size() - 1).getName());
           btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.purple_200));
           boolean bb = dataBaseHelper.addOne(Bio.get(Bio.size() - 1));
           l1.addView(btnTag);
           String id=b.getId();
           Intent intent = new Intent(this, DisplayInfo.class);
           btnTag.setOnClickListener(view -> {
               intent.putExtra("book", b);
               startActivity(intent);
           });
           btnTag.setOnLongClickListener(view -> {

               Intent intentEdit = new Intent(this, EditInfo.class);
               intentEdit.putExtra("book", b);
               intentEdit.putExtra("id",id);
               startActivityForResult(intentEdit,21);
               index=(Bio.size() - 1);
               return true;
           });
       } else
       if(b.getCategory().equals("Romanian Classic")) {
           cls.add(b);
           LinearLayout l1 = (LinearLayout) findViewById(R.id.l3);
           MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
           btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
           btnTag.setText(cls.get(cls.size() - 1).getName());
           btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));
           String id=b.getId();
           boolean bb = dataBaseHelper.addOne(cls.get(cls.size() - 1));
           l1.addView(btnTag);
           Intent intent = new Intent(this, DisplayInfo.class);
           btnTag.setOnClickListener(view -> {
               intent.putExtra("book", b);
               startActivity(intent);
           });
           btnTag.setOnLongClickListener(view -> {

               Intent intentEdit = new Intent(this, EditInfo.class);
               intentEdit.putExtra("book", b);
               intentEdit.putExtra("id",id);
               startActivityForResult(intentEdit,21);
               index=(cls.size() - 1);
               return true;
           });
       }

   }





   void AddDbElems(DataBaseHelper db){
         if(books.isEmpty())
             books=db.getAllData();

         for(int i=0;i<books.size();i++){
            Book b=books.get(i);
             if(b.getCategory().equals("Self Improvment")) {
                 Self.add(b);
                 LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
              MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
                 btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                 btnTag.setText(Self.get(Self.size() - 1).getName());
                 l1.addView(btnTag);
                 btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_200));
                 String id=b.getId();
                 // btnTag.setImageBitmap(Self.get(Self.size() - 1).getBtm());
                 Intent intent = new Intent(this, DisplayInfo.class);
                 btnTag.setOnClickListener(view -> {
                     intent.putExtra("book", b);
                     startActivity(intent);
                 });
                 btnTag.setOnLongClickListener(view -> {

                     Intent intentEdit = new Intent(this, EditInfo.class);
                     intentEdit.putExtra("book", b);
                     intentEdit.putExtra("id",id);
                     startActivityForResult(intentEdit,21);
                     index=(Self.size() - 1);
                     return true;
                 });
             }

             if(b.getCategory().equals("Biography")) {
                 Bio.add(b);
                 LinearLayout l1 = (LinearLayout) findViewById(R.id.l2);
                 MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
                 btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                 btnTag.setText(Bio.get(Bio.size() - 1).getName());
                 btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.purple_200));
                 String id=b.getId();
                 l1.addView(btnTag);
                 Intent intent = new Intent(this, DisplayInfo.class);
                 btnTag.setOnClickListener(view -> {
                     intent.putExtra("book", b);
                     startActivity(intent);
                 });
                 btnTag.setOnLongClickListener(view -> {

                     Intent intentEdit = new Intent(this, EditInfo.class);
                     intentEdit.putExtra("book", b);
                     intentEdit.putExtra("id",id);
                     startActivityForResult(intentEdit,21);
                     index=(Bio.size() - 1);
                     return true;
                 });
             }
             if(b.getCategory().equals("Romanian Classic")) {
                 cls.add(b);
                 LinearLayout l1 = (LinearLayout) findViewById(R.id.l3);
                 MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
                 btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                 btnTag.setText(cls.get(cls.size() - 1).getName());
                 btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));
                 String id=b.getId();
                 l1.addView(btnTag);
                 Intent intent = new Intent(this, DisplayInfo.class);
                 btnTag.setOnClickListener(view -> {
                     intent.putExtra("book", b);
                     startActivity(intent);
                 });
                 btnTag.setOnLongClickListener(view -> {

                     Intent intentEdit = new Intent(this, EditInfo.class);
                     intentEdit.putExtra("book", b);
                     intentEdit.putExtra("id",id);
                     startActivityForResult(intentEdit,21);
                     index=(cls.size() - 1);
                     return true;
                 });
             }
         }


        }
    void bookCreationFirstTime(){
      /* Book b1 = new Book("Self Improvment","Mark Manson", "A book about hope", "12231", "Mark Manson");
       Book b2 = new Book("Self Improvment","You are a baddas", " IS THE SELF-HELP BOOK FOR PEOPLE WHO DESPERATELY WANT TO IMPROVE THEIR LIVES", "1232", "Jen Sincero");
       Book b3 = new Book("Self Improvment","Power of Now", "How to manange to live in the present", "1999", "Eckhart Tolle");
       Book b4 = new Book("Self Improvment","Rich dad Poor dad", "How to get rich", "29832", "Robert Kiyosaki");
       Book b5=new Book("Biography","Elon Musk","In Elon Musk: Tesla, SpaceX, and the Quest for a Fantastic Future, veteran technology journalist Ashlee Vance provides the first inside look into the extraordinary life and times of Silicon Valley's most audacious entrepreneur"
       ,"23432","Ashlee Vance");
       Book b6=new Book("Biography","Steve Jobs ","The most important person in Apple history","1984","Walter Issacson");
       Book b7=new Book("Biography","Unbroken"," A World War II Story of Survival, Resilience, and Redemption","7584","Laura Hillenbrand");
       Book b8=new Book("Biography","Creativity, Inc","Overcoming the Unseen Forces That Stand in the Way of True Inspiration ","34432","Ed Catmull ");
       Book b9=new Book("Romanian Classic","Maitreyi","Love story between two young adults in India","34511","Mircea Eliade");
       Book b10=new Book("Romanian Classic","Ciresarii","Life at countryside","1211","Constantin Chirita");
       Book b11=new Book("Romanian Classic","Ion","a man that was in love with his land","2117","Liviu Rebreanu");
       Self.add(b1);
       Self.add(b2);
       Self.add(b3);
       Self.add(b4);
       Bio.add(b5);
       Bio.add(b6);
       Bio.add(b7);
       Bio.add(b8);
       cls.add(b9);
       cls.add(b10);
       cls.add(b11);
     if(dataBaseHelper.isEmpty()){
         boolean bb = dataBaseHelper.addOne(Self.get(Self.size() - 1));
         bb = dataBaseHelper.addOne(Self.get(Self.size() - 1));
         bb = dataBaseHelper.addOne(Self.get(Self.size() - 1));
         bb = dataBaseHelper.addOne(Self.get(Self.size() - 1));
         bb = dataBaseHelper.addOne(Bio.get(Bio.size() - 1));
         bb = dataBaseHelper.addOne(Bio.get(Bio.size() - 1));
         bb = dataBaseHelper.addOne(Bio.get(Bio.size() - 1));
         bb = dataBaseHelper.addOne(Bio.get(Bio.size() - 1));
         bb = dataBaseHelper.addOne(cls.get(cls.size() - 1));
         bb = dataBaseHelper.addOne(cls.get(cls.size() - 1));
         bb = dataBaseHelper.addOne(cls.get(cls.size() - 1));
     }*/

    }



   }

