package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import classification.Classifier;
import classification.GaussianNB;
import classification.KNN;
import classification.Perceptron;
import classification.NearestCentroid;
import objectModels.Data;
import objectModels.Distance;
import util.AccuracyTesting;
import util.Util;

public class HandwrittenDigitRecognition {

	public static void main(String[] args) throws IOException {

		Classifier knn = new KNN().setK(1).setDistanceFunction(Distance.Function.Euclidean);
		Classifier gnb = new GaussianNB();
		Classifier nc = new NearestCentroid().setDistanceFunction(Distance.Function.Euclidean);
		Classifier mcp = new Perceptron(0.1, 10);

		List<Data> data = readData(Util.getCSVparser(new File("digits.txt")));
		Collections.shuffle(data);
		int testSz = 500;

		gnb.train(data.subList(testSz, data.size()));
		nc.train(data.subList(testSz, data.size()));
		knn.train(data.subList(testSz, data.size()));
		mcp.train(data.subList(testSz, data.size()));

		double knnAcc = AccuracyTesting.accuracyTest(knn, data.subList(0, testSz));
		double gnbAcc = AccuracyTesting.accuracyTest(gnb, data.subList(0, testSz));
		double ncAcc = AccuracyTesting.accuracyTest(nc, data.subList(0, testSz));
		double mcpAcc = AccuracyTesting.accuracyTest(mcp, data.subList(0, testSz));

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
