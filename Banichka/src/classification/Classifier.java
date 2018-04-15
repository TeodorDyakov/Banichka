package classification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import objectModels.Data;

public abstract class Classifier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7171083870980898018L;

	public abstract void train(List<Data> data);

	public abstract String classify(Data data);

	public void saveModelToFile(File file) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(file.getPath());
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
	}

	public static Classifier readModelFromFile(File file) throws IOException, ClassNotFoundException {
		FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
		@SuppressWarnings("resource")
		ObjectInputStream in = new ObjectInputStream(fileIn);
		return (Classifier) in.readObject();
	}

}
