package cap.model.handler;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cap.model.entity.ApplicationData;
import cap.model.entity.BehaviouralElements;
import cap.model.entity.Exit;
import cap.model.entity.Mode;
import cap.model.entity.Port;
import cap.model.entity.SAElement;
import cap.model.entity.SoftwareArchitecture;
import cap.sim.utility.XMLUtil;

public class XMLParser {

	private String xmlFile;

	public XMLParser(String xmlFile) {
		// TODO Auto-generated constructor stub
		this.xmlFile = xmlFile;
	}

	public SoftwareArchitecture loadDataFromXML() {
		Document doc = XMLUtil.getDocument(xmlFile);
		if (doc == null)
			return null;

		FillSoftwareArch(doc);

		return null;
	}

	private void FillSoftwareArch(Document doc) {

		SoftwareArchitecture sa = SoftwareArchitecture
				.getSoftwareArchitechure();
		sa.getElements().clear();
		// get list of elements as Node
		NodeList nList = doc.getElementsByTagName("SAElements");

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// create new SAElement
				SAElement element = new SAElement();
				// fill attributes of elements
				element.setType(eElement.getAttribute("xsi:type"));
				element.setName(eElement.getAttribute("name"));
				element.setSource(eElement.getAttribute("source"));
				element.setTarget(eElement.getAttribute("target"));

				sa.getElements().add(element);

				if (eElement.getElementsByTagName("ports") != null
						&& eElement.getElementsByTagName("ports").getLength() > 0)
					addPorts(eElement.getElementsByTagName("ports"),element);
				if (eElement.getElementsByTagName("modes") != null
						&& eElement.getElementsByTagName("modes").getLength() > 0)
					addModes(eElement.getElementsByTagName("modes"),element);
				
				if (eElement.getElementsByTagName("applicationData") != null
						&& eElement.getElementsByTagName("applicationData").getLength() > 0)
					addAppData(eElement.getElementsByTagName("applicationData"),element);

			}

		}
	}

	private void addAppData(NodeList apps, SAElement element) {
		// TODO Auto-generated method stub
		for (int temp = 0; temp < apps.getLength(); temp++) {

			Node nNode = apps.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// create new Port
				ApplicationData e = new ApplicationData();
				e.setType(eElement.getAttribute("xsi:type"));
				e.setDataName(eElement.getAttribute("dataName"));
				e.setDataType(eElement.getAttribute("dataType"));
				e.setValue(eElement.getAttribute("value"));
				
				element.getApps().add(e);
				
			}
		}
	}

	private void addModes(NodeList modes, SAElement element) {
		
		for (int temp = 0; temp < modes.getLength(); temp++) {

			Node nNode = modes.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// create new Mode
				Mode mode = new Mode();
				mode.setName(eElement.getAttribute("name"));
				mode.setType(eElement.getAttribute("xsi:type"));				
				element.getModes().add(mode);
				
				if (eElement.getElementsByTagName("behaviouralElements") != null
						&& eElement.getElementsByTagName("behaviouralElements").getLength() > 0)
					addBehaviouralElements(eElement.getElementsByTagName("behaviouralElements"),mode);
				
				if (eElement.getElementsByTagName("exits") != null
						&& eElement.getElementsByTagName("exits").getLength() > 0)
					addExits(eElement.getElementsByTagName("exits"),mode);
				
				if (eElement.getElementsByTagName("entries") != null
						&& eElement.getElementsByTagName("entries").getLength() > 0)
					addEntries(eElement.getElementsByTagName("entries"),mode);
			}
		}
	}

	private void addEntries(NodeList entryls, Mode mode) {
		// TODO Auto-generated method stub
		for (int temp = 0; temp < entryls.getLength(); temp++) {

			Node nNode = entryls.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// create new Port
				Exit e = new Exit();
				e.setName(eElement.getAttribute("name"));
				e.setIncoming(eElement.getAttribute("incoming"));
				e.setTargetMode(eElement.getAttribute("targetMode"));
				mode.getExites().add(e);				
			}
		}
	}

	private void addExits(NodeList existls, Mode mode) {
		// TODO Auto-generated method stub
		
		for (int temp = 0; temp < existls.getLength(); temp++) {

			Node nNode = existls.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// create new Port
				Exit e = new Exit();
				e.setName(eElement.getAttribute("name"));
				e.setTargetMode(eElement.getAttribute("targetMode"));
				
				mode.getExites().add(e);				
			}
		}
		
	}

	private void addBehaviouralElements(NodeList belement,
			Mode mode) {
		
		for (int temp = 0; temp < belement.getLength(); temp++) {

			Node nNode = belement.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// create new Port
				BehaviouralElements e = new BehaviouralElements();
				e.setName(eElement.getAttribute("name"));
				e.setType(eElement.getAttribute("xsi:type"));
				e.setOutgoing(eElement.getAttribute("outgoing"));
				e.setIncoming(eElement.getAttribute("incoming"));
				e.setSource(eElement.getAttribute("source"));
				e.setTarget(eElement.getAttribute("target"));
				e.setFromMessagePorts(eElement.getAttribute("fromMessagePorts"));
				e.setCondition(eElement.getAttribute("condition"));
				e.setDataRecipient(eElement.getAttribute("dataRecipient"));
				// new 
				e.setDataRecipientName(eElement.getAttribute("dataRecipientName"));
				e.setCyclic(eElement.getAttribute("cyclic"));
				e.setPeriod(eElement.getAttribute("period"));
				e.setReceiverName(eElement.getAttribute("receiverName"));
				e.setToMessagePorts(eElement.getAttribute("toMessagePorts"));
				e.setTimer(eElement.getAttribute("timer"));
				if(e.getType().equals("components:MulticastSendMessage"))
				{
					e.getReceiverNames().addAll( addReceiverNames(eElement.getElementsByTagName("receiverNames"),e));
				}				
				mode.getBehaviouralElements().add(e);				
			}
		}
	}

	private List<String> addReceiverNames(NodeList names,
			BehaviouralElements e) {
		// TODO Auto-generated method stub
		List<String> ls=new ArrayList<String>();
		for (int i=0;i<names.getLength();i++) {
			Node node=names.item(i);
			ls.add(node.getTextContent());
		}
		return ls;
	}

	private void addPorts(NodeList ports, SAElement element) {
		
		for (int temp = 0; temp < ports.getLength(); temp++) {

			Node nNode = ports.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// create new Port
				Port port = new Port();
				port.setName(eElement.getAttribute("name"));
				port.setType(eElement.getAttribute("xsi:type"));
				
				element.getPorts().add(port);
				
			}
		}
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

}
