package cap.model.handler;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cap.sim.utility.XMLUtil;

import com.hwml.entity.HWML;
import com.spml.entity.SPML;

public class SPML_XMLParser {

	private String xmlFile;
	public static ArrayList<SPML>SPMLList=new ArrayList<SPML>();
	
	public SPML_XMLParser(String xmlFile) {
		// TODO Auto-generated constructor stub
		this.xmlFile = xmlFile;
		
	}

	public void loadDataFromXML() {
		Document doc = XMLUtil.getDocument(xmlFile);
		if (doc == null)
			return ;

		FillHWComponent(doc);

		
	}
	
	private void FillHWComponent(Document doc) {
		
		SPML_XMLParser.SPMLList.clear();
		
		NodeList nList = doc.getElementsByTagName("device");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				SPML spml=new SPML();
				spml.setDevice_Name(eElement.getElementsByTagName("device_name").item(0).getTextContent());
				spml.setDevice_id(eElement.getElementsByTagName("device_id").item(0).getTextContent());
				spml.setDevice_type(eElement.getElementsByTagName("device_type").item(0).getTextContent());
				spml.setDevice_longitude(eElement.getElementsByTagName("device_longitude").item(0).getTextContent());				
				spml.setDevice_latitude(eElement.getElementsByTagName("device_latitude").item(0).getTextContent());
				
				spml.setDevice_elevation(eElement.getElementsByTagName("device_elevation").item(0).getTextContent());
				spml.setDevice_radius(eElement.getElementsByTagName("device_radius").item(0).getTextContent());
				spml.setDevice_hide(eElement.getElementsByTagName("device_hide").item(0).getTextContent());
				
				spml.setDevice_draw_battery(eElement.getElementsByTagName("device_draw_battery").item(0).getTextContent());
				spml.setDevice_sensor_unit_radius(eElement.getElementsByTagName("device_sensor_unit_radius").item(0).getTextContent());
								
				SPML_XMLParser.SPMLList.add(spml);
			}
		}
	}
	
	public static void main(String[] args) {
		SPML_XMLParser sp=new SPML_XMLParser("files/SPML.xml");
		sp.loadDataFromXML();
		System.out.println(sp.SPMLList.size());
	}
	
}
