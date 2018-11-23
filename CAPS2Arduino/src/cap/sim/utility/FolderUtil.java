package cap.sim.utility;

import java.io.File;
import java.io.IOException;

public class FolderUtil {
	
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

	public static void checkPath(String path) {
		// TODO Auto-generated method stub
		File f=new File(path);
		if(!f.exists())
		{
			System.out.println("creating "+f.getName()+":"+f.mkdirs());
		}
	}

}
