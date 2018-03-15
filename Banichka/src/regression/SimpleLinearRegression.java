package regression;

import org.apache.commons.math3.distribution.BinomialDistribution;

import util.Util;

public class SimpleLinearRegression {

	private double alpha = 0, beta = 0;

	public void fit(double[] x, double[] y) {
		double sumX = Util.sum(x), sumY = Util.sum(y), sumXX = Util.sumOfSquares(x),
				sumXY = Util.sumOfProducts(x, y);
		int n = x.length;

		beta = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
		alpha = sumY / n - (beta * sumX) / n;
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	static double[] x = new double[] { 1.47, 1.50, 1.52, 1.55, 1.57, 1.60, 1.63, 1.65, 1.68, 1.70, 1.73, 1.75,
			1.78, 1.80, 1.83 };
	static double[] y = new double[] { 52.21, 53.12, 54.48, 55.84, 57.20, 58.57, 59.93, 61.29, 63.11, 64.47,
			66.28, 68.10, 69.92, 72.19, 74.46 };

	public static void main(String[] args) {
		SimpleLinearRegression slr = new SimpleLinearRegression();
		slr.fit(x, y);
		System.out.println(slr.alpha);
		System.out.println(slr.beta);
		BinomialDistribution bd = new BinomialDistribution(10000, 0.51);
		System.out.println(bd.cumulativeProbability(5000));
	}

}
