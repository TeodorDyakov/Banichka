package classification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import objectModels.Data;
import util.Util;

public class ZeroR extends Classifier {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6177639131047586090L;
	/*
	 * This is the simplest classifer that can exist. It just retruns the most
	 * common category in the dataset
	 */
	String prediction = "";

	public void train(List<Data> data) {
		Map<String, Integer> categoryFreq = new HashMap<String, Integer>();

		for (Data dataPoint : data) {
			categoryFreq.put(dataPoint.category, categoryFreq.getOrDefault(dataPoint.category, 0) + 1);
		}
		prediction = Util.getKeyOfMaxValue(categoryFreq);
	}

	@Override
	public String classify(Data data) {
		return prediction;
	}

}
