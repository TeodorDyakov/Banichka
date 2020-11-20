package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		List<Data> data = readData(Util.getCSVparser(new File("datasets/Iris.csv")));
		Collections.shuffle(data, new Random(11));

		int testSz = data.size() - 200;
		List<Data> testSet = data.subList(0, testSz), trainSet = data.subList(testSz, data.size());

		Classifier gnb, knn, nc, zeroR, mcp, qda;

		gnb = new GaussianNB();
		knn = new KNN().setDistanceFunction(Distance.Function.Euclidean).setK(1);
		nc = new NearestCentroid().setDistanceFunction(Distance.Function.Euclidean);
		zeroR = new ZeroR();
		mcp = new Perceptron(0.1, 1000);
		qda = new QuadraticDiscriminantAnalysis();

		List<Classifier> list = Arrays.asList(gnb, knn, zeroR, mcp, qda, nc);

		for(Classifier c : list){
			c.train(trainSet);
			AccuracyTesting.accuracyTest(c, testSet);
		}
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
