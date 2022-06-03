/*
 * Метод validateKey в отдельном классе создан для того, чтобы при изменении требований по составу алфавита можно было бы просто
 * изменить charsString в классе Alphabet (он вводится в отсортированном порядке вручную), при этом больше в коде нигде
 * изменений для проверки корректности ключа делать  не надо. Вообще сюда весь код по проверке валидности ключа пернести
 * надо.
 * */

class KeyValueValidation {
static int maxKeyValue = Alphabet.fillCharsListFromAlphabet().size() - 1;
    static boolean validateKey(int key) {
        return key > 0 && (key % (maxKeyValue + 1)) != 0;
    }
}
