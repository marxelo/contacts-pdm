package com.marcelo.contatos;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.marcelo.contatos.utils.Utils;

public class ShowActivity extends AppCompatActivity {
    TextView initials_input, name_input, phone_input, birthday_input;

    String id, initials, name, phone, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initials_input = findViewById(R.id.textView_initials);
        name_input = findViewById(R.id.textView_name);
        phone_input = findViewById(R.id.textView_phone);
        birthday_input = findViewById(R.id.textView_birthday);

        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(name);
        }

    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("phone") && getIntent().hasExtra("birthday")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            birthday = getIntent().getStringExtra("birthday");
            initials = Utils.getInitials(String.valueOf(name.toUpperCase()));

            //Setting Intent Data
            initials_input.setText(initials);
            name_input.setText(name);
            phone_input.setText(phone);
            birthday_input.setText(birthday);
        }else{
            Toast.makeText(this, R.string.no_contact, Toast.LENGTH_SHORT).show();
        }
    }

}
