package helper;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LogHelper {
	public static synchronized void log(String str) {
		try {
			BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("log.log", true), "utf-8"));
			bfw.write(str + "\r\n");
			bfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logThis(int year,int month,int day) {
		try {
			BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("tmp"), "utf-8"));
			bfw.write(year + "\r\n");
			bfw.write(month + "\r\n");
			bfw.write(day);
			bfw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
