import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

class CesarDecryptor {

    int key;

    void decryption() {
        ArrayList<Character> charsFromFile;
        ArrayList<String > fileNames;
        Scanner console; // = new Scanner(System.in);
        System.out.println("Please enter the path and filename you want to decrypt \n" +
                " and then filename you want write result");

        fileNames = FileNameValidation.setFileNames();
        String sourceFileName = fileNames.get(0);
        String destFileName = fileNames.get(1);

        key = KeyValueValidation.validateKey();

        try (FileReader fileReader = new FileReader(sourceFileName);
             FileWriter fileWriter = new FileWriter(destFileName);
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
     * метод аналогичен методу из класса CesarEncryptor, только работает наоборот (шифрует).
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