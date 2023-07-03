import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author telegram - @sxdxvsxdxv / Шевцов Дмитрий Юрьевич
 * hh.ru - https://spb.hh.ru/resume/bf7cba5fff0c16f5d50039ed1f59646d6a6556
 */
public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Точка входа в программу
     */
    public static void main(String[] args) {
        String fileName = "input.txt";
        searchReadInputFile(fileName);
    }

    /**
     * Поиск input-файла и первичное чтение информации из него.
     * @param fileName название файла, указанное в методе Main
     */
    public static void searchReadInputFile(String fileName){
        File file = new File("src/" + fileName);
        String filePath = file.getAbsolutePath();
        File inputFile = new File(filePath);
        try {
            BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
            logger.info("Файл найден");

            String line;
            ArrayList<String> lines = new ArrayList<String>();
            while ((line = inputReader.readLine()) != null) {
                lines.add(line);
            }

            intermediateDefinition(lines);
        } catch (FileNotFoundException e) {
            logger.info("Файл не найден:\n" + e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Промежуточный этап, в котором происходит получение информации первого списка и второго.
     * Делается это для удобства сравнения элементов в дальнейшем.
     * @param lines
     */
    public static void intermediateDefinition(ArrayList<String> lines){
        String[] firstArray = null;
        String[] secondArray = null;
        for (int i = 0; i < lines.size(); i++) {
            try {
                int countOfElements = Integer.parseInt(lines.get(i));
                if (i==0){
                    firstArray = new String[countOfElements];
                    for (int j = 0; j < countOfElements; j++) {
                        firstArray[j] = lines.get(i+1+j);
                    }
                } else {
                    secondArray = new String[countOfElements];
                    for (int j = 0; j < countOfElements; j++) {
                        secondArray[j] = lines.get(i+1+j);
                    }
                }
            } catch (NumberFormatException e){
                logger.info("Идет парсирование в String\n" + e);
            } catch (Exception e){
                logger.info("Проверье входящие данные: количество элементов " +
                        "должно соответстовать номеру перед ними:\n" + e);
            }
        }

        comparison(firstArray, secondArray);
    }

    /**
     * Метод сравнения содержимого массивов для сопоставления позиция между собой.
     * @param firstArray получаем первый массив данных.
     * @param secondArray получаем второй массив данных.
     */
    public static void comparison(String[] firstArray, String[] secondArray){
        String[] output = new String[firstArray.length];
        for (int i = 0; i < output.length; i++) {
            output[i] = firstArray[i] + ":?";
        }

        // Проверка на количество строк данных в исходных файлах
        if (firstArray.length == 1 && secondArray.length == 1){
            output[0] = firstArray[0] + ":" + secondArray[0];
        } else {
            // Проход по элементам первого массива
            for (int i = 0; i < firstArray.length; i++) {
                String[] partsOfElementFirst = firstArray[i].split(" ");

                // Проход по частям элементов первого массива
                for (String s : partsOfElementFirst) {

                    // Проход по элементам второго массива
                    for (String value : secondArray) {
                        String[] partsOfElementSecond = value.split(" ");

                        // Проход по частям элементов второго массива
                        for (String item : partsOfElementSecond) {

                            // Заполнение выходными данными массива для файла output.txt
                            if (s.equals(item)) {
                                output[i] = firstArray[i] + ":" + value;
                                break;
                            }
                        }
                    }
                }
            }
        }
        writeOutputFile(output);
    }

    /**
     * Заполнение файла output.txt
     * @param output получение данных из метода comparison
     */
    public static void writeOutputFile(String[] output){
        try (FileWriter writer = new FileWriter("src/output.txt", false)){
            for (int i = 0; i < output.length; i++) {
                writer.append(output[i] + "\n");
                writer.flush();
            }
        }
        catch(IOException e){
            logger.info("Ошибка записи в файл:\n" + e);
            e.printStackTrace();
        }
    }
}
