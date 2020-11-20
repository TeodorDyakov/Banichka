package examples;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import classification.NearestCentroid;
import classification.Perceptron;
import classification.QuadraticDiscriminantAnalysis;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import classification.Classifier;
import classification.GaussianNB;
import classification.KNN;
import classification.ZeroR;
import objectModels.Data;
import objectModels.Distance;
import util.AccuracyTesting;
import util.HashingVectorizer;

public class SpamClassification {

	public static void main(String[] args) throws IOException {
		File file = new File("datasets/spam.csv");
		FileReader in = new FileReader(file);
		CSVParser parser = CSVParser.parse(in, CSVFormat.RFC4180);

		List<Data> data = new ArrayList<>();
		int cnt = 0;
		for (CSVRecord csvRecord : parser) {
			String text = csvRecord.get(1);
			double[] vec = HashingVectorizer.vectorize(text, 10000);
			if (cnt++ > 5 && cnt < 3000) {
				data.add(new Data(csvRecord.get(0), vec));
			}
		}

		Collections.shuffle(data);
		List<Data> testSet = data.subList(0, 100), trainSet = data.subList(100, 200);

		Classifier gnb, knn, nc, zeroR, mcp, qda;

		gnb = new GaussianNB();
		knn = new KNN().setDistanceFunction(Distance.Function.Euclidean).setK(1);
		nc = new NearestCentroid().setDistanceFunction(Distance.Function.Euclidean);
		zeroR = new ZeroR();
		mcp = new Perceptron(0.1, 100);
		qda = new QuadraticDiscriminantAnalysis();

		List<Classifier> list = Arrays.asList(gnb, knn, zeroR, mcp, qda, nc);

		for(Classifier c : list){
			c.train(trainSet);
			AccuracyTesting.accuracyTest(c, testSet);
		}
	}

}
