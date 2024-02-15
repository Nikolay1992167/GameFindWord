package by.solbeg.service;

import by.solbeg.enam.Color;
import by.solbeg.exception.ValidateException;
import by.solbeg.response.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindWordService {

    public static final String HIDDEN_WORD = "город";

    public List<Response> find(String enteredWord) {
        if (checkWord(enteredWord)) {
            return outputByCharacters(enteredWord);
        }
        return Collections.emptyList();
    }

    List<Response> outputByCharacters(String enteredWord) {

        List<Response> result = new ArrayList<>();

        char[] hidden = HIDDEN_WORD.toCharArray();
        char[] entered = enteredWord.toCharArray();

        for (int i = 0; i < hidden.length; i++) {
            if (i < entered.length) {
                if (hidden[i] == entered[i]) {
                    result.add(new Response(i, Color.GREEN));
                    hidden[i] = '*';
                } else if (new String(hidden).contains(String.valueOf(entered[i]))) {
                    int j = new String(hidden).indexOf(entered[i]);
                    if (hidden[j] == entered[j]) {
                        result.add(new Response(i, Color.GRAY));
                        continue;
                    }
                    result.add(new Response(i, Color.YELLOW));
                    hidden[j] = '*';
                } else {
                    result.add(new Response(i, Color.GRAY));
                }
            }
        }
        return result;
    }

    private boolean checkWord(String word) {

        if (word == null || word.trim().isEmpty()) {
            throw new ValidateException("Значение не может быть null или пустым!");
        }

        if (word.length() != 5) {
            throw new ValidateException("Длина слова должна равняться 5!");
        }

        for (char c : word.toCharArray()) {

            if (!Character.isLetter(c)) {
                throw new ValidateException("Слово должно состоять из букв!");
            }
        }
        return true;
    }
}
