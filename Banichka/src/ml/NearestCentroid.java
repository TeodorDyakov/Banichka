package ml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearestCentroid {

	Collection<Data> centroids;

	public void train(List<Data> train) {
		Map<String, Integer> map = new HashMap<>();
		Map<String, Data> catToCentroid = new HashMap<>();
		for (Data node : train) {
			map.put(node.category, map.getOrDefault(node.category, 0) + 1);

			Data centroid = catToCentroid.getOrDefault(node.category,
					new Data(node.category, new double[node.features.length]));

			for (int i = 0; i < node.features.length; i++) {
				centroid.features[i] += node.features[i];
			}

			catToCentroid.putIfAbsent(centroid.category, centroid);
		}
		for (Data node : catToCentroid.values()) {
			for (int i = 0; i < node.features.length; i++) {
				node.features[i] /= map.get(node.category);
			}
		}
		centroids = new ArrayList<>(catToCentroid.values());
	}

	public String classify(Data node, Distance.Function d) {
		String closest = null;
		double dist = Integer.MAX_VALUE;
		for (Data centroid : centroids) {
			if (dist > Distance.distance(node, centroid, d)) {
				dist = Distance.distance(node, centroid, d);
				closest = centroid.category;
			}
		}
		return closest;
	}
}
