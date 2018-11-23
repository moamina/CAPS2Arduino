package cap.model.handler;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hwml.entity.HWML;

import cap.model.entity.SoftwareArchitecture;
import cap.sim.utility.XMLUtil;

public class HWML_XMLParser {
	
	private String xmlFile;
	public static ArrayList<HWML>HWList=new ArrayList<HWML>();
	
	public HWML_XMLParser(String xmlFile) {
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
		
		HWML_XMLParser.HWList.clear();
		
		NodeList nList = doc.getElementsByTagName("device");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				HWML hwml=new HWML();
				hwml.setDevice_Name(eElement.getElementsByTagName("device_name").item(0).getTextContent());
				hwml.setDevice_id(eElement.getElementsByTagName("device_id").item(0).getTextContent());
				hwml.setCurrent_radio_name(eElement.getElementsByTagName("current_radio_name").item(0).getTextContent());
				hwml.setRadio_standard(eElement.getElementsByTagName("radio_standard").item(0).getTextContent());				
				hwml.setRadio_my(eElement.getElementsByTagName("radio_my").item(0).getTextContent());
				
				hwml.setRadio_channel(eElement.getElementsByTagName("radio_channel").item(0).getTextContent());
				hwml.setRadio_network_id(eElement.getElementsByTagName("radio_network_id").item(0).getTextContent());
				hwml.setRadio_radius(eElement.getElementsByTagName("radio_radius").item(0).getTextContent());
				
				hwml.setRadio_etx(eElement.getElementsByTagName("radio_etx").item(0).getTextContent());
				hwml.setRadio_erx(eElement.getElementsByTagName("radio_erx").item(0).getTextContent());
				hwml.setRadio_esleep(eElement.getElementsByTagName("radio_esleep").item(0).getTextContent());
				hwml.setRadio_elisten(eElement.getElementsByTagName("radio_elisten").item(0).getTextContent());
				hwml.setRadio_data_rate(eElement.getElementsByTagName("radio_data_rate").item(0).getTextContent());
				
				HWML_XMLParser.HWList.add(hwml);
			}
		}
	}
	
	public static void main(String[] args) {
		HWML_XMLParser xx=new HWML_XMLParser("files/HWML.xml");
		xx.loadDataFromXML();
		
		System.out.println(xx.HWList.size());
	}
}
