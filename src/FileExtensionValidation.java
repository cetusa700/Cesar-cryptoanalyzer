/*
* код для проверки требований на предмет расширения файла .txt. Вообще сюда весь код на создание и проверку файла и его
* пути перенести надо
* Но в связи с небольшим цейтнотом пока вот так...
* */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileExtensionValidation {

    static boolean validateFileExtension(String fileName) {

        Pattern pattern = Pattern.compile(".+\\.(txt)");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
}