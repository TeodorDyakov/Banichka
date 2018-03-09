package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import ml.Data;
import ml.Distance;
import ml.GaussianNB;
import ml.KNN;
import ml.NearestCentroid;
import util.Util;

public class IrisClassification {

	public static void main(String[] args) throws IOException {

		List<Data> data = readData(Util.getCSVparser(new File("Iris.csv")));
		Collections.shuffle(data, new Random(new Random().nextInt()));

		GaussianNB gnb = new GaussianNB();
		KNN knn = new KNN();
		NearestCentroid nc = new NearestCentroid();

		int testSz = data.size() / 2, correct = 0, correct1 = 0, correct2 = 0;

		gnb.train(data.subList(testSz, data.size()));
		nc.train(data.subList(testSz, data.size()));

		for (int i = 0; i < testSz; i++) {
			String expected = knn.classify(data.get(i), data.subList(testSz, data.size()),
					Distance.Function.Euclidean, 1);

			if (data.get(i).category.equals(expected)) {
				correct++;
			}
			String expected1 = gnb.classify(data.get(i));

			if (data.get(i).category.equals(expected1)) {
				correct1++;
			}
			String expected2 = nc.classify(data.get(i), Distance.Function.Euclidean);

			if (data.get(i).category.equals(expected2)) {
				correct2++;
			}
		}
		System.out.println("Knn Accuracy: " + correct / (double) testSz);
		System.out.println("GNB Accuracy: " + correct1 / (double) testSz);
		System.out.println("NC Accuracy: " + correct2 / (double) testSz);

	}

	public static List<Data> readData(CSVParser parser) {
		int c = 0;
		List<Data> data = new ArrayList<>();
		for (CSVRecord csvRecord : parser) {
			if (c++ > 1) {
				double[] values = new double[csvRecord.size() - 2];
				for (int i = 0; i < values.length; i++) {
					values[i] = Double.parseDouble(csvRecord.get(i + 1));
					data.add(new Data(csvRecord.get(csvRecord.size() - 1), values));
				}
			}
		}
		return data;
	}
}
