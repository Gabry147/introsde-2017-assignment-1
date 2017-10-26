package it.gabry147.handlingXMLandJSON;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import it.gabry147.XMLformatter.DateFormatter;
import it.gabry147.people.generated.ActivityPreference;
import it.gabry147.people.generated.People;
import it.gabry147.people.generated.Person;

public class XMLhandling {
	/**
	 * The number of person entities to generate
	 */
	private int personToGenerate;
	
	/**
	 * Generate the class with this.personToGenerate = 20
	 */
	public XMLhandling() {
		this.personToGenerate = 20;
	}
	
	/**
	 * Generate the class with a custom value of this.personToGenerate
	 * @param i The integer to set to this.personToGenerate
	 */
	public XMLhandling(int i) {
		this.personToGenerate = i;
	}
	
	/**
	 * Generate a People entity and populate it with a list of Person
	 * @return the People instance generated
	 */
	private People generatePersonList() {
		People person_list = new People();
		
		//base date for generating person (about 31/32 years ago)
		Long baseDateBirth = System.currentTimeMillis() - (long) Math.pow(10, 12);
		//base date for activity (about 1/2 days ago)
		Long baseDateStartdate = System.currentTimeMillis() - (long) Math.pow(10, 8);
		
		for(int i=1; i<personToGenerate+1; i++) {
			//generation of a person
			Person p = new Person();
			p.setId(i);
			p.setFirstname("Jhon"+i);
			p.setLastname("Doe"+i);
			
			//every date is separated by an offset of more or less 116 days
			Long offset1 = (long)(i*Math.pow(10, 10));
			Date d1 = new Date(baseDateBirth-offset1);
			p.setBirthdate(DateFormatter.fromDateToXMLGregorianCalendar(d1));
			
			//generation of an activity preference
			ActivityPreference ap = new ActivityPreference();
			int activityPreferenceID = i+100;
			ap.setId(activityPreferenceID);
			ap.setName("ActivityName"+activityPreferenceID);
			ap.setPlace("Place"+activityPreferenceID);
			ap.setDescription(activityPreferenceID+" Lorem ipsum dolor sit amet");
			
			//every startdate is separated by an offset of more or less 1 days
			Long offset2 = (long)(i*Math.pow(10, 8));
			Date d2 = new Date(baseDateStartdate-offset2);
			ap.setStartdate(DateFormatter.fromDateToXMLGregorianCalendar(d2));
			
			//set the ActivityPreference to the Person
			p.setActivitypreference(ap);
			
			//add the person to the list
			person_list.getPerson().add(p);
		}
		return person_list;
	}
	
	/**
	 * Generate a People entity and save it in a file
	 * @param filename the name of the newly created file
	 */
	public void generatePeopleXML(String filename) {
		try {

			JAXBContext jaxbContext = JAXBContext.newInstance("it.gabry147.people.generated");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", true);
			System.out.println("Marshalling data to XML...");
			marshaller.marshal(this.generatePersonList(), new FileOutputStream(filename));

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	/**
	 * Generate a People entity from an xml file
	 * @param filename the file to unmarshal
	 * @return The People entity representing the content of the file
	 */
	public static People extractPeopleXML(String filename) {
		People people = null;
		try {
			//create API entry based on people
			JAXBContext jc = JAXBContext.newInstance(People.class);
			//create unmarshaller
	        Unmarshaller um = jc.createUnmarshaller();
	        System.out.println("Unmarshalling from XML...");
	        people = (People) um.unmarshal(new FileReader(filename));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return people;
	}
	
	public static void main(String args[]) {
		//this is how the xml db was created
		//new XMLhandling().generatePeopleXML("People.xml");
		
		//task 4
		System.out.println("Task #4: Creating a People with 3 entity and marshalling them to ThreeNewPeople.xml");
		new XMLhandling(3).generatePeopleXML("ThreeNewPeople.xml");
	}

}
