/*
 * Для расшифровки использовал методы с regex. Как мне кажется, самый простой и понятный способ. Создал несколько regex,
 * попробовал разные, статистически наиболее встречающиеся в русском языке. Метод работает отлично.
 * При нахождении сочетания тех предлогов, которые есть в regex дает при тестировании
 * даже небольших текстов 100%-й результат. Специально искал ключи, которые могут вывести аналогичные предлоги и при
 * этом выдать неправильный результат, но пока ничего такого не нашел. То есть, совпадений при переборе ключей, которые
 * случайно бы находили проверяемые сочетания, но привели бы к неверной расшифровке - не нашел. Статистически это
 * невозможно,  вроде. Но, в принципе, можно на всякий случай сделать и так: 1) проверку при нахождении совпадений путем
 * вывода в консоли первой строки текста, если все ОК, то дать команду на запись в файл; 2) Если все-таки вышло
 * совпадение с regex случайное, но ключ неправильный или совпадений не найдено, то дать команду, например, на смену
 * regex по желаемому выбору и повторить поиск ключа. Хотя, повторюсь, у меня такого не было с различными текстами.
 * На 100% работает и по предлогу "при". Одно из самых часто встречаемых слов.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BruteForceDecryptor {

    int key;

    void bruteForce() {

        ArrayList<Character> charsFromFile;
        ArrayList<String > fileNames;
        Scanner console; // = new Scanner(System.in);
        System.out.println("Please enter the path and filename you want to decrypt \n" +
                " and then filename you want write result");

        fileNames = FileNameValidation.setFileNames();
        String sourceFileName = fileNames.get(0);
        String destFileName = fileNames.get(1);

        try (FileReader fileReader = new FileReader(sourceFileName);
             FileWriter fileWriter = new FileWriter(destFileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            int ch;
            charsFromFile = new ArrayList<>();
            while ((ch = bufferedReader.read()) != -1) {
                charsFromFile.add((char) ch);
            }

            String regex1 = "\sи\s";
            String regex2 = "\sс\s";
            String regex3 = "\sв\s";
            String regex4 = "\sна\s";
            String regex5 = "\sо\s";

            Pattern pattern1 = Pattern.compile(regex1);
            Pattern pattern2 = Pattern.compile(regex2);
            Pattern pattern3 = Pattern.compile(regex3);
            Pattern pattern4 = Pattern.compile(regex4);
            Pattern pattern5 = Pattern.compile(regex5);

            for (key = 1; key < charsFromFile.size() - 1; key++) {
                CesarDecryptor.decryptToCharList(Alphabet.fillCharsListFromAlphabet(), charsFromFile, key);
                StringBuilder decryptedText = new StringBuilder();
                for (Character character : charsFromFile) {
                    decryptedText.append(character);
                }

                Matcher matcher1 = pattern1.matcher(decryptedText);
                Matcher matcher2 = pattern2.matcher(decryptedText);
                Matcher matcher3 = pattern3.matcher(decryptedText);
                Matcher matcher4 = pattern4.matcher(decryptedText);
                Matcher matcher5 = pattern5.matcher(decryptedText);

                if ((matcher1.find() && matcher2.find()) ||
                    (matcher2.find() && matcher3.find()) ||
                    (matcher3.find() && matcher4.find()) ||
                    (matcher4.find() && matcher5.find())) {
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