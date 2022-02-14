package com.example.virtualbiblioteque;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class Show extends AppCompatActivity {
    static ArrayList<Book> Self = new ArrayList<>();
    static ArrayList<Book> Bio = new ArrayList<>();
    static ArrayList<Book> cls = new ArrayList<>();
    private LinearLayout selfHelpBook;
    private LinearLayout biograpy;
    private LinearLayout romanian;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        Intent intent = getIntent();
        ArrayList<Book> books = MainActivity.books;
        for (int i = 0; i < books.size(); i++) {
            if (!books.get(i).isDisponibility()) {
                addbook(books.get(i));
            }
        }
    }

    void addbook(Book b) {
        if (b.getCategory().equals("Self Improvment")) {
            Self.add(b);
            LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
            MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
            btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_200));
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(Self.get(Self.size() - 1).getName());
            l1.addView(btnTag);
        } else if (b.getCategory().equals("Biography")) {
            Bio.add(b);
            LinearLayout l1 = (LinearLayout) findViewById(R.id.l2);
            MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
            btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.purple_200));
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(Bio.get(Bio.size() - 1).getName());

            l1.addView(btnTag);


        } else if (b.getCategory().equals("Romanian Classic")) {
            cls.add(b);
            LinearLayout l1 = (LinearLayout) findViewById(R.id.l3);
            MaterialButton btnTag = new MaterialButton(this,null,R.attr.materialButtonStyle);
            btnTag.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.teal_700));
            btnTag.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            btnTag.setText(cls.get(cls.size() - 1).getName());
            l1.addView(btnTag);
            Intent intent = new Intent(this, DisplayInfo.class);


        }

    }
}


