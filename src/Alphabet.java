import java.util.ArrayList;
import java.util.List;

class Alphabet {

    static final List<Character> ALPHABET = new ArrayList<>();

    static ArrayList<Character> fillCharsListFromAlphabet() {
        String charsString = " " + "!\"(),-.:?АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" + "абвгдежзийклмнопрстуфхцчшщъыьэюя";

        for (char c : charsString.toCharArray()) {
            ALPHABET.add(c);
        }
        return (ArrayList<Character>) ALPHABET;
    }
}

