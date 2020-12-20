package com.davida.myapplication;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private Button btnAddItem;
    private final int REQUEST_CODE = 20;
    private final int RESULT_OK = 25 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items  = new ArrayList<>();
        btnAddItem = findViewById(R.id.btnAddItem);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                items);
        lvItems.setAdapter(itemsAdapter);
        launchComposeView();
        setupListViewListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }

    public void onAddItem(View v){
         EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
         String itemText = etNewItem.getText().toString();
         if (itemText.matches(".*\\w.*"))
            itemsAdapter.add(itemText);
         etNewItem.setText("");
         writeItems();
     }

     private void readItems(){
         File filesDir = getFilesDir();
         File todoFile = new File(filesDir, "todo.txt");

         try {
             items = new ArrayList<String>(FileUtils.readLines(todoFile));
         } catch (IOException e) {
             items = new ArrayList<String>();
         }
     }

     private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

     public void launchComposeView(){
         Intent i = new Intent(MainActivity.this, EditItemActivity.class);
         lvItems.setOnItemClickListener(
                 new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String text = items.get(position);
                        i.putExtra("text", text);
                        i.putExtra("position", position);
                        startActivityForResult(i, REQUEST_CODE);
                     }
                 }
         );
         //startActivityForResult(i, REQUEST_CODE);
     }



    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String newText = data.getStringExtra("new text");
        int pos = data.getIntExtra("position", 0);
        if (newText.matches(".*\\w.*"))
            items.set(pos, newText);
        else
            items.remove(pos);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }
}