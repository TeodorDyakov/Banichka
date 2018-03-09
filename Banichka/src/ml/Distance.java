package ml;

public class Distance {

	public static enum Function {
		Hamming, Euclidean, CosineSimilarity
	}

	public static double distance(Data node1, Data node2, Function d) {
		double dist = 0, productSum = 0, sqSum1 = 0, sqSum2 = 0;
		for (int i = 0; i < node1.features.length; i++) {

			if (d == Function.Hamming)
				if (node1.features[i] != node2.features[i])
					dist++;

			if (d == Function.Euclidean)
				dist += Math.pow(Math.abs(node1.features[i] - node2.features[i]), 2);

			if (d == Function.CosineSimilarity) {
				productSum += node1.features[i] * node2.features[i];

				sqSum1 += node1.features[i] * node1.features[i];
				sqSum2 += node2.features[i] * node2.features[i];
			}
		}

		if (d == Function.Euclidean)
			return Math.sqrt(dist);

		if (d == Function.CosineSimilarity) {
			return productSum / Math.sqrt(sqSum1 * sqSum2);
		}
		return dist;
	}
}
