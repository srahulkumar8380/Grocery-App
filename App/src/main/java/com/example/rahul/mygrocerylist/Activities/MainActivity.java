package com.example.rahul.mygrocerylist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.rahul.mygrocerylist.Data.DatabaseHandler;
import com.example.rahul.mygrocerylist.Model.Grocery;
import com.example.rahul.mygrocerylist.R;

public class MainActivity extends AppCompatActivity {
private AlertDialog.Builder dilogbuilder;
private AlertDialog dailog;
private EditText groceryItem ,groceryQuantity  ;
private Button saveButoon;
public DatabaseHandler db;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db= new DatabaseHandler(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  createPopupDialog();
            }
        });
        byPassActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

     private void  createPopupDialog(){
      dilogbuilder= new AlertDialog.Builder(this);
      View view= getLayoutInflater().inflate(R.layout.popup_layout,null);

      groceryItem=view.findViewById(R.id.groceryItem);
      groceryQuantity=view.findViewById(R.id.groceryQuantity);
      saveButoon=view.findViewById(R.id.saveButton);

      dilogbuilder.setView(view);
      dailog=dilogbuilder.create();
      dailog.show();

      saveButoon.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (!groceryItem.getText().toString().isEmpty() && !groceryQuantity.getText().toString().isEmpty()){
                saveGroceryToDB(v);
              }

          }
      });
    }
    private void saveGroceryToDB(View view){
        Grocery grocery =new Grocery();
        String newGrocery=groceryItem.getText().toString();
        String newGroceryQuantity=groceryQuantity.getText().toString();

        grocery.setName(newGrocery);
        grocery.setQuantity(newGroceryQuantity);
        // Saved to db

     db.addGrocery(grocery);

        Snackbar.make(view,"Item Saved!",Snackbar.LENGTH_LONG ).show();
    //    Log.d("Item Added_Id",String.valueOf(db.getGroceriesCount()));
         new Handler().postDelayed(new Runnable() {
             @Override
             public void run() {
                  dailog.dismiss();
                  //start a new activity
                 startActivity(new Intent(MainActivity.this,ListActivity.class));
             }
         },800);

    }
    public  void byPassActivity(){
        //  check if database isEmpty ; if not we just go to listView and show all the list
        if (db.getGroceriesCount()>0){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
            finish();
        }
    }
}
