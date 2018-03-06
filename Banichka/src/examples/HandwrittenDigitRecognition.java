package examples;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import ml.Data;
import ml.Distance;
import ml.GaussianNB;
import ml.KNN;

public class HandwrittenDigitRecognition {
	public static void main(String[] args) throws IOException {
		KNN knn = new KNN();
		File csvData = new File("digits.txt");

		Reader in = new FileReader(csvData);
		CSVParser parser = CSVParser.parse(in, CSVFormat.RFC4180);
		int c = 0;
		List<Data> data = new ArrayList<>();
		for (CSVRecord csvRecord : parser) {
			// System.out.println(csvRecord);
			if (c > 10) {
				double[] values = new double[csvRecord.size() - 1];
				for (int i = 0; i < values.length; i++) {
					values[i] = Double.parseDouble(csvRecord.get(i + 1));
				}
				data.add(new Data(csvRecord.get(0), values));
			}
			c++;

		}

		GaussianNB gnb = new GaussianNB();

		int testSz = 100;

		gnb.train(data.subList(testSz, data.size()));

		int correct = 0;
		int correct1 = 0;
		for (int i = 0; i < testSz; i++) {
			String expected = knn.classify(data.get(i), data.subList(testSz, data.size()),
					Distance.Metric.Euclidean, 1);
			if (data.get(i).category.equals(expected)) {
				correct++;
			}
			String expected1 = gnb.classify(data.get(i));
			if (data.get(i).category.equals(expected1)) {
				correct1++;
			}
		}
		System.out.println(" Accuracy: " + correct / (double) testSz);
		System.out.println(" Accuracy: " + correct1 / (double) testSz);

	}
}