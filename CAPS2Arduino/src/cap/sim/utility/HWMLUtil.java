package cap.sim.utility;

import cap.model.handler.HWML_XMLParser;

import com.hwml.entity.HWML;

public class HWMLUtil {
	
	public static HWML getHWMLByID(String id)
	{
		for (HWML hwobj : HWML_XMLParser.HWList) {
			if(hwobj.getDevice_id().endsWith(id))
			{
				return hwobj;
			}
		}
		return null;
	}

}
