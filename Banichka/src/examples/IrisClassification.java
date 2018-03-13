package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import accuracyTesting.AccuracyTesting;
import ml.Classifier;
import ml.Data;
import ml.Distance;
import ml.GaussianNB;
import ml.KNN;
import ml.NearestCentroid;
import ml.ZeroR;
import util.Util;

public class IrisClassification {

	public static void main(String[] args) throws IOException {

		Classifier gnb = new GaussianNB();
		Classifier knn = new KNN().setDistanceFunction(Distance.Function.Euclidean).setK(1);
		Classifier nc = new NearestCentroid().setDistanceFunction(Distance.Function.Euclidean);
		Classifier zeroR = new ZeroR();

		List<Data> data = readData(Util.getCSVparser(new File("Iris.csv")));
		Collections.shuffle(data);
		int testSz = data.size() / 2;

		List<Data> testSet = data.subList(0, testSz), trainSet = data.subList(testSz, data.size());

		zeroR.train(trainSet);
		gnb.train(trainSet);
		nc.train(trainSet);
		knn.train(trainSet);

		double knnAcc = AccuracyTesting.accuracyTest(knn, testSet),
				gnbAcc = AccuracyTesting.accuracyTest(gnb, trainSet),
				ncAcc = AccuracyTesting.accuracyTest(nc, trainSet),
				zeroRAcc = AccuracyTesting.accuracyTest(zeroR, trainSet);

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
