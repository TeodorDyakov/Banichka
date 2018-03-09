package util;

public class HashingVectorizer {

	/**
	 * returns a feature vector from text using the hashing trick
	 * 
	 * @param text
	 * @param vectorSize
	 * @return
	 */
	public static double[] vectorize(String text, int vectorSize) {
		double[] vector = new double[vectorSize];
		String[] words = text.split("\\W+");
		for (String s : words) {
			vector[Math.abs(s.hashCode() % vector.length)]++;
		}
		return vector;
	}

}