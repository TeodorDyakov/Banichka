package ml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class GaussianNB implements Classifier {

	int totalNodes = 0;

	public Map<String, SummaryStatistics[]> stats = new HashMap<>();
	public Map<String, NormalDistribution[]> categoryToND = new HashMap<>();

	public void train(List<Data> trainSet) {
		totalNodes = trainSet.size();
		for (Data node : trainSet) {
			SummaryStatistics[] statistics = stats.get(node.category);

			if (statistics == null) {
				statistics = new SummaryStatistics[node.features.length];
				for (int i = 0; i < statistics.length; i++) {
					statistics[i] = new SummaryStatistics();
				}
			}

			for (int i = 0; i < node.features.length; i++) {
				statistics[i].addValue(node.features[i]);
			}
			stats.putIfAbsent(node.category, statistics);
		}

		for (Entry<String, SummaryStatistics[]> e : stats.entrySet()) {
			if (categoryToND.get(e.getKey()) == null) {
				NormalDistribution[] ndArray = new NormalDistribution[e.getValue().length];
				for (int i = 0; i < e.getValue().length; i++) {
					double sd = e.getValue()[i].getStandardDeviation();
					ndArray[i] = new NormalDistribution(e.getValue()[i].getMean(), sd <= 0 ? 1 : sd);
				}
				categoryToND.put(e.getKey(), ndArray);
			}

		}
	}

	@Override
	public String classify(Data node) {
		double max = Double.NEGATIVE_INFINITY;
		String prediction = null;
		for (Entry<String, SummaryStatistics[]> e : stats.entrySet()) {
			SummaryStatistics[] stats = e.getValue();

			double prob = Math.log((double) stats[0].getN() / totalNodes);

			for (int i = 0; i < stats.length; i++) {
				prob += categoryToND.get(e.getKey())[i].logDensity(node.features[i]);
				max++;
			}
			if (max <= prob) {
				max = prob;
				prediction = e.getKey();
			}
		}
		return prediction;
	}

}
