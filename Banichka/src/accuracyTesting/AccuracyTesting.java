package accuracyTesting;

import java.util.List;

import ml.Classifier;
import ml.Data;

public class AccuracyTesting {
	public static double accuracyTest(Classifier c, List<Data> test) {
		int correct = 0;
		for (Data d : test) {
			if (d.category.equals(c.classify(d))) {
				correct++;
			}
		}
		double accu = (double) correct / test.size();
		System.out.println(c.getClass().toString() + "accuracy: " + accu);
		return accu;
	}
}
