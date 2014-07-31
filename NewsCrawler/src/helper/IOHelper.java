package helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class IOHelper {
	private static String CHARSET = "utf-8";

	public static String getCHARSET() {
		return CHARSET;
	}

	public static void setCHARSET(String cHARSET) {
		CHARSET = cHARSET;
	}

	private static void writeFile(String path, String data, boolean append)
			throws IOException {
		BufferedWriter bfw = null;
		try {
			bfw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path, append), CHARSET));
			bfw.write(data);
			bfw.flush();
			bfw.close();
		} catch (IOException e) {
			try {
				bfw.close();
			} catch (Exception e1) {
			}
			System.err.println(e.getMessage());
			throw e;
		}
	}

	public static void newFile(String path, String data) throws IOException {
		writeFile(path, data, false);
	}

	public static void appendFile(String path, String data) throws IOException {
		writeFile(path, data, true);
	}

	public static String readFile(String path) throws IOException {
		String data = "";
		BufferedReader bfr = null;
		try {
			bfr = new BufferedReader(new InputStreamReader(new FileInputStream(
					path), CHARSET));
			String newLine = null;
			while ((newLine = bfr.readLine()) != null) {
				data += newLine + "\n";
			}
			bfr.close();
			return data;
		} catch (IOException e) {
			try {
				bfr.close();
			} catch (Exception e1) {
			}
			System.err.println(e.getMessage());
			throw e;
		}
	}

	public static void print(Object str) {
		System.out.print(str);
	}

	public static void println(Object str) {
		System.out.println(str);
	}
}
