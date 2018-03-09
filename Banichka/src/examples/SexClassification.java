package examples;

import java.util.ArrayList;
import java.util.List;

import ml.Data;
import ml.GaussianNB;

public class SexClassification {
	public static void main(String[] args) {

		List<Data> data = new ArrayList<>();
		
		data.add(new Data("male", new double[] { 6, 180, 12 }));
		data.add(new Data("male", new double[] { 5.92, 190, 11 }));
		data.add(new Data("male", new double[] { 5.58, 170, 12 }));
		data.add(new Data("male", new double[] { 5.92, 165, 10 }));
		data.add(new Data("female", new double[] { 5, 100, 6 }));
		data.add(new Data("female", new double[] { 5.5, 150, 8 }));
		data.add(new Data("female", new double[] { 5.42, 130, 7 }));
		data.add(new Data("female", new double[] { 5.75, 150, 9 }));

		GaussianNB nb = new GaussianNB();
		nb.train(data);
		System.out.println(nb.classify(new Data(null, new double[] { 6, 130, 8 })));
	}
}
