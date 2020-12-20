package com.davida.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private String text;
    private int position;
    private EditText etML;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position", 0);
        etML = findViewById(R.id.etML);
        etML.setText(text);
    }

    public void onSubmit(View v){
        this.finish();
    }


    public void onClick(View v) {
        EditText etML = (EditText) findViewById(R.id.etML);
        String itemText = etML.getText().toString();
        Intent i = new Intent(EditItemActivity.this, MainActivity.class);
        i.putExtra("new text", itemText);
        i.putExtra("position", position);
        setResult(25, i);
        this.finish();
    }
}