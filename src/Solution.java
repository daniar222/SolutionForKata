import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Scanner;
import static java.lang.System.in;

public class Solution {
    static boolean Flag = false;

    enum RomanNumeral {
        M(1000),CM(900),D(500),CD(400),C(100),XC(90),L(50),
        XL(40), X(10),IX(9),V(5),IV(4),I(1);

        private final int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public static int romanToArabic(@NotNull String input) {

        int result = 0;

        List<RomanNumeral> romanNumerals = List.of(RomanNumeral.values());

        String romanNumeral = input.toUpperCase();
        int i = 0;
        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException("т.к. неверный операнд : " + input);
        }

        return result;
    }

    public static @NotNull String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " is not in range (0,4000]");
        }


        List<RomanNumeral> romanNumerals = List.of(RomanNumeral.values());

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }

    public static int Result(int a, int b, @NotNull String operator){
        if(operator.equals("+"))return a+b;
        if(operator.equals("-"))return a-b;
        if(operator.equals("*"))return a*b;
        if(operator.equals("/"))return a/b;
        return 0;
    }

    public static int isNumeric(@NotNull String str1) throws Exception {
        try {
            if(str1.matches("-?\\d+(\\.\\d+)?")){
                return Integer.parseInt(str1);
            } else{
                Flag = true;
                return romanToArabic(str1);
            }
        } catch (NumberFormatException e){
            throw new Exception("т.к. Введено не целое числое");
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(in);
        String input_line = scn.nextLine();
        String [] input = input_line.split(" ");

        if(!(input_line.contains("*") || input_line.contains("+")
                || input_line.contains("-") || input_line.contains("/")) ){
            throw new Exception("т.к. строка не является математической операцией");
        }

        if((input.length != 3 ) ){
            throw new Exception("т.к. формат математической операции не " +
                    "удовлетворяет заданию - два операнда и один оператор(+, -, /, *)");
        }

        String First_member = input[0];
        String Operator = input[1];
        String Second_member = input[2];


        if(!(Operator.equals("+") || Operator.equals("/")
                || Operator.equals("*") || Operator.equals("-")) ){
            throw new Exception("т.к. неверный формат задания математической операции," +
                    "используйте два операнда раздереленных допустимым оператором : (+, -, /, *)");
        }


        if(First_member.matches("-?\\d+(\\.\\d+)?") ^ Second_member.matches("-?\\d+(\\.\\d+)?")){
            throw new Exception("т.к. используются одновременно разные системы счисления");
        }
        int First_member_uno = isNumeric(First_member);
        int Second_member_uno = isNumeric(Second_member);

        if(!(First_member_uno <= 10 && First_member_uno >= 1 && Second_member_uno >= 1
                && Second_member_uno <= 10)){
            throw new Exception("т.к. входное число не входит в допустимый диапазон");
        }


        String Result;

        if(Flag && (Result(First_member_uno,Second_member_uno,Operator) < 1) ){
            throw new Exception("т.к. в римской системе нет отрицательных чисел и ноля");
        }

        if(Flag)Result = arabicToRoman(Result(First_member_uno,Second_member_uno,Operator));
        else Result = String.valueOf(Result(First_member_uno,Second_member_uno,Operator));


        System.out.println(Result);

    }
}
