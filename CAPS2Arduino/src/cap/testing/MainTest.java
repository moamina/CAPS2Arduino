package cap.testing;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

import cap.model.entity.SoftwareArchitecture;
import cap.model.handler.XMLParser;
import cap.sim.analyzer.ModelAnalyzer;
import cap.sim.generator.ConfigurationGenerator;
import cap.sim.generator.ScriptController;
import cap.sim.utility.FileUtility;

public class MainTest {
	
	public static void main(String[] args) {
//		String str="/test/test2/test.txt";
//		String sub=str.substring(0,str.lastIndexOf("/"));
//		System.out.println(sub);
//		
//		checkPath(sub);
//		checkfile("/test/test2/test.txt");
//		FileUtility.createAndWriteToFile("/test/test2/test.txt", "Text file");
		String s="//@SAElements.2/@modes.0/@behaviouralElements.7 //@SAElements.2/@modes.0/@behaviouralElements.11";
		System.out.println(s.split(" ")[0]);
		System.out.println(s.split(" ")[1]);
	}

	private static void checkfile(String path) {
		// TODO Auto-generated method stub
		File f=new File(path);
		if(!f.exists())
		{
			try {
				System.out.println(f.createNewFile());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void checkPath(String path) {
		// TODO Auto-generated method stub
		File f=new File(path);
		if(!f.exists())
		{
			System.out.println(f.mkdirs());
		}
	}
	
	

}
