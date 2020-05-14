package com.xuecheng.framework.utils;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
 
public class XmlUtil {
 
	private XmlUtil() {
		// Not instantiable
	}
 
	@SuppressWarnings("unchecked")
	public static <T> T xml2Java(String xml, Class<T> clazz) throws JAXBException {
		T t = null;
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		t = (T) unmarshaller.unmarshal(new StringReader(xml));
		return t;
	}
 
	public static String java2Xml(Object obj, String encoding) throws JAXBException {
		String result = null;
 
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
 
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
 
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		result = writer.toString();
		return result;
	}
 
	public static String getNodeText(Document document, String xpath) {
		String nodeText = "";
		Node node = document.selectSingleNode(xpath);
		if (node != null) {
			nodeText = node.getText();
		}
		return nodeText;
	}
 
	public static String getNodeText(String xml, String xPath)
			throws DocumentException, UnsupportedEncodingException, IOException {
		String nodeText = null;
		try (InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"))) {
 
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(is);
			nodeText = XmlUtil.getNodeText(document, xPath);
		}
		return nodeText;
	}
 
	public static String getElementText(Element parentElement, String elementName) {
		return parentElement.element(elementName).getText();
	}
 
	public static String getRootNodeName(String xmlData) {
		String rootNodeName = null;
		if (isNotEmpty(xmlData)) {
			int beginIndex = xmlData.indexOf(">") + 1;
			rootNodeName = xmlData.substring(beginIndex);
			if (isNotEmpty(rootNodeName)) {
				// 判断是否有xml文件头
				if (rootNodeName.indexOf("?") > 0) {
					// 第二个<的位置
					int secondGlndex = xmlData.indexOf("<", beginIndex);
					// 第二个>的位置
					int secondLIndex = xmlData.indexOf(">", beginIndex);
					if (secondGlndex < secondLIndex) {
						rootNodeName = xmlData.substring(secondGlndex + 1, secondLIndex);
					}
				} else {
					// 第二个<的位置
					int secondGlndex = xmlData.indexOf("<");
					// 第二个>的位置
					int secondLIndex = xmlData.indexOf(">");
					if (secondGlndex < secondLIndex) {
						rootNodeName = rootNodeName.substring(secondLIndex);
					}
				}
			}
			String[] words = rootNodeName.split("\\s+");
			rootNodeName = words[0];
		}
		return rootNodeName;
	}
 
	public static String formatXml(String xmlStr,String encoding) throws DocumentException, IOException{
		Document document = null;
		document= DocumentHelper.parseText(xmlStr);
		//格式化输出格式
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		StringWriter writer = new StringWriter();
		//格式化输出流
		XMLWriter xmlWriter = new XMLWriter(writer,format);
		//将document写入到输出流
		xmlWriter.write(document);
		xmlWriter.close();
		return writer.toString();
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() < 1;
	}
 
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
}