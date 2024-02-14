package by.solbeg.service;

import by.solbeg.exception.ValidateException;

public class FindWordService {

    public static final String HIDDEN_WORD = "город";

    public void find(String enteredWord) {

        if (checkWord(enteredWord)) {

            outputByCharacters(enteredWord);
        }
    }

    private void outputByCharacters(String enteredWord) {

        char[] hidden = HIDDEN_WORD.toCharArray();
        char[] entered = enteredWord.toCharArray();

        for (int i = 0; i < hidden.length; i++) {

            if (i < entered.length) {

                if (hidden[i] == entered[i]) {

                    System.out.print("\u001B[32m" + entered[i] + "\u001B[0m");
                    hidden[i] = '*';

                } else if (new String(hidden).contains(String.valueOf(entered[i]))) {
                    int j = new String(hidden).indexOf(entered[i]);
                    if (hidden[j] == entered[j]) {
                        System.out.print("\u001B[90m" + entered[i] + "\u001B[0m");
                        continue;
                    }

                    System.out.print("\u001B[33m" + entered[i] + "\u001B[0m");
                    hidden[j] = '*';

                } else {
                    System.out.print("\u001B[90m" + entered[i] + "\u001B[0m");
                }
            }
        }
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
