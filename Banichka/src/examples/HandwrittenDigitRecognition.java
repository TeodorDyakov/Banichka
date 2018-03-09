package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import accuracyTesting.AccuracyTesting;
import ml.Data;
import ml.Distance;
import ml.GaussianNB;
import ml.KNN;
import ml.NearestCentroid;
import util.Util;

public class HandwrittenDigitRecognition {
	public static void main(String[] args) throws IOException {

		List<Data> data = readData(Util.getCSVparser(new File("digits.txt")));

		KNN knn = new KNN();
		GaussianNB gnb = new GaussianNB();
		NearestCentroid nc = new NearestCentroid();

		Collections.shuffle(data, new Random(new Random().nextLong()));

		int testSz = 500;

		gnb.train(data.subList(testSz, data.size()));
		nc.train(data.subList(testSz, data.size()), Distance.Function.Euclidean);
		knn.train(data.subList(testSz, data.size()), Distance.Function.Euclidean, 1);

		double knnAcc = AccuracyTesting.accuracyTest(knn, data.subList(0, testSz));
		double gnbAcc = AccuracyTesting.accuracyTest(gnb, data.subList(0, testSz));
		double ncAcc = AccuracyTesting.accuracyTest(nc, data.subList(0, testSz));

	}

	public static List<Data> readData(CSVParser parser) {
		int c = 0;
		List<Data> data = new ArrayList<>();
		for (CSVRecord csvRecord : parser) {
			if (c++ > 10) {
				double[] values = new double[csvRecord.size() - 1];
				for (int i = 0; i < values.length; i++) {
					values[i] = Double.parseDouble(csvRecord.get(i + 1));
				}
				data.add(new Data(csvRecord.get(0), values));
			}
		}
		return data;
	}
}
