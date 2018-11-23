package cap.sim.utility;

public class StringUtility {
	
	public static String getElementID(String s)
	{
		return s.split("/")[2].replace("@SAElements.", "");
	}
	public static String getBehaviouralElementID(String s) throws Exception
	{
		////@SAElements.0/@modes.0/@behaviouralElements.14
		return s.split("@behaviouralElements.")[1].trim();//s.split("/")[2].replace("@SAElements.", "");
	}
	public static String getModeID(String s)
	{
		//@SAElements.0/@modes.1/@entries.0
		return s.split("/")[3].split("modes.")[1].trim();//s.split("/")[2].replace("@SAElements.", "");
	}
	public static String getExitID(String s)
	{
		//@SAElements.0/@modes.0/@exits.0
		return s.split("@exits.")[1].trim();//s.split("/")[2].replace("@SAElements.", "");
	}
	public static String getAppDataID(String s)
	{
		//@SAElements.2/@applicationData.5
		return s.split("@applicationData.")[1].trim();
	}
	
	public static void main(String[] args) {
		//System.out.println(getBehaviouralElementID("//@SAElements.0/@modes.0/@behaviouralElements.14"));
		String s="//@SAElements.0/@modes.1/@entries.0";
		System.out.println(s.split("/")[3].split("modes.")[1]);
	}
}
