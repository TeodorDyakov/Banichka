package examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import classification.QuadraticDiscriminantAnalysis;
import classification.ZeroR;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import classification.Classifier;
import classification.GaussianNB;
import classification.KNN;
import classification.NearestCentroid;
import classification.Perceptron;
import objectModels.Data;
import objectModels.Distance;
import util.AccuracyTesting;
import util.Util;

public class HandwrittenDigitRecognition {

	public static void main(String[] args) throws IOException {

		//read the data from a csv file
		List<Data> data = readData(Util.getCSVparser(new File("datasets/digits.txt")));

		//shuffle the data so there is no bias
		Collections.shuffle(data, new Random(23));

		//create a training set used for training the classifiers
		// and a test set on which we test accuracy of the classifiers
		List<Data>trainSet = data.subList(0, 1000), testSet = data.subList(0, 2000);

		//initialize and create the classifiers
		Classifier gnb, knn, nc, zeroR, mcp;
		gnb = new GaussianNB();
		knn = new KNN().setDistanceFunction(Distance.Function.Euclidean).setK(1);
		nc = new NearestCentroid().setDistanceFunction(Distance.Function.Euclidean);
		zeroR = new ZeroR();
		mcp = new Perceptron(0.1, 10);

		List<Classifier> list = Arrays.asList(gnb, knn, zeroR, mcp, nc);

		for(Classifier c : list){
			c.train(trainSet);
			AccuracyTesting.accuracyTest(c, testSet);
		}
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
