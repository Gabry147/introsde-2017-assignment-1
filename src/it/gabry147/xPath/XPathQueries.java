package it.gabry147.xPath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathQueries {
	/**
	 * Create an access to XPath environment
	 * @return an access to XPath environment
	 */
	private static XPath generateXPathCompiler() {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		return xpath;
	}
	
	/**
	 * Get a Document instance from an XML file
	 * @param documentName the name of the file
	 * @return the Document representation of the file
	 */
	private static Document getDocumentFromXML(String documentName) {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		//enable support for namespace
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		try {
			//create document builder
			builder = domFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			//build the document
			doc = builder.parse(documentName);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * Auxiliary function, given an query, search for it in People.xml and expects a single result
	 * @param xPathQuery the query string
	 * @return the result of the query in string representation
	 */
	private static String getSingleXPathResult(String xPathQuery) {
		System.out.println("Query: "+xPathQuery);
		XPathExpression expr = null;
		try {
			//generate compiler
			expr = generateXPathCompiler().compile(xPathQuery);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Object result = null;
		try {
			//evaluate query. Set result as a single node
			result = expr.evaluate(getDocumentFromXML("People.xml"), XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Node node = (Node) result;
		//return the string representation of the node
		return node.getNodeValue();
	}
	
	/**
	 * Using getSingleXPathResult() and the ID of the person, return the activity place
	 * @param personID the ID of the person
	 * @return the name of the activity place as String
	 */
	public static String getActivityPlace(int personID) {
		return getSingleXPathResult(
				"/people/person[@id="+personID+"]/activitypreference/place/text()"
				);
	}
	
	/**
	 * Using getSingleXPathResult() and the ID of the person, return the activity description
	 * @param personID the ID of the person
	 * @return the activity description as String
	 */
	public static String getActivityDescription(int personID) {
		return getSingleXPathResult(
				"/people/person[@id="+personID+"]/activitypreference/description/text()"
				);
	}
	
	/**
	 * Auxiliary function. Given a query string, return the String representation of a single XML element in People.xml matching the query
	 * @param xPathQuery The query String
	 * @return The String representation of the XML element
	 */
	private static String getElementAsString(String xPathQuery) {
		System.out.println("Query: "+xPathQuery);
		//generate a compiled query
		XPathExpression expr = null;
		try {
			expr = generateXPathCompiler().compile(xPathQuery);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		Object result = null;
		try {
			//evaluate query. Set result as a Node
			result = expr.evaluate(getDocumentFromXML("People.xml"), XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Node node = (Node) result;
		return node.getTextContent();
	}
	
	/**
	 * Using getElementAsString() and the ID of the person, return the activity
	 * @param personID the ID of the person
	 * @return the activity represented as String
	 */
	public static String getActivity(int personID) {
		return getElementAsString("/people/person[@id="+personID+"]/activitypreference");
		
	}
	
	/**
	 * Using getElementAsString(), return all Person
	 * @return the string representation of the list of Person
	 */
	public static String getAllPeople() {
		return getElementAsString("/people");
		
	}
	
	/**
	 * Auxiliary function. Given a query as string, it expects a Nodeset as result.
	 * The nodeset is represented in String form.
	 * @param xPathQuery The query string
	 * @return The result Nodeset in string representation
	 */
	private static String getElementListAsString(String xPathQuery) {
		System.out.println("Query: "+xPathQuery);
		//generate a compiled query
		XPathExpression expr = null;
		try {
			expr = generateXPathCompiler().compile(xPathQuery);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		Object result = null;
		try {
			// evaluate query. Set result as a Nodeset
			result = expr.evaluate(getDocumentFromXML("People.xml"), XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nodelist = (NodeList) result;
		//initializing result string
		String resultString = "";
		//printing number of elements found
		System.out.println("LEN:"+nodelist.getLength());
		for(int i=0; i<nodelist.getLength(); i++) {
			Node node = nodelist.item(i);
			//save the string representation of every element in the resulting string
			resultString = resultString + node.getTextContent();
		}
		return resultString;
	}
	
	/**
	 * Auxiliary function. Given an operator and a time in String format, create a query.
	 * Use getElementListAsString() function to return a list of Person matching the query
	 * @param operator a numeric operator such < > =
	 * @param date a YYYY-MM-DD String representation of a time
	 * @return The String representation of the resulting Person list
	 */
	public static String getPersonByActivityStartdate(String date, String operator) {
		String[] dateParameters = date.split("-");
		if(dateParameters.length != 3) return null;
		List<Integer> numbers = new ArrayList<Integer>();
		for(String number : dateParameters) {
			numbers.add(Integer.parseInt(number));
		}
		//input YYYY/DD(Jan=1, Dec=12)/MM, gregorian calendar YYYY/MM(Jan=0, Dec=11)/DD
		GregorianCalendar gc = new GregorianCalendar(numbers.get(0), numbers.get(2)-1, numbers.get(1));
		XMLGregorianCalendar xmlCalendar = null;
		try {
			//create XML calendar
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getElementListAsString(
				"/people/person["
				//substitute all non numeric character with nothing ( = deleting them)
				+ "translate(activitypreference/startdate, '-T:.+', '')"
				//using a numeric operator, XPATH 1.0 doesn't have String comparison
				//this is possible because with translate the string are composed only by number, 
				//ordered in the proper order thanks to the GregorianCalendar structure
				+ operator 
				+ "translate('"+xmlCalendar+"', '-T:.+', '')"
				+ "]"
				);
		
	}
	
	public static void main(String args[]) {
		System.out.println("Task #1: List of all Person");
		System.out.println(XPathQueries.getAllPeople());
		
		System.out.println("Task #2: Activity of person with ID=5");
		System.out.println(XPathQueries.getActivity(5));
		
		System.out.println("Task #3: List of person with Startdate from Activity > 2017-13-10");
		System.out.println(XPathQueries.getPersonByActivityStartdate("2017-13-10", ">"));
	}

}
