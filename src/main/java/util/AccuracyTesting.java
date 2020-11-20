package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import classification.Classifier;
import objectModels.Data;

public class AccuracyTesting {

	public static double accuracyTest(Classifier c, List<Data> test) {
		Map<String, Map<String, Integer>> confusionMatrix = new HashMap<>();
		int correct = 0;
		for (Data d : test) {
			String prediction = c.classify(d);
			if (d.category.equals(prediction)) {
				correct++;
			}
			Map<String, Integer> map = confusionMatrix.get(d.category);
			if (map == null) {
				map = new HashMap<>();
			}
			map.put(prediction, map.getOrDefault(prediction, 0) + 1);
			confusionMatrix.put(d.category, map);
		}
		double accu = (double) correct / test.size();
		System.out.println(c.getClass().getSimpleName() + " accuracy: " + accu);
		System.out.println(confusionMatrix);
		return accu;
	}
}
