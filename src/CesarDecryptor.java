import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

class CesarDecryptor {

    int key;

    void decryption() {
        String sourceFileName = null;
        String destDecryptedFile = null;
        ArrayList<Character> charsFromFile;
        boolean sameNames = true;
        boolean correctFileName = false;

        Scanner console = new Scanner(System.in);
        System.out.println("""
                Please enter the path to the file you want to decrypt,\s
                file extension must be   ".txt"\s
                Please note the names encrypted and decrypted files must not be the same\s""");

        while (sameNames) {
            while (!correctFileName) {
                sourceFileName = console.nextLine();
                if (!FileExtensionValidation.validateFileExtension(sourceFileName)) {
                    System.out.println("You have entered incorrect file name. \n" +
                            "Please try again.");
                } else if (Files.notExists(Path.of(sourceFileName))) {
                    System.out.println("File not found! Please correct the filepath!");
                } else {
                    correctFileName = true;
                }
            }

            correctFileName = false;
            System.out.println("Please enter the path to the decrypted file\n" +
                    "file extension must be   \".txt\" : ");

            while (!correctFileName) {
                destDecryptedFile = console.nextLine();
                if (!FileExtensionValidation.validateFileExtension(destDecryptedFile)) {
                    System.out.println("You have entered incorrect file name. \n" +
                            "Please try again.");
                } else {
                    correctFileName = true;
                }
            }
            if (sourceFileName.equals(destDecryptedFile)) {
                System.out.println("You have entered decrypted file name the same with encrypted file! \n" +
                        " Please correct it and repeat entering decrypted file name");
            } else
                sameNames = false;
        }
        System.out.println("Please enter crypto Key. It must be positive number and \n" +
                "must not be a multiple 74 : ");

        boolean keyIsCorrect = false;
        while (!keyIsCorrect) {
            console = new Scanner(System.in);
            if (console.hasNextInt()) {
                boolean keyIsValid = false;
                while (!keyIsValid) {

                    key = console.nextInt();

                    if (!KeyValueValidation.validateKey(key)) {
                        System.out.println("You have entered incorrect key value. \n"
                                + "Please try again");
                    } else {
                        keyIsValid = true;
                        keyIsCorrect = true;
                    }
                }
            } else {
                System.out.println("You have entered not an integer number. \n" +
                        "Please try again");
            }
        }

        try (FileReader fileReader = new FileReader(sourceFileName);
             FileWriter fileWriter = new FileWriter(destDecryptedFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            int letter;
            charsFromFile = new ArrayList<>();
            while ((letter = bufferedReader.read()) != -1) {
                charsFromFile.add((char) letter);
            }
            CesarDecryptor.decryptToCharList(Alphabet.fillCharsListFromAlphabet(), charsFromFile, key);

            for (Character character : charsFromFile) {
                bufferedWriter.write(character);
            }
            System.out.println("\n The file has been decrypted, \n" +
                    "check it please. \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * метод аналогичен методу из класса CesarEncryptor, только работает наоборот (дешифрует).
     */
    static void decryptToCharList(ArrayList<Character> charsFromAlphabet,
                                  ArrayList<Character> charsListFromFile,
                                  int key) {
        int size = charsFromAlphabet.size();
        int fileCharsSize = charsListFromFile.size();
        int j = 0;

        for (int i = 0; i < fileCharsSize; i++) {
            j = Collections.binarySearch(charsFromAlphabet, charsListFromFile.get(i));
            if (j >= 0) {
                if ((j - key) < 0) {
                    charsListFromFile.set(i, charsFromAlphabet.get(j - key + size));
                } else {
                    charsListFromFile.set(i, charsFromAlphabet.get(j - key));
                }
            }
        }
    }
}