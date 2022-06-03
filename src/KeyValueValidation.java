/*
 * Метод validateKey в отдельном классе создан для того, чтобы при изменении требований по составу алфавита можно было бы просто
 * изменить charsString в классе Alphabet (он вводится в отсортированном порядке вручную), при этом больше в коде нигде
 * изменений для проверки корректности ключа делать  не надо. Вообще сюда весь код по проверке валидности ключа пернести
 * надо.
 * */

import java.util.Scanner;

class KeyValueValidation {

    static int validateKey() {
        int key = 0;
        Scanner console;
        System.out.println("Please enter crypto Key. It must be positive number and \n" +
                "must not be a multiple 74 : ");

        boolean keyIsCorrect = false;
        while (!keyIsCorrect) {
            console = new Scanner(System.in);
            if (console.hasNextInt()) {
                boolean keyIsValid = false;
                while (!keyIsValid) {

                    key = console.nextInt();

                    if (!KeyValueValidation.validateKeyValue(key)) {
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
        return key;
    }

    static int maxKeyValue = Alphabet.fillCharsListFromAlphabet().size() - 1;

    static boolean validateKeyValue(int key) {
        return key > 0 && (key % (maxKeyValue + 1)) != 0;
    }
}
