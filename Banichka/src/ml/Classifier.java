package ml;

import java.util.List;

public interface Classifier {

	public void train(List<Data> data);

	public String classify(Data data);

}
