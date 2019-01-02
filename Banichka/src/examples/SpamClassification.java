package examples;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		File file = new File("spam.csv");
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
		Classifier knn = new KNN();
		Classifier ZeroR = new ZeroR();
		Classifier nb = new GaussianNB();
		Classifier knn2 = new KNN().setDistanceFunction(Distance.Function.CosineSimilarity);
		knn.train(data.subList(data.size() - 1000, data.size()));
		nb.train(data.subList(data.size() - 1000, data.size()));
		ZeroR.train(data.subList(data.size() - 1000, data.size()));
		double knnAcc = AccuracyTesting.accuracyTest(knn, data.subList(0, 1000));
		knn2.train(data.subList(data.size() - 1000, data.size()));
		double knnAcc1 = AccuracyTesting.accuracyTest(knn2, data.subList(0, 1000));
		double ze = AccuracyTesting.accuracyTest(ZeroR, data.subList(0, 1000));
		double nba = AccuracyTesting.accuracyTest(nb, data.subList(0, 1000));
	}

}
