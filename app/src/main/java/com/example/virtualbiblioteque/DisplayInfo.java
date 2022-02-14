package com.example.virtualbiblioteque;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayInfo extends AppCompatActivity {
        private TextView author;
        private  TextView title;
        private TextView description;
        private  TextView Isbn;
        private CheckBox check;
        private Button btn;
        private  Button btngalery;
        private RadioGroup radioGroup;
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.display);
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
           TextView returnDate=(TextView)findViewById(R.id.date);
            returnDate.setText(b.getReturnDate());

    }

}
