package com.example.rahul.mygrocerylist.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.rahul.mygrocerylist.R;

public class DetailsActivity extends AppCompatActivity {
private Bundle bundle;
private TextView name,qty,date;
private Button btn1,btn2;
private  int groceryId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name=findViewById(R.id.itemNameDet);
        qty=findViewById(R.id.itemQuantityDet);
        date=findViewById(R.id.itemDateAddedDet);
        btn1=findViewById(R.id.editButtonDet);
        btn2=findViewById(R.id.deleteButtonDet);
        bundle=getIntent().getExtras();
        if (bundle!=null){
            name.setText(bundle.getString("name"));
            qty.setText(bundle.getString("qty"));
            date.setText(bundle.getString("date"));
              groceryId=bundle.getInt("id");
        }
    }
}
