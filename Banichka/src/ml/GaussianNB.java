package ml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class GaussianNB {

	int totalNodes = 0;

	Map<String, List<NormalDistribution>> stdDistributionOfFeatures = new HashMap<>();
	Map<String, Integer> countInCategory = new HashMap<>();

	public void train(List<Data> trainSet) {
		totalNodes = trainSet.size();
		for (Entry<String, List<Data>> e : categoryToNodes(trainSet).entrySet()) {
			List<NormalDistribution> dForCat = new ArrayList<>();
			int featureCount = trainSet.get(0).features.length;
			for (int i = 0; i < featureCount; i++) {

				DescriptiveStatistics stats = new DescriptiveStatistics();
				for (Data node : e.getValue()) {
					stats.addValue(node.features[i]);
				}
				NormalDistribution nd = null;
				if (stats.getStandardDeviation() <= 0) {
					nd = new NormalDistribution(stats.getMean(), Double.MIN_VALUE);
				} else {
					nd = new NormalDistribution(stats.getMean(), stats.getStandardDeviation());
				}
				dForCat.add(nd);
			}
			countInCategory.put(e.getKey(), e.getValue().size());
			stdDistributionOfFeatures.put(e.getKey(), dForCat);
		}
	}

	public String classify(Data node) {
		double max = Double.NEGATIVE_INFINITY;
		String MAP = "";
		for (Entry<String, List<NormalDistribution>> e : stdDistributionOfFeatures.entrySet()) {
			double prob = Math.log((double) countInCategory.get(e.getKey()) / totalNodes);
			for (int i = 0; i < e.getValue().size(); i++) {
				prob += Math.log(e.getValue().get(i).density(node.features[i]));
			}
			if (max <= prob) {
				max = prob;
				MAP = e.getKey();
			}
		}
		return MAP;
	}

	static Map<String, List<Data>> categoryToNodes(List<Data> nodes) {
		Map<String, List<Data>> categoryToNodes = new HashMap<>();
		for (Data node : nodes) {
			List<Data> nd = categoryToNodes.get(node.category);
			if (nd == null) {
				nd = new ArrayList<>();
				categoryToNodes.put(node.category, nd);
			}
			nd.add(node);
		}
		return categoryToNodes;
	}

}
