package ml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class KNN implements Classifier {

	List<Data> trainSet;
	Distance.Function distanceFunction;
	int k;

	public void train(List<Data> trainSet, Distance.Function d, int k) {
		this.trainSet = trainSet;
		this.distanceFunction = d;
		this.k = k;
	}

	@Override
	public String classify(Data m) {

		double[] distance = new double[trainSet.size()];
		Map<String, Integer> freq = new HashMap<>();
		for (int i = 0; i < trainSet.size(); i++)
			distance[i] = Distance.distance(m, trainSet.get(i), distanceFunction);

		for (int j = 0; j < k; j++) {

			double minDist = Double.POSITIVE_INFINITY;
			for (int i = 0; i < trainSet.size(); i++) {
				if (minDist > distance[i]) {
					minDist = distance[i];
				}
			}

			for (int i = 0; i < trainSet.size(); i++)
				if (minDist == distance[i]) {
					distance[i] = Double.POSITIVE_INFINITY;
					freq.put(trainSet.get(i).category, freq.getOrDefault(trainSet.get(i).category, 0) + 1);
				}

		}
		int max = 0;
		String category = "";
		for (Entry<String, Integer> e : freq.entrySet()) {
			if (max < e.getValue()) {
				category = e.getKey();
				max = e.getValue();
			}
		}
		return category;
	}

}
