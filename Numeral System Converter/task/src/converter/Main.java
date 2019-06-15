package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int sourceRadix;
        String sourceNumber;
        int targetRadix;

        if (scanner.hasNextInt()) {
            sourceRadix = scanner.nextInt();
            if (scanner.hasNext()) {
                sourceNumber = scanner.next();
                if (scanner.hasNextInt()) {
                    targetRadix = scanner.nextInt();
                    if (hasError(sourceRadix, targetRadix)) {
                        System.out.println("error");
                    } else {
                        System.out.println(convert(sourceNumber, sourceRadix, targetRadix));
                    }
                } else {
                    System.out.println("error");
                }
            } else {
                System.out.println("error");
            }
        } else {
            System.out.println("error");
        }
    }

    private static String convert(String sourceNumber, int sourceRadix, int targetRadix){
        String convertedNumber;

        if (sourceNumber.contains(".")) {
            convertedNumber = processAsDouble(sourceNumber, sourceRadix, targetRadix);
        } else {
            convertedNumber = processAsInt(sourceNumber, sourceRadix, targetRadix);
        }

        return convertedNumber;
    }

    private static String processAsInt(String sourceNumber, int sourceRadix, int targetRadix) {
        String convertedNumber;
        long numberConvertedToDecimal = convertToDecimal(sourceNumber, sourceRadix);
        convertedNumber = convertToTargetRadix(numberConvertedToDecimal, targetRadix);

        return convertedNumber;
    }

    private static String processAsDouble(String sourceNumber, int sourceRadix, int targetRadix) {
        String convertedNumber;
        String integerPart = sourceNumber.substring(0, sourceNumber.indexOf("."));
        String fractionalPart = sourceNumber.substring(sourceNumber.indexOf("."));
        long integerPartConvertedToDecimal = convertToDecimal(integerPart, sourceRadix);
        double fractionalPartConvertedToDouble = convertFractionalPartToDouble(fractionalPart, sourceRadix);
        String part1 = convertToTargetRadix(integerPartConvertedToDecimal, targetRadix);
        String part2 = convertFractionalPartToTargetRadix(fractionalPartConvertedToDouble, targetRadix);

        if (targetRadix == 1) {
            convertedNumber = part1;
        } else {
            convertedNumber = part1 + "." + part2;
        }

        return convertedNumber;
    }

    private static long convertToDecimal(String number, int radix) {
        long convertedNumber;

        if (radix == 1) {
            convertedNumber = convertFromRadixOne(number);
        } else {
            convertedNumber = Long.parseLong(number, radix);
        }

        return convertedNumber;
    }

    private static double convertFractionalPartToDouble(String str, int radix) {
        double fractionalPart = 0.0;
        for (int i = 1; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                fractionalPart += Character.getNumericValue(str.charAt(i)) / Math.pow(radix, i);
            } else {
                fractionalPart += Character.getNumericValue(str.charAt(i)) / Math.pow(radix, i);
            }
        }
        return fractionalPart;
    }

    private static long convertFromRadixOne(String sourceNumber) {

        return sourceNumber.length();
    }

    private static String convertToTargetRadix(long number, int targetRadix) {
        String convertedNumber;

        if (targetRadix == 1) {
            convertedNumber = convertToRadixOne(number);
        } else {
            convertedNumber = Long.toString(number, targetRadix);
        }

        return convertedNumber;
    }

    private static String convertToRadixOne(long sourceNumber) {
        String convertedNumber = "";
        for (int i = 0; i < sourceNumber; i++) {
            convertedNumber += 1;
        }
        return convertedNumber;
    }

    private static String convertFractionalPartToTargetRadix(double number, int radix) {
        String convertedNumber = "";

        if (radix > 1) {
            for (int i = 0; i < 5; i++) {
                number = number * radix;
                int iPart = (int) number;
                convertedNumber += Long.toString(iPart, radix);
                number -= iPart;
            }
        }

        return convertedNumber;
    }

    private static boolean hasError(int sourceRadix, int targetRadix) {
        boolean error = false;

        if (sourceRadix > 36 || sourceRadix < 1) {
                error = true;
            }

        if (targetRadix > 36 || targetRadix < 1) {
                error = true;
            }

        return error;
    }
}
