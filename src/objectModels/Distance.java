package objectModels;

public class Distance {

	public static enum Function {
		Hamming, Euclidean, CosineSimilarity
	}

	public static double distance(Data node1, Data node2, Function d) {
		return distance(node1.features, node2.features, d);
	}

	public static double distance(double[] vec1, double[] vec2, Function d) {
		double dist = 0, productSum = 0, sqSum1 = 0, sqSum2 = 0;
		for (int i = 0; i < vec1.length; i++) {

			if (d == Function.Hamming)
				if (vec1[i] != vec2[i])
					dist++;

			if (d == Function.Euclidean)
				dist += Math.pow(Math.abs(vec1[i] - vec2[i]), 2);

			if (d == Function.CosineSimilarity) {
				productSum += vec1[i] * vec2[i];

				sqSum1 += vec1[i] * vec1[i];
				sqSum2 += vec2[i] * vec2[i];
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
