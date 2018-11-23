package cap.sim.utility;

import cap.model.handler.HWML_XMLParser;
import cap.model.handler.SPML_XMLParser;

import com.hwml.entity.HWML;
import com.spml.entity.SPML;

public class SPMLUtil {

	public static SPML getSPMLByID(String id)
	{
		for (SPML spobj : SPML_XMLParser.SPMLList) {
			if(spobj.getDevice_id().endsWith(id))
			{
				return spobj;
			}
		}
		return null;
	}
}
