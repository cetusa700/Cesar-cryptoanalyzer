/*
 *    1. Класс содержит код по проверке наличия и валидности файлов для шифрования и дешифрования?. В том числе проверку
 *   на то, чтобы имена файлов случайно не совпадали. Пока не стал заморачиваться  на создании методов в отдельном классе
 *   (кроме проверки формата файла - .txt и корректности введенного ключа). В принципе,  можно его создать, вызывать его
 *   в классах,  шифрующих и дешифрующих (они все используют код, почти одинаковый), он будет возвращать  объект с
 *   переменными типа String - пути и имена файлов для шифрования и дешифрования. Так код покороче и поприятнее будет.
 *   То же касается и кода по созданию и проверки ключа. Надо отдельно все вынести в класс KeyValidation. Просто пока
 *   сделал что-то вроде MVP.
 *   Но пока решил так, чтобы побыстрее (от этого он не менее поняте, как мне кажется, просто более громоздкий). Как сказал
 *   Андрей З. -  в небольших проектах, скорее всего работоспособность на первом месте. Это на больших подход другой ))
 *    2. Поскольку обработка Exceptions "дорогая вещь", то, чтобы они не возникали, в простых случаях решил провести
 *   дополнительную проверку (например, форматы чисел, валидацию расширения файла, совпадение имен файлов и т.п.) в
 *   условиях if(). По-моему, это несколько проще, там, где точно можно предугадать эти исключения.
 *    3. Комментарии нигде не ставил, я надеюсь, что и без них в коде все понятно, старался писать максимально правильно
 *  с точки зрения именований классов, полей и методов, ну и логики программы.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;

class CesarEncryptor {

    int key;

    void encryption() {

        ArrayList<Character> charsFromFile;
        String sourceFileName = null;
        String destEncryptedFile = null;
        boolean sameNames = true;
        boolean correctFileName = false;

        Scanner console = new Scanner(System.in);
        System.out.println("""
                Please enter the path to the file you want to encrypt,\s
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
            System.out.println("Please enter the path to the encrypted file\n" +
                    "file extension must be   \".txt\" : ");

            while (!correctFileName) {
                destEncryptedFile = console.nextLine();
                if (!FileExtensionValidation.validateFileExtension(destEncryptedFile)) {
                    System.out.println("You have entered incorrect file name. \n" +
                            "Please try again.");
                } else {
                    correctFileName = true;
                }
            }

            if (destEncryptedFile.equals(sourceFileName)) {
                System.out.println("You have entered encrypted file name the same with source file! \n" +
                        " Please correct it and repeat entering encrypted file name");
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
             FileWriter fileWriter = new FileWriter(destEncryptedFile);
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