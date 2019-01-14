package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import classification.Classifier;
import classification.GaussianNB;
import classification.KNN;
import classification.NearestCentroid;
import classification.Perceptron;
import classification.QuadraticDiscriminantAnalysis;
import classification.ZeroR;
import objectModels.Data;
import objectModels.Distance;
import util.AccuracyTesting;
import util.Util;

public class IrisClassification {

	public static void main(String[] args) throws IOException {

		Classifier gnb = new GaussianNB();
		Classifier knn = new KNN().setDistanceFunction(Distance.Function.Euclidean).setK(1);
		Classifier nc = new NearestCentroid().setDistanceFunction(Distance.Function.Euclidean);
		Classifier zeroR = new ZeroR();
		Classifier mcp = new Perceptron(0.1, 1000);
		Classifier qda = new QuadraticDiscriminantAnalysis();
		List<Data> data = readData(Util.getCSVparser(new File("Iris.csv")));
		Collections.shuffle(data, new Random(11));
		int testSz = data.size() - 200;

		List<Data> testSet = data.subList(0, testSz), trainSet = data.subList(testSz, data.size());

		// util.Normalizer.minMaxNormalize(data);

		zeroR.train(trainSet);
		gnb.train(trainSet);
		nc.train(trainSet);
		knn.train(trainSet);
		qda.train(trainSet);
		mcp.train(trainSet);
		double knnAcc = AccuracyTesting.accuracyTest(knn, testSet),
				gnbAcc = AccuracyTesting.accuracyTest(gnb, testSet),
				ncAcc = AccuracyTesting.accuracyTest(nc, testSet),
				zeroRAcc = AccuracyTesting.accuracyTest(zeroR, testSet),
				mcpAcc = AccuracyTesting.accuracyTest(mcp, testSet),
		qdaAcc = AccuracyTesting.accuracyTest(qda, testSet);

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
