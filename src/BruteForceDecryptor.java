/*
 * Для расшифровки использовал методы с regex. Как мне кажется, самый простой и понятный способ. Создал несколько regex,
 * попробовал разные, статистически наиболее встречающиеся в русском языке, остальные пока закомментировал, удалять не
 * стал. Метод работает отлично. При нахождении сочетания тех предлогов, которые есть в regex дает при тестировании
 * даже небольших текстов 100%-й результат. Специально искал ключи, которые могут вывести аналогичные предлоги и при
 * этом выдать неправильный результат, но пока ничего такого не нашел. То есть, совпадений при переборе ключей, которые
 * случайно бы находили проверяемые сочетания, но привели бы к неверной расшифровке - не нашел. Статистически это
 * невозможно,   вроде. Но, в принципе, можно на всякий случай сделать и так: 1) проверку при нахождении совпадений путем
 * вывода в консоли первой строки текста, если все ОК, то дать команду на запись в файл; 2) Если все-таки вышло
 * совпадение с regex случайное, но ключ неправильный или совпадений не найдено, то дать команду, например, на смену
 * regex по желаемому выбору и повторить поиск ключа. Хотя, повторюсь, у меня такого не было с различными текстами.
 * На 100% работает и по предлогу "при". Одно из самых часто встречаемых слов.
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BruteForceDecryptor {

    int key;

    void bruteForce() {

        ArrayList<Character> charsFromFile;
        String sourceFileName = null;
        String destDecryptFile = null;
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
                destDecryptFile = console.nextLine();
                if (!FileExtensionValidation.validateFileExtension(destDecryptFile)) {
                    System.out.println("You have entered incorrect file name. \n" +
                            "Please try again.");
                } else {
                    correctFileName = true;
                }
            }
            if (sourceFileName.equals(destDecryptFile)) {
                System.out.println("You have entered decrypted file name the same with encrypted file! \n" +
                        " Please correct it and repeat entering decrypted file name");
            } else
                sameNames = false;
        }

        try (FileReader fileReader = new FileReader(sourceFileName);
             FileWriter fileWriter = new FileWriter(destDecryptFile);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            int ch;
            charsFromFile = new ArrayList<>();
            while ((ch = bufferedReader.read()) != -1) {
                charsFromFile.add((char) ch);
            }

            String regex1 = "\sна\s";
            String regex2 = "\sв\s";

            Pattern pattern1 = Pattern.compile(regex1);
            Pattern pattern2 = Pattern.compile(regex2);

            for (key = 1; key < charsFromFile.size() - 1; key++) {
                CesarDecryptor.decryptToCharList(Alphabet.fillCharsListFromAlphabet(), charsFromFile, key);
                StringBuilder decryptedText = new StringBuilder();
                for (Character character : charsFromFile) {
                    decryptedText.append(character);
                }

                Matcher matcher2 = pattern1.matcher(decryptedText);
                Matcher matcher3 = pattern2.matcher(decryptedText);

                if (matcher2.find() && matcher3.find()) {
                    System.out.println("\n The file has been decrypted, \n" +
                            "check it please. \n");
                    break;
                }
            }

            for (Character character : charsFromFile) {
                bufferedWriter.write(character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}