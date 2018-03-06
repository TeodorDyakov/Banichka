package ml;

import java.util.Arrays;

public class Data {

	@Override
	public String toString() {
		return "Data [category=" + category + ", features=" + Arrays.toString(features) + "]";
	}

	public String category;
	double[] features;

	public Data(String category, double[] features) {
		this.category = category;
		this.features = features;
	}

}
