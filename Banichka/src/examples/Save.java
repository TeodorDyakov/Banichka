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
import objectModels.Data;
import util.AccuracyTesting;
import util.Util;

public class Save {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Classifier gnb = new GaussianNB();

		List<Data> data = readData(Util.getCSVparser(new File("Iris.csv")));
		Collections.shuffle(data);
		int testSz = data.size() / 2;

		List<Data> testSet = data.subList(0, testSz), trainSet = data.subList(testSz, data.size());

		gnb.train(trainSet);
		gnb.saveModelToFile(new File("model.txt"));
		Classifier gnb1 = Classifier.readModelFromFile(new File("model.txt"));
		double gnb1nAcc = AccuracyTesting.accuracyTest(gnb1, testSet),
				gnbAcc = AccuracyTesting.accuracyTest(gnb, testSet);

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
