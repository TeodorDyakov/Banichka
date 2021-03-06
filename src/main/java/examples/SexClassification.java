package examples;

import java.util.ArrayList;
import java.util.List;

import classification.GaussianNB;
import classification.KNN;
import classification.NearestCentroid;
import objectModels.Data;
import objectModels.Distance;

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
		NearestCentroid nc = new NearestCentroid().setDistanceFunction(Distance.Function.Euclidean);
		KNN knn = new KNN().setDistanceFunction(Distance.Function.Euclidean).setK(1);

		nb.train(data);
		nc.train(data);
		knn.train(data);

		System.out.println(nb.classify(new Data(null, new double[] { 6, 130, 8 })));
		System.out.println(nc.classify(new Data(null, new double[] { 6, 130, 8 })));
		System.out.println(knn.classify(new Data(null, new double[] { 6, 130, 8 })));

	}
}
