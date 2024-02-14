package by.solbeg;

import by.solbeg.service.FindWordService;

public class Main {

    public static void main(String[] args) {

        FindWordService service = new FindWordService();
        service.find("порог");
    }
}