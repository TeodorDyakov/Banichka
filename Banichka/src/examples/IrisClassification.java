package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import accuracyTesting.AccuracyTesting;
import ml.Data;
import ml.Distance;
import ml.GaussianNB;
import ml.KNN;
import ml.NearestCentroid;
import ml.ZeroR;
import util.Util;

public class IrisClassification {

	public static void main(String[] args) throws IOException {

		List<Data> data = readData(Util.getCSVparser(new File("Iris.csv")));

		Collections.shuffle(data);

		GaussianNB gnb = new GaussianNB();
		KNN knn = new KNN();
		NearestCentroid nc = new NearestCentroid();
		ZeroR zeroR = new ZeroR();

		int testSz = data.size() / 2;

		zeroR.train(data);
		gnb.train(data.subList(testSz, data.size()));
		nc.train(data.subList(testSz, data.size()), Distance.Function.Euclidean);
		knn.train(data.subList(testSz, data.size()), Distance.Function.Euclidean, 1);
		
		double knnAcc = AccuracyTesting.accuracyTest(knn, data.subList(0, testSz));
		double gnbAcc = AccuracyTesting.accuracyTest(gnb, data.subList(0, testSz));
		double ncAcc = AccuracyTesting.accuracyTest(nc, data.subList(0, testSz));
		double zeroRAcc = AccuracyTesting.accuracyTest(zeroR, data.subList(0, testSz));

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
