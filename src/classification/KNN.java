package classification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objectModels.Data;
import objectModels.Distance;
import util.Util;

public class KNN extends Classifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6427966460938350044L;
	List<Data> trainSet;
	Distance.Function distanceFunction = Distance.Function.Euclidean;
	int k = 1;

	public KNN setK(int k) {
		this.k = k;
		return this;
	}

	public KNN setDistanceFunction(Distance.Function distanceFunction) {
		this.distanceFunction = distanceFunction;
		return this;
	}

	public void train(List<Data> trainSet) {
		this.trainSet = trainSet;
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
