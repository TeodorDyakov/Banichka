package ml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Util;

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
	public String classify(Data data) {

		double[] distanceCache = new double[trainSet.size()];
		Map<String, Integer> freq = new HashMap<>();

		for (int i = 0; i < trainSet.size(); i++)
			distanceCache[i] = Distance.distance(data, trainSet.get(i), distanceFunction);

		for (int j = 0; j < k; j++) {

			double minDist = Util.min(distanceCache);

			for (int i = 0; i < trainSet.size(); i++)
				if (minDist == distanceCache[i]) {
					distanceCache[i] = Double.POSITIVE_INFINITY;
					freq.put(trainSet.get(i).category, freq.getOrDefault(trainSet.get(i).category, 0) + 1);
				}

		}
		return Util.getKeyOfMaxValue(freq);
	}

}
