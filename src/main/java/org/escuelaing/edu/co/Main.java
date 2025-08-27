package main.java.org.escuelaing.edu.co;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Reads doubles from a file into MyLinkedList<Double>, then computes mean and
 * sample standard deviation (using n-1 denominator).
 * Usage: java Main path/to/numbers.txt
 * The input file may contain numbers separated by whitespace or new lines.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Main <input-file>");
            System.exit(1);
        }
        String path = args[0];

        MyLinkedList<Double> numbers = new MyLinkedList<>();
        try {
            loadNumbersFromFile(path, numbers);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + path);
            System.exit(2);
        }

        if (numbers.size() == 0) {
            System.out.println("No numbers to process.");
            return;
        }

        double mean = computeMean(numbers);
        double sampleStdDev = computeSampleStdDev(numbers, mean);

        System.out.printf(Locale.US, "Count: %d%n", numbers.size());
        System.out.printf(Locale.US, "Mean: %.6f%n", mean);
        System.out.printf(Locale.US, "Sample Std Dev (n-1): %.6f%n", sampleStdDev);
    }

    private static void loadNumbersFromFile(String path, MyLinkedList<Double> list) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        sc.useLocale(Locale.US); // ensure dot as decimal separator
        while (sc.hasNext()) {
            if (sc.hasNextDouble()) {
                list.add(sc.nextDouble());
            } else {
                // skip token if not parseable as double
                sc.next();
            }
        }
        sc.close();
    }

    private static double computeMean(MyLinkedList<Double> list) {
        final double[] sum = {0.0};
        list.forEach(d -> sum[0] += d);
        return sum[0] / list.size();
    }

    private static double computeSampleStdDev(MyLinkedList<Double> list, double mean) {
        int n = list.size();
        if (n < 2) return 0.0;
        final double[] sumSq = {0.0};
        list.forEach(d -> {
            double diff = d - mean;
            sumSq[0] += diff * diff;
        });
        return Math.sqrt(sumSq[0] / (n - 1));
    }
}