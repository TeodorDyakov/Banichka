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
		for (int i = 0; i < vector.length; i++) {
			if (min > vector[i]) {
				min = vector[i];
			}
		}
		return min;
	}
}
