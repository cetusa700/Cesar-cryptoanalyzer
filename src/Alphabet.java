/*
 *  в методе класса "вручную" можно добавить или убрать любые символы, НО!! надо вводить в отсортированном виде (просто
 * по порядку, который в Юникоде существует. Остальной код менять не надо, все автоматически откорректируется, в т.ч.
 * текстовая информация в сообщениях, выводимых в консоль
 **/
import java.util.ArrayList;
import java.util.List;

class Alphabet {

    static final List<Character> ALPHABET = new ArrayList<>();

    static ArrayList<Character> fillCharsListFromAlphabet() {
        String charsString = " " + "!\"(),-.:?" + "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" + "абвгдежзийклмнопрстуфхцчшщъыьэюя";

        for (char c : charsString.toCharArray()) {
            ALPHABET.add(c);
        }
        return (ArrayList<Character>) ALPHABET;
    }
}

