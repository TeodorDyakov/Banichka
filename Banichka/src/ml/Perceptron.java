package ml;

import java.util.Arrays;

public class Perceptron {
	double bias = 0.5;

	double[][] inputs;
	double[] weights;
	int[] inputClasses;
	int epoch = 100;

	public Perceptron() {

	}

	public Perceptron(double[][] inputs, int[] inputClasses, int epoch) {
		this.inputs = inputs;
		this.inputClasses = inputClasses;
		weights = new double[inputs[0].length];
		this.epoch = epoch;
	}

	double classify(double[] inputs) {
		double sum = bias;
		for (int j = 0; j < inputs.length; j++) {
			sum += weights[j] * inputs[j];
		}
		if (sum <= 0) {
			return 0;
		}
		return 1;
	}

	void train() {
		for (int k = 0; k < epoch; k++) {
			for (int i = 0; i < inputs.length; i++) {
				double sum = classify(inputs[i]);
				// update bias
				bias += (inputClasses[i] - sum);
				// update weights
				for (int j = 0; j < inputs[0].length; j++) {
					weights[j] += (inputClasses[i] - sum) * inputs[i][j];
				}
				System.out.println(Arrays.toString(weights));
			}
		}
	}

	public static void main(String[] args) {
		Perceptron p = new Perceptron();

		p.train();
	}

}
