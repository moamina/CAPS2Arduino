package cap.sim.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtility {

	
	public static String readFromFile(String fileName) {

		BufferedReader br = null;
		FileReader fr = null;

		try {

			//br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(fileName);
			br = new BufferedReader(fr);

			String sCurrentLine;
			StringBuilder sb=new StringBuilder("");
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
			
			return sb.toString();

		} catch (IOException e) {

			e.printStackTrace();
			return null;

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
	
	public static boolean createAndWriteToFile(String file, String content) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			bw.write(content);
			return true;

		} catch (IOException e) {

			e.printStackTrace();
			return false;

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	}

}
