package classification;

import java.util.List;

import objectModels.Data;

public interface Classifier {

	public void train(List<Data> data);

	public String classify(Data data);

}
