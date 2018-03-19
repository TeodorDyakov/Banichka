package regression;

import util.Util;

public class SimpleLinearRegression {

	private double alpha = 0, beta = 0;

	public void fit(double[] x, double[] y) {
		double sumX = Util.sum(x), sumY = Util.sum(y), sumXX = Util.dotProduct(x, x),
				sumXY = Util.dotProduct(x, y);
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

	public double predict(double x) {
		return alpha + x * beta;
	}

}
