package com.fbasketball;

import java.io.Serializable;
import java.util.Random;

public class RandomParam implements Serializable {
    private Random random = new Random();
    private final String mAllLetters = "abcdefghijklmnopqrstuwxyz";

    public String randomWord () {
        char[] chars = new char[12];
        for (int c = 1; c<13; c++) {
            char letter = mAllLetters.charAt(random.nextInt(mAllLetters.length()));
            chars[c-1] = letter;
        }
        String randomWord= new String (chars);
        return randomWord;
    };

    public int randomNumber () {
        int rNumber = random.nextInt(99);
        return rNumber;
    };

    public int randomScore () {
        int rScore = 60 + random.nextInt(40);
        return rScore;
    };


}
