package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public class Util {
	public static CSVParser getCSVparser(File f) throws IOException {
		Reader in = new FileReader(f);
		return CSVParser.parse(in, CSVFormat.RFC4180);
	}
}
