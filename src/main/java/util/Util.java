package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class Util {
	public static CSVParser getCSVparser(File f) throws IOException {
		Reader in = new FileReader(f);
		return CSVParser.parse(in, CSVFormat.RFC4180);
	}

	public static <K, V extends Comparable<V>> K getKeyOfMaxValue(Map<K, V> map) {
		Entry<K, V> maxEntry = null;

		for (Entry<K, V> entry : map.entrySet()) {
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}
		return maxEntry.getKey();
	}

	public static double min(double[] vector) {
		double min = Double.POSITIVE_INFINITY;
		for (double a : vector) {
			min = Math.min(min, a);
		}
		return min;
	}

	public static double sum(double[] arr) {
		double sum = 0.0;
		for (double a : arr) {
			sum += a;
		}
		return sum;
	}

	public static double magnitude(double[] arr) {
		return Math.sqrt(dotProduct(arr, arr));
	}

	public static double dotProduct(double[] arr, double[] arr1) {
		double sum = 0.0;
		for (int i = 0; i < arr.length && i < arr1.length; i++) {
			sum += arr[i] * arr1[i];
		}
		return sum;
	}

	public static double mean(double[] arr) {
		return sum(arr) / arr.length;
	}

	public static double[] addInPlace(double[] dest, double[] source) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] += source[i];
		}
		return dest;
	}

	public static double[] divideInPlace(double[] dest, double div) {
		for (int i = 0; i < dest.length; i++) {
			dest[i] /= div;
		}
		return dest;
	}

}
