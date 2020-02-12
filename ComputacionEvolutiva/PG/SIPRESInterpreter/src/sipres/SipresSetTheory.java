/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sipres;

import static sipres.language.SipresConstants.NATURAL;
import static sipres.language.SipresConstants.SUCCESSOR;
import sipres.language.Term;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Camiku
 */
public class SipresSetTheory {

    private static String element;
    private static int ran, i;
    private static Term parent, child, leaf;
    private static StringBuilder sb = new StringBuilder();
    private static final DecimalFormatSymbols separatorSymbol = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
    private static final DecimalFormat decimalFormat1 = new DecimalFormat("0.0", separatorSymbol);
    private static final DecimalFormat decimalFormat2 = new DecimalFormat("0.00", separatorSymbol);
    private static final DecimalFormat decimalFormat3 = new DecimalFormat("0.000", separatorSymbol);

    public static String getRandomElementSet(Set<String> set) {
        if (set.isEmpty()) {
            System.err.println("Hash set is empty");
            return null;
        } else {
            element = null;
            i = 0;
            Random random = new Random();
            ran = random.nextInt(set.size());
            for (Iterator<String> it = set.iterator(); it.hasNext();) {
                element = it.next();
                if (i == ran) {
                    return element;
                } else {
                    i++;
                }
            }
            return element;
        }
    }

    /**
     * n >= 3
     *
     * @param n
     * @return
     */
    public static boolean isPrime(long n) {
        long sqrt = (long) Math.sqrt(n);
        for (long counter = 3; counter <= sqrt; counter += 2) {
            if (n % counter == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSubSet(Set<String> A, Set<String> B) {
        for (Iterator<String> iterator = A.iterator(); iterator.hasNext();) {
            if (!B.contains(iterator.next())) {
                return false;
            }
        }
        return true;
    }

    public static Term generateTreeNumber(int n) {
        Term node = new Term();
        return generateTreeNumber(n, node);
    }

    public static Term generateTreeNumber(int n, Term root) {
        if (n == 0) {
            root.setType(NATURAL);
            root.setValue("0");
        } else {
            root.setType(SUCCESSOR);
            root.setValue("s");
            child = new Term("0", NATURAL, null);
            for (i = 1; i < n; i++) {
                parent = new Term("s", SUCCESSOR, null);
                parent.addChild(child);
                child = parent;
            }
            root.addChild(child);
        }
        return root;
    }

    public static String printArrayInt(int[] array) {
        sb.delete(0, sb.length());
        sb.append("[");
        for (i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(" ");
            } else {
                sb.append("]");
            }
        }
        return sb.toString();
    }

    public static String printArrayDouble(double[] array) {
        sb.delete(0, sb.length());
        sb.append("[");
        for (i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i < array.length - 1) {
                sb.append(" ");
            } else {
                sb.append("]");
            }
        }
        return sb.toString().replace('.', ',');
    }

    public static int sumArray(int[] array) {
        int S = 0;
        for (i = 0; i < array.length; i++) {
            S += array[i];
        }
        return S;
    }

    public static long sumArray(long[] array) {
        long S = 0;
        for (i = 0; i < array.length; i++) {
            S += array[i];
        }
        return S;
    }

    public static double sumArray(double[] array) {
        double S = 0;
        for (double value : array) {
            S += value;
        }
        return S;
    }

    public static double sumArray(ArrayList<Double> array) {
        double S = 0;
        for (double value : array) {
            S += value;
        }
        return S;
    }

    public static double getAverange(int[] array) {
        return 1.0 * sumArray(array) / array.length;
    }

    public static double getAverange(long[] array) {
        return 1.0 * sumArray(array) / array.length;
    }

    public static double getAverange(double[] array) {
        return 1.0 * sumArray(array) / array.length;
    }

    public static double getAverange(ArrayList<Double> array) {
        return sumArray(array) / array.size();
    }

    public static double[] getPercents(int[] array, int sum) {
        double[] result = new double[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = 100.0 * array[i] / sum;
        }
        return result;
    }

    public static String truncateNumber1(double x) {
        if (Double.isNaN(x)) {
            return "NAN";
        } else {
            return decimalFormat1.format(x);
        }
    }

    public static String truncateNumber2(double x) {
        if (Double.isNaN(x)) {
            return "NAN";
        } else {
            return decimalFormat2.format(x);
        }
    }

    public static String truncateNumber3(double x) {
        if (Double.isNaN(x)) {
            return "NAN";
        } else {
            return decimalFormat3.format(x);
        }
    }

    public static double[] toArray(ArrayList<Double> arrayList) {
        double[] array = new double[arrayList.size()];
        for (i = 0; i < array.length; i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }

}
