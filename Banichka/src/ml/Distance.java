package ml;

public class Distance {

	public static enum Metric {
		Hamming, Euclidean,
	}

	public static double distance(Data node1, Data node2, Metric d) {
		double dist = 0;
		for (int i = 0; i < node1.features.length; i++) {

			if (d == Metric.Hamming)
				if (node1.features[i] != node2.features[i])
					dist++;

			if (d == Metric.Euclidean)
				dist += Math.pow(Math.abs(node1.features[i] - node2.features[i]), 2);
		}

		if (d == Metric.Euclidean)
			return Math.sqrt(dist);
		return dist;
	}
}
