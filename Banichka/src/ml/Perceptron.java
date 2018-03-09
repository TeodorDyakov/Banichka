package ml;

import java.util.Arrays;

public class Perceptron {
	double bias = 0.5;

	double[][] inputs = new double[][] { { 10, 9 }, { 10, 10 }, { 9, 9 }, { 1, 1 }, { 2, 1 }, { 3, 3 },
			{ 20, 20 }, { 21, 20 } };
	double[] weights = new double[inputs[0].length];
	int[] inputClasses = new int[] { 1, 1, 1, 0, 0, 0, 0, 1, 1 };
	int epoch = 100;

	public Perceptron() {

	}

	public Perceptron(double[][] inputs, int[] inputClasses) {
		this.inputs = inputs;
		this.inputClasses = inputClasses;
		weights = new double[inputs[0].length];
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
