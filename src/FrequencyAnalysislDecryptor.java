 /* Метод частотного анализа (статистический метод). Исходим из того, что самый распространенный символ в русском (и не
 только) языке - пробел. Превышает количество других на 20 - 35% Суть: из сформированной коллекции символов шифрованногго
 текста вычисляем максимально часто встречающийся элемент. Получаем индексы этого элемента и индекса "пробела" из
 справочника ALPHABET. Вычисляем ключ, дешифруем, используя полученный ключ. Даже в небольших контрольных текстах (до
 одного абзаца и по стилям - судебное решение, технический текст, художественная литература) дает прекрасный результат.
        REM. Проскольку в русском языке следующие по частоте использования символы - 'о', 'и', 'е', то стоит включить
    несложный код со следующей логикой: создать список из 4х - 5ти самых частых символов, проводить расчет ключа по
    циклу. Вывести на экран после первой дешифровки первую строку текста, если пользователя устраивает, рашифровка
    корректная, пишем в файл. Если нет, просто программа меняет контрольный символ, например, с пробела на букву "о". И
    так несколько шагов. Но, судя по литературе в редких случаях требуется не более 2-ух - 3-ех таких шагов. Чаще по
    пробелу все с первого раза.
 */

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FrequencyAnalysislDecryptor {
    int key;

    void frequencyAnalysisDecryption() {
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
            Alphabet.fillCharsListFromAlphabet();

            /*
             * получаем символ, который чаще всего встречается в зашифрованном тексте при помощи метода (ниже в классе)
             */
            char maxFrequentChar = maxFrequentChar (charsFromFile);

            /*
             * получаем индекс в ALPHABET часто встречающегося символа в зашифрованном тексте
             */
            int indexOfMaxFrequentChar = Alphabet.ALPHABET.indexOf(maxFrequentChar);

            /*
            * получаем индекс статисттически самого частого элемента в русском языке из ALPHABET (это пробел) как
            * конторольного варианта для вычисления первого ключа, вычисляем ключ
            */
            int indexOfControlChar = Alphabet.ALPHABET.indexOf(' ');
            key = indexOfMaxFrequentChar - indexOfControlChar;

            /*
            * дешифруем
            * */
            CesarDecryptor.decryptToCharList(Alphabet.fillCharsListFromAlphabet(), charsFromFile, key);
            System.out.println("\n The file has been decrypted, \n" +
                    "check it please. \n");

            /*
             * пишем в файл расшированный текст
             */
            for (Character character : charsFromFile) {
                bufferedWriter.write(character);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * Метод для подсчета количества совпадающих символов в зашифрованном тексте, записи в HashMap<>, сортировки по
     * значению (количеству повторений) и возврата символа, который чаще всего встречается в тексте
     **/
    static char maxFrequentChar (ArrayList<Character> charsFromFile) {

        Map<Character, Integer> countCharsMap = new HashMap<>();
        for (Character item : charsFromFile) {

            if (countCharsMap.containsKey(item))
                countCharsMap.put(item, countCharsMap.get(item) + 1);
            else
                countCharsMap.put(item, 1);
        }
        /*
         * сортируем по значению -  т.е. по частоте символа в тексте, использовал готовый вариант кода
         */
        LinkedHashMap<Character, Integer> sortedMap = countCharsMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new));

        ArrayList<Character> charsInFileByFrequencyList = new ArrayList<Character>(sortedMap.keySet());
        int lastIndexFrequencyList = charsInFileByFrequencyList.size() - 1;
        char maxFrequentChar = charsInFileByFrequencyList.get(lastIndexFrequencyList);

        return maxFrequentChar;
    }
}
