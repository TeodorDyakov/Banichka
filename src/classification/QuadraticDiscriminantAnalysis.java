package classification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.stat.descriptive.MultivariateSummaryStatistics;

import objectModels.Data;

public class QuadraticDiscriminantAnalysis extends Classifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuadraticDiscriminantAnalysis() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, MultivariateSummaryStatistics> stats = new HashMap<>();
	public Map<String, MultivariateNormalDistribution> categoryToND = new HashMap<>();

	public void train(List<Data> trainSet) {
		int dim = trainSet.get(0).features.length;
		for (Data node : trainSet) {
			MultivariateSummaryStatistics statistics = stats.get(node.category);

			if (statistics == null) {
				statistics = new MultivariateSummaryStatistics(dim, true);
			}

			statistics.addValue(node.features);
			stats.putIfAbsent(node.category, statistics);
		}

		for (Entry<String, MultivariateSummaryStatistics> e : stats.entrySet()) {
			if (!categoryToND.containsKey(e.getKey())) {
				MultivariateSummaryStatistics mvs = e.getValue();
				double[] m = mvs.getMean();
				double[][] cov = mvs.getCovariance().getData();
				MultivariateNormalDistribution multivariate = new MultivariateNormalDistribution(m, cov);
				categoryToND.put(e.getKey(), multivariate);
			}

		}

	}

	@Override
	public String classify(Data data) {
		double max = 0;
		String argmax = "";
		for (Entry<String, MultivariateNormalDistribution> e : categoryToND.entrySet()) {
			double pdf = e.getValue().density(data.features);
			if (max < pdf) {
				max = pdf;
				argmax = e.getKey();
			}
		}
		return argmax;
	}

}
