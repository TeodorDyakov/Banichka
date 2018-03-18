package classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objectModels.Data;
import objectModels.Distance;
import util.Util;

public class NearestCentroid implements Classifier {

	Collection<Data> centroids;
	Distance.Function distanceFunction;

	public NearestCentroid setDistanceFunction(Distance.Function f) {
		this.distanceFunction = f;
		return this;
	}

	public void train(List<Data> train) {
		Map<String, Integer> categoryCount = new HashMap<>();
		Map<String, Data> catToCentroid = new HashMap<>();

		for (Data node : train) {
			categoryCount.put(node.category, categoryCount.getOrDefault(node.category, 0) + 1);

			Data centroid = catToCentroid.getOrDefault(node.category,
					new Data(node.category, new double[node.features.length]));

			centroid.features = Util.addInPlace(centroid.features, node.features);

			catToCentroid.putIfAbsent(centroid.category, centroid);
		}

		for (Data node : catToCentroid.values()) {
			node.features = Util.divideInPlace(node.features, categoryCount.get(node.category));
		}
		centroids = new ArrayList<>(catToCentroid.values());
	}

	@Override
	public String classify(Data node) {
		String closest = null;
		double dist = Integer.MAX_VALUE;
		for (Data centroid : centroids) {
			if (dist > Distance.distance(node, centroid, distanceFunction)) {
				dist = Distance.distance(node, centroid, distanceFunction);
				closest = centroid.category;
			}
		}
		return closest;
	}
}
