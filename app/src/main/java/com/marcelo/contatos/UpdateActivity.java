package com.marcelo.contatos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marcelo.contatos.utils.DbHelper;

public class UpdateActivity extends AppCompatActivity {

    EditText name_input, phone_input, birthday_input;

    Button update_button, delete_button;

    String id, name, phone, birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name_input = findViewById(R.id.name_input2);
        phone_input = findViewById(R.id.phone_input2);
        birthday_input = findViewById(R.id.birthday_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(name);;
        }
        update_button.setOnClickListener(view -> {
            try (DbHelper dbHelper = new DbHelper(UpdateActivity.this)) {
                name = name_input.getText().toString().trim();
                phone = phone_input.getText().toString().trim();
                birthday = birthday_input.getText().toString().trim();
                dbHelper.updateData(id, name, phone, birthday);
            }
            finish();
        });

        delete_button.setOnClickListener(v -> confirmDialog());

    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("phone") && getIntent().hasExtra("birthday")){
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            phone = getIntent().getStringExtra("phone");
            birthday = getIntent().getStringExtra("birthday");

            //Setting Intent Data
            name_input.setText(name);
            phone_input.setText(phone);
            birthday_input.setText(birthday);
        }else{
            Toast.makeText(this, R.string.no_contact, Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle(getString(R.string.delete) + name + "?");
        builder.setMessage(getString(R.string.confirm_delete_message) + name + "?");
        builder.setPositiveButton("Sim", (dialog, which) -> {
            try (DbHelper dbHelper = new DbHelper(UpdateActivity.this)) {
                dbHelper.deleteOneRow(id);
            }
            finish();
        });
        builder.setNegativeButton("Não", (dialog, which) -> {

        });
        builder.create().show();
    }
}