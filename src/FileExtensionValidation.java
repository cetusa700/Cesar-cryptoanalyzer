import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileExtensionValidation {

    static boolean validateFileExtension(String fileName) {

        Pattern pattern = Pattern.compile(".+\\.(txt)");
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }
}