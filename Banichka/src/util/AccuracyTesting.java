package util;

import java.util.List;

import classification.Classifier;
import objectModels.Data;

public class AccuracyTesting {
	public static double accuracyTest(Classifier c, List<Data> test) {
		int correct = 0;
		for (Data d : test) {
			if (d.category.equals(c.classify(d))) {
				correct++;
			}
		}
		double accu = (double) correct / test.size();
		System.out.println(c.getClass().getSimpleName() + " accuracy: " + accu);
		return accu;
	}
}
