package com.marcelo.contatos.utils;

import android.content.Context;
import android.widget.Toast;

import com.marcelo.contatos.AddActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String getInitials(String str) {
        if (str.trim().isEmpty()) {
            return "?";
        }

        // Replace all multiple spaces with a single space.
        str = str.replaceAll("\\s+", " ");

        // Split the string into words and remove empty words.
        String[] words = str.split(" ");
        List<String> nonEmptyWords = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                nonEmptyWords.add(word);
            }
        }

        if (nonEmptyWords.size() == 1) {
            return String.valueOf(nonEmptyWords.get(0).charAt(0));
        }

        return nonEmptyWords.get(0).charAt(0) + String.valueOf(nonEmptyWords.get(nonEmptyWords.size() - 1).charAt(0));
    }

    public static boolean isValidName(String name) {
        return !name.isEmpty();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number is null or empty
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        // Check if the phone number contains only digits
        if (!phoneNumber.matches("\\d+")) {
            return false;
        }

        // Check the length of the phone number
        int length = phoneNumber.length();
        return length >= 3 && length <= 15;
    }

    public static boolean isValidDate(String dateString) {
        SimpleDateFormat[] formats = {
                new SimpleDateFormat("dd/MM"),
                new SimpleDateFormat("ddMM")
        };

        for (SimpleDateFormat format : formats) {
            try {
                format.setLenient(false); // Strict date parsing
                format.parse(dateString);
                return true;
            } catch (ParseException e) {
                // Ignore and try the next format
            }
        }
        return false;
    }

    public static boolean isValidInput(String name, String phone, String birthday, Context context) {

        if (!isValidName(name)) {
            Toast.makeText(context, "Informe o nome", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!isValidPhoneNumber(phone)) {
            Toast.makeText(context, "Número de telefone deve ter entre 3 e 15 dígitos", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidDate(birthday)) {
            Toast.makeText(context, "Data de aniversário inválida", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
