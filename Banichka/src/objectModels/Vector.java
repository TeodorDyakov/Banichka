package objectModels;

import java.util.Arrays;
import java.util.Iterator;

public class Vector implements Iterable<Double> {

	private double[] values;

	public Vector(double[] values) {
		this.values = values;
	}

	static Vector copyOf(Vector vec) throws CloneNotSupportedException {
		return (Vector) vec.clone();
	}

	public double magnitude() {
		return Math.sqrt(this.dotProduct(this));
	}

	public double dotProduct(Vector vec) {
		double dotProduct = 0.0;
		for (int i = 0; i < values.length && i < vec.values.length; i++) {
			dotProduct += values[i] * vec.values[i];
		}
		return dotProduct;
	}

	public double mean() {
		return this.sum() / values.length;
	}

	public void add(Vector source) {
		for (int i = 0; i < values.length; i++) {
			values[i] += source.values[i];
		}
	}

	public void divide(double div) {
		for (int i = 0; i < values.length; i++) {
			values[i] /= div;
		}
	}

	public void multiply(double m) {
		for (int i = 0; i < values.length; i++) {
			values[i] *= m;
		}
	}

	public double sum() {
		double sum = 0.0;
		for (double a : values) {
			sum += a;
		}
		return sum;
	}

	public int size() {
		return values.length;
	}

	public double min() {
		double min = Double.POSITIVE_INFINITY;
		for (double a : values) {
			min = Math.min(min, a);
		}
		return min;
	}

	public double max() {
		double max = Double.NEGATIVE_INFINITY;
		for (double a : values) {
			max = Math.max(max, a);
		}
		return max;
	}

	@Override
	public Iterator<Double> iterator() {
		return Arrays.stream(values).iterator();
	}

	public static void main(String[] args) throws CloneNotSupportedException {
		Vector v = new Vector(new double[] { 1, 2, 3 });
		for (double d : v) {
			System.out.println(d);
		}
		Vector v1 = Vector.copyOf(v);
	}
}
