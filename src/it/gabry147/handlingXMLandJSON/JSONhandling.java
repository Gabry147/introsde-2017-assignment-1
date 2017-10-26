package it.gabry147.handlingXMLandJSON;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import it.gabry147.people.generated.People;

public class JSONhandling {
	
	/**
	 * Generate an XML file from a People entity
	 * @param p The People entity to marshal
	 * @param filename The name of the XML file to generate
	 */
	public static void generateJSON(People p, String filename) {
		// Jackson Object Mapper 
		ObjectMapper mapper = new ObjectMapper();
		
		// Adding the Jackson Module to process JAXB annotations
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        
		// configure as necessary
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

        String result = null;
		try {
			result = mapper.writeValueAsString(p);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        System.out.println("JSON preview: \n\n"+result+"\n\n");
        try {
			mapper.writeValue(new File(filename), p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	public static void main(String args[]) {
		//this is how the xml db was created
		//new XMLhandling().generateXML("People.xml");
		
		//task 5
		System.out.println("Task #5: Extracting data from ThreeNewPeople.xml and saving it to classes");		
		People people = XMLhandling.extractPeopleXML("ThreeNewPeople.xml");
		
		//task 6
		System.out.println("Task #6: Generating JSON representation from Java classes extracted");
		JSONhandling.generateJSON(people, "TheSameThreePeople.json");
	}

}
