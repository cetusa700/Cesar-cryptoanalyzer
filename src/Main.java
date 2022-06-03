/*
 * Комментариев почти нет, только общие в начале двух классов (CesarEncryptor, BruteForceDecryptor и
 * FrequencyAnalysisDecryptor. Проект небольшой,  поэтому код и без них понятен, надеюсь ))
 */

import javax.sound.midi.Soundbank;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean exit = false;

        while (!exit) {
            Scanner console = new Scanner(System.in);

            System.out.println("Please enter desired MODE:\n" +
                    "\" 1 \"  if encrypt a file MODE\n" +
                    "\" 2 \"  if decrypt a file by Cesar MODE\n" +
                    "\" 3 \"  if decrypt by Brute Force MODE\n" +
                    "\" 4 \"  for Statistic Mode (Frequency Analysis)\n" +
                    "\" 5 \"  if you want to Exit from program\n");

            if (console.hasNextInt()) {
                int mode = console.nextInt();
                switch (mode) {
                    case 1 -> {
                        CesarEncryptor cesarEncryptor = new CesarEncryptor();
                        cesarEncryptor.encryption();
                    }
                    case 2 -> {
                        CesarDecryptor cesarDecryptor = new CesarDecryptor();
                        cesarDecryptor.decryption();
                    }
                    case 3 -> {
                        BruteForceDecryptor bruteForce = new BruteForceDecryptor();
                        bruteForce.bruteForce();
                    }

                    case 4 -> {
                        FrequencyAnalysislDecryptor frequencyAnalysislDecryptor = new FrequencyAnalysislDecryptor();
                        frequencyAnalysislDecryptor.frequencyAnalysisDecryption();
                    }

                    case 5 -> {
                        exit = true;
                        console.close();
                    }

                    default -> System.out.println("Please enter correct number to select desired action");
                }
            } else {
                System.out.println("You have entered incorrect number! \n" +
                        "Please enter correct value \n");
            }
        }
    }
}


