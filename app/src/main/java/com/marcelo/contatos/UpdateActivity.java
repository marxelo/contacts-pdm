package com.marcelo.contatos;


import static com.marcelo.contatos.utils.Utils.isValidInput;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.marcelo.contatos.utils.DbHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, phone_input, birthday_input;

    Button update_button;

    String id, name, phone, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.input_name);
        phone_input = findViewById(R.id.input_phone);
        birthday_input = findViewById(R.id.input_birthday);
        update_button = findViewById(R.id.update_button);

        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(name);
        }
        update_button.setOnClickListener(view -> {
            if (isValidInput(name_input.getText().toString().trim(),
                    phone_input.getText().toString().trim(),
                    birthday_input.getText().toString().trim(),
                    UpdateActivity.this)) {
                try (DbHelper dbHelper = new DbHelper(UpdateActivity.this)) {
                    name = name_input.getText().toString().trim();
                    phone = phone_input.getText().toString().trim();
                    birthday = birthday_input.getText().toString().trim();
                    dbHelper.updateData(id, name, phone, birthday);
                }
                finish();
            }
        });
    }


    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("phone") && getIntent().hasExtra("birthday")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            birthday = getIntent().getStringExtra("birthday");

            //Setting Intent Data
            name_input.setText(name);
            phone_input.setText(phone);
            birthday_input.setText(birthday);
        } else {
            Toast.makeText(this, R.string.no_contact, Toast.LENGTH_SHORT).show();
        }
    }


}