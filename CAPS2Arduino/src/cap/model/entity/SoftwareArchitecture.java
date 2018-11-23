package cap.model.entity;

import java.util.ArrayList;
import java.util.List;


public class SoftwareArchitecture {
	

	private static SoftwareArchitecture SofArc;
	private List<SAElement> elements=null;
	
	private SoftwareArchitecture() {
		elements=new ArrayList<SAElement>();
	}
	
	public static SoftwareArchitecture getSoftwareArchitechure()
	{
		if(SofArc==null)
			SofArc= new SoftwareArchitecture();
		return SofArc;
	}

	public List<SAElement> getElements() {
		return elements;
	}

	public void setElements(List<SAElement> elements) {
		this.elements = elements;
	}
	
	

}
