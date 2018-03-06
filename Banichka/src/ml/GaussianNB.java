package ml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class GaussianNB {

	int totalNodes = 0;

	public Map<String, DescriptiveStatistics[]> stats = new HashMap<>();

	public void train(List<Data> trainSet) {
		totalNodes = trainSet.size();
		for (Data node : trainSet) {
			DescriptiveStatistics[] statistics = stats.get(node.category);

			if (statistics == null) {
				statistics = new DescriptiveStatistics[node.features.length];
				for (int i = 0; i < statistics.length; i++) {
					statistics[i] = new DescriptiveStatistics();
				}
			}

			for (int i = 0; i < node.features.length; i++) {
				statistics[i].addValue(node.features[i]);
			}
			stats.putIfAbsent(node.category, statistics);
		}
	}

	public String classify(Data node) {
		double max = Double.NEGATIVE_INFINITY;
		String MAP = null;
		for (Entry<String, DescriptiveStatistics[]> e : stats.entrySet()) {
			DescriptiveStatistics[] stats = e.getValue();

			double prob = Math.log((double) stats[0].getN() / totalNodes);

			for (int i = 0; i < stats.length; i++) {
				if (stats[i].getStandardDeviation() <= 0) {
					stats[i].addValue(Double.MIN_VALUE);
					stats[i].addValue(Double.MIN_VALUE);
				}
				NormalDistribution nd = new NormalDistribution(stats[i].getMean(),
						stats[i].getStandardDeviation());
				prob += nd.logDensity(node.features[i]);
			}
			if (max <= prob) {
				max = prob;
				MAP = e.getKey();
			}
		}
		return MAP;
	}

}
