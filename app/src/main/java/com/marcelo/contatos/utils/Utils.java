package com.marcelo.contatos.utils;

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
}
