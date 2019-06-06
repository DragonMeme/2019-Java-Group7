/*
    This class is responsible for storing settings and highscores using the txt files
    This class will be responsible for overwriting highscores and settings memory when possible.
 */

package ctrl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Memory {
	private File file;
	private String st;
	private BufferedReader r;

	public String readSettings() {
		file = new File(getRelativePath("setting"));
		try {
			r = new BufferedReader(new FileReader(file));
			st = r.readLine();
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}

	public void writeSettings(String str) {
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(getRelativePath("setting"), false));
			w.write(str);
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> readScores() {
		ArrayList<String> scoreList = new ArrayList<>();
		String score;
		file = new File(getRelativePath("score"));
		try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			while ((score = r.readLine()) != null)
				scoreList.add(score);
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return scoreList;
	}

	public void writeScores(ArrayList<String> strs) {
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(getRelativePath("score"), false));
			for (String str : strs)
				w.write(str + "\n");
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getRelativePath(String path) {
		File filePath = new File("");
		String absPath = filePath.getAbsolutePath() + File.separator + "res" + 
		File.separator + "str" + File.separator;
		return absPath + path + ".txt";
	}
}
