package Calculate;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import static Calculate.RomanNumeral.HashMapFromTextFile;

public class HomeWorkTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();                                     // ввод операции вычисления в одну строку
        expression = expression.replaceAll("\\s+", "").toUpperCase();        // убираем пробелы в строке и приводим к верхнуму регистру
        ValuesAndSign valuesAndSign = new ValuesAndSign();                 // создаем объект класса где определяем знака и создаем массив из двух значений
        char sign = valuesAndSign.getSign(expression);                              // определяем знак
        Map<String, Integer> mapFromFile = HashMapFromTextFile();

        try {
            String a1 = valuesAndSign.getValues(expression)[0]; // например Х или 4 строка
            String b1 = valuesAndSign.getValues(expression)[1]; // например i или 6 строка

            if (isNumeric(a1) && isNumeric(b1)) {
                int a = Integer.parseInt(a1);
                int b = Integer.parseInt(b1);
                if (a <= 10 && b <=10){
                int result = 0;
                    switch (sign) {
                        case '+' -> result = a + b;
                        case '-' -> result = a - b;
                        case '*' -> result = a * b;
                        case '/' -> result = a / b;
                    }
                    System.out.println(result);}
                else {
                    System.out.println("Числа a и b должны быть не больше 10");
                }

            } else if ((!isNumeric(a1) && !isNumeric(b1))) {
                int a = mapFromFile.get(a1);
                int b = mapFromFile.get(b1);

                if (a <= 10 && b <=10) {
                    int result = 0;
                    switch (sign) {
                        case '+' -> result = a + b;
                        case '-' -> result = a - b;
                        case '*' -> result = a * b;
                        case '/' -> result = a / b;
                    }
                    if (result > 0) {
                        String Key = "";
                        for (String k : mapFromFile.keySet()) {
                            if (mapFromFile.get(k).equals(result)) {
                                Key = k;
                                break;
                            }
                        }
                        System.out.println(Key);
                    } else {
                        System.out.println("Римские числа не могут быть меньше 1");
                    }
                } else {
                    System.out.println("Числа a и b должны быть не больше 10");
                }

            } else {
                System.out.println("используются одновременно разные системы счисления");
            }
        }catch (Exception e){
            System.out.println("строка не является математической операцией");
        }
    }

    public static boolean isNumeric(String string) {
        int intValue;

        if(string == null || string.equals("")) {
            return false;
        }
        try {
            intValue = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
}

class ValuesAndSign {

    String[] getValues(String expression) {
        String[] s1 = new String[0];

        if (expression.contains("+")) {
            s1 = expression.split("\\+");
        } else if (expression.contains("-")) {
            s1 = expression.split("\\-");
        } else if (expression.contains("*")) {
            s1 = expression.split("\\*");
        } else if (expression.contains("/")) {
            s1 = expression.split("\\/");
        }
        return s1;
    }


    char getSign(String expression) {
        char sign = 0;

        if (expression.contains("+")) {
            sign = '+';
        } else if (expression.contains("-")) {
            sign = '-';
        } else if (expression.contains("*")) {
            sign = '*';
        } else if (expression.contains("/")) {
            sign = '/';
        }
        return sign;
    }
}

class RomanNumeral{

    public static Map<String, Integer> HashMapFromTextFile() {

        Map<String, Integer> map = new HashMap<>();
        BufferedReader br = null;

        try {
            // create file object
            File file = new File("RomanArabic.txt");
            // create BufferedReader object from the File
            br = new BufferedReader(new FileReader(file));

            String line;
            // read file line by line
            while ((line = br.readLine()) != null) {
                // split the line by :
                String[] parts = line.split(":");
                // first part is name, second is number
                String name = parts[0].trim();
                Integer number = Integer.valueOf(parts[1].trim());

                // put name, number in HashMap if they are
                // not empty
                if (!name.equals(""))
                    map.put(name, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            // Always close the BufferedReader
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                }
            }
        }
        return map;
    }
}
