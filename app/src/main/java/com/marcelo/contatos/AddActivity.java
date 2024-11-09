package com.marcelo.contatos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.marcelo.contatos.utils.DbHelper;

public class AddActivity extends AppCompatActivity {

    EditText name_input, phone_input, birthday_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        name_input = findViewById(R.id.name_input);
        phone_input = findViewById(R.id.phone_input);
        birthday_input = findViewById(R.id.birthday_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(view -> {
            try (DbHelper dbHelper = new DbHelper(AddActivity.this)) {
                dbHelper.addContact(name_input.getText().toString().trim(),
                        phone_input.getText().toString().trim(),
                        birthday_input.getText().toString().trim());
                setResult(RESULT_OK);
                finish();
            } catch (SQLException e) {
                Log.e("AddActivity", "Error creating DbHelper", e);
            }

        });
    }
}