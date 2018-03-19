package classification;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import objectModels.Data;

public class Perceptron implements Classifier {

	public Map<String, double[]> classWeights = new HashMap<>();

	public double learningRate;
	public int epoch = 10;
	public double accuracy = 0;

	public void train(List<Data> data) {
		for (Data d : data) {

			double[] weights = new double[d.features.length + 1];
			Arrays.fill(weights, Math.random());

			// this is the bias for each node in the output layer
			weights[weights.length - 1] = 1;
			classWeights.putIfAbsent(d.category, weights);

		}
		for (int i = 0; i < epoch && accuracy <= 0.90; i++) {
			accuracy = 0;
			for (Data d : data) {
				learn(d.category, d.features);
			}
			accuracy /= data.size();
		}
	}

	public String classify(Data d) {
		return getResult(d.features);
	}

	public Perceptron(double learningRate, int epoch) {
		this.learningRate = learningRate;
		this.epoch = epoch;
	}

	public String getResult(double[] inputs) {
		String max = null;
		double maxPr = Double.NEGATIVE_INFINITY;
		for (Entry<String, double[]> e : classWeights.entrySet()) {
			double dotProduct = dotProduct(inputs, e.getValue());
			if (dotProduct > maxPr) {
				maxPr = dotProduct;
				max = e.getKey();
			}
		}
		return max;
	}

	public String learn(String expectedClass, double[] inputs) {
		// get the currently assigned class
		String resultClass = getResult(inputs);

		// if the retruned class does not match the expected class
		if (!resultClass.equals(expectedClass)) {
			adjustWeights(classWeights.get(resultClass), -1, inputs);
			adjustWeights(classWeights.get(expectedClass), 1, inputs);
		} else {
			accuracy++;
		}

		return resultClass;
	}

	private void adjustWeights(double[] weights, double error, double[] inputs) {
		for (int i = 0; i < weights.length - 1; i++) {
			weights[i] += learningRate * error * inputs[i];
		}
		weights[weights.length - 1] += learningRate * error;
	}

	private double dotProduct(double[] inputs, double[] weights) {
		double dotProduct = 0.0;
		for (int i = 0; i < inputs.length; i++) {
			dotProduct += inputs[i] * weights[i];
		}
		return dotProduct + weights[weights.length - 1];
	}

}