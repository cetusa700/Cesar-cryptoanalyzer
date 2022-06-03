/*
 *    1. Класс содержит код по проверке наличия и валидности файлов для шифрования и дешифрования.
 *    2. Поскольку обработка Exceptions "дорогая вещь" то, чтобы они не возникали, в простых случаях решил провести
 *   дополнительную проверку (например, форматы чисел, валидацию расширения файла, совпадение имен файлов и т.п.) в
 *   условиях if(). По-моему, это несколько проще, там, где точно можно предугадать эти исключения.
 *    3. Комментарии особо не ставил, я надеюсь, что и без них в коде все понятно, старался писать максимально правильно
 *  с точки зрения именований классов, полей и методов, ну и логики программы.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

class CesarEncryptor {

    int key;

    void encryption() {

        ArrayList<Character> charsFromFile;
        ArrayList<String > fileNames;
        Scanner console; // = new Scanner(System.in);
        System.out.println("Please enter the path and filename you want to encrypt \n" +
                " and then filename you want to write result");

        fileNames = FileNameValidation.setFileNames();
        String sourceFileName = fileNames.get(0);
        String destFileName = fileNames.get(1);

        /*
         * проверяем валидность ключа (что это число типа int, не равен 0, не кратен размеру ALPHABET, иначе цикл вернет
         * те же символы, не отрицательное число
         */
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
            encryptToCharArrayList(Alphabet.fillCharsListFromAlphabet(), charsFromFile, key);

            for (Character character : charsFromFile) {
                bufferedWriter.write(character);
            }
            System.out.println("\n The file has been encrypted, \n" +
                    " check it please");
            System.out.printf("Do not forget your key! It's number  %d  :) \n \n" , key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * метод меняет (шифрует) char в коллекции char из файла по правилам шифра Цезаря, поиск бинарный, т.к. ALPHABET
    * отсортирован
    */
    private static void encryptToCharArrayList(ArrayList<Character> charsFromAlphabet,
                                               ArrayList<Character> charsListFromFile,
                                               int key) {
        int maxIndex = charsFromAlphabet.size() - 1;
        int j = 0;

        for (int i = 0; i < charsListFromFile.size(); i++) {
            j = Collections.binarySearch(charsFromAlphabet, charsListFromFile.get(i));
            if (j >= 0) {
                if ((j + key) > maxIndex) {
                    charsListFromFile.set(i, charsFromAlphabet.get(j + key - (maxIndex + 1)));
                } else {
                    charsListFromFile.set(i, charsFromAlphabet.get(j + key));
                }
            }
        }
    }
}