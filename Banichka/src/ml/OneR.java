package ml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OneR {

	String prediction = "";

	public void train(List<Data> data) {
		Map<String, Map<String, Integer>> map = new HashMap<>();
		for (Data dataPoint : data) {
			map.putIfAbsent(dataPoint.category, new HashMap<>());
		}
		for (Data dataPoint : data) {
			Map m = map.get(dataPoint.category);
//			for(int i = 0; i < )
		}
	}

	public String classify(Data data) {
		return prediction;
	}

}
