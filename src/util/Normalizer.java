package util;

import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import objectModels.Data;

public class Normalizer {
	public static void minMaxNormalize(List<Data> dataPoints) {
		int numFeatures = dataPoints.get(0).features.length;

		for (int i = 0; i < numFeatures; i++) {
			DescriptiveStatistics ds = new DescriptiveStatistics();
			for (Data data : dataPoints) {
				ds.addValue(data.features[i]);
			}
			for (Data data : dataPoints) {
				double div = ds.getMax() - ds.getMin();
				if (div != 0) {
					data.features[i] = (data.features[i] - ds.getMin()) / (ds.getMax() - ds.getMin());
				}
			}
			// dump descriptive statistics for each feature
			// System.out.println(ds);
		}
	}
}
