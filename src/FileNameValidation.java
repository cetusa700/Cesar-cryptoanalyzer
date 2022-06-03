/*
 * код для проверки требований на предмет валидности имени файла, пути к файлу и расширения файла .txt.
 * */

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileNameValidation {

        static ArrayList<String> setFileNames () {

        ArrayList<String> fileNames = new ArrayList<>();
        String sourceFileName = null;
        String destFileName = null;
        boolean sameNames = true;
        boolean correctFileName = false;
        Scanner console = new Scanner(System.in);

        System.out.println("Files extension must be  .txt \s");

        while (sameNames) {
            while (!correctFileName) {
                sourceFileName = console.nextLine();
                if (!FileNameValidation.validateFileExtension(sourceFileName)) {
                    System.out.println("You have entered incorrect file name. \n" +
                            "Please try again.");
                } else if (Files.notExists(Path.of(sourceFileName))) {
                    System.out.println("File not found! Please correct the filepath!");
                } else {
                    correctFileName = true;
                    fileNames.add(sourceFileName);
                }
            }

            correctFileName = false;
            System.out.println("Now you can enter the next filepath \s");

            while (!correctFileName) {
                destFileName = console.nextLine();
                if (!FileNameValidation.validateFileExtension(destFileName)) {
                    System.out.println("You have entered incorrect file name. \n" +
                            "Please try again.");
                } else {
                    correctFileName = true;
                    fileNames.add(destFileName);
                }
            }
            if (sourceFileName.equals(destFileName)) {
                System.out.println("You have entered decrypted file name the same with encrypted file! \n" +
                        " Please correct it and repeat entering decrypted file name");
            } else
                sameNames = false;
        }
        return  fileNames;
    }

    /*
    * метод на проверку правильности расширения файла
    */
    static boolean validateFileExtension(String fileName) {

        Pattern pattern = Pattern.compile(".+\\.(txt)");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
}