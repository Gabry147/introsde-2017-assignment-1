# SDE 2017 - First Assignment Delivery
#### Gabriele Scarton
#### gabriele.scarton@studenti.unitn.it
This is the first assignment for [IntroSDE course] at [Unitn].


## The project
The goal of the assignment is to generateing Java Objects from a XML schema, reading from/writing to them XML and JSON document and querying an XML document. The marshalling and unmarshilling is provided by JAXB for XML and [Jackson] for JSON. The XML document are queried using XPath 1.0.

## The code
There are three packages, one for XML utilities, one for XPath and one for un/marshalling data.
 - **DateFormatter.java** from package **XMLformatter** is a utility class that contains two methods: one for converting a Date in XMLGregorianCalendar and viceversa the other.
- **XPathQueries.java** from **xPath** implements all methods that use XPath. There are five auxiliary private functions and five public functions:
    - generateXPathCompiler() creates an access to XPath library environment
    - getDocumentFromXML(documentName) parses a XML file in a Document class
    - getSingleXPathResult(xPathQuery) compiles a query, retrieves the XML document, expects a text Node as result and return the result as String
    - getElementAsString(xPathQuery) compiles a query, retrieves the XML document, expects a Node as result and return the Node in String representation using node.getTextContent() which automatically indents the text contents inside the node considering the XML tree with root as the resulting node. 
    - getElementListAsString(xPathQuery) compiles a query, retrieves the XML document, expects a NodeList as result, gets the String representation for every node and appends all representation in a single result String.
    - **getActivityPlace(personID)** and **getActivityDescription(personID)** create the XPath query using the personID and retrieve the result using getSingleXPathResult(xPathQuery).
    - **getActivity(personID)** create the XPath query using the personID and retrieve the result using getElementAsString(xPathQuery). It's used for task #2.
    - **getAllPeople()** uses the "/people" query and getElementAsString(xPathQuery) function to get all Person inside People. It's used for task #1.
    - **getPersonByActivityStartdate(date, operator)** accepts a date in YYYY/MM/DD String form and a numerical operator, than it creates the query. Since XPath 1.0 doesn't have a date comparator, the input date and the date in xml are treated as string, all non numerical character are removed using XPath function [translate()] and than they are compared as number. This is possible assuming a correct input date and exploiting the fact that XMLGregorianCalendar structure orders the digits in a proper order and all zeros are not trimmed.
- **XMLhandling** from **handlingXMLandJSON** has a field, an auxiliary private function and two public functions:
    - the integer personToGenerate set how many Person entities has a People entity.
    - generatePersonList() generates <personToGenerate> Person entities with a personal ActivityPreference, all filled with fake data. Return the list of person saved in a People entity.
    - **generatePeopleXML(filename)** uses a JAXB marshaller (using the generated package as context) to save the data given by generatePersonList() in a new XML file named <filename>. It's used for task #4.
    - **extractPeopleXML(filename)** uses a JAXB unmarshaller (using the generated package as context) to extract data from a given XML file named <filename>. The result are return as a People entity. It's used for task #5 (it's called in JSONhandling main function)
- **JSONhandling** from **handlingXMLandJSON** has a single public function named **generateJSON(People p, filename)** which takes a People entity, uses a Jackson mapper to extract the data as a JSON string and than saves the JSON in a new file called <filename>. It's used for task #6.


## Execution
> In order to run the code as it's intended, you need to have installed [Ant]

The console command "ant" compile the code and install the libraries needed. You can also use "ant compile"
```sh
$ ant
$ ant compile
```
#
In order to run all the classes, you can execute "ant execute.evaluation". The command itself does nothing, but it calls all single class execution targets, which are subsequently commented.
```sh
$ ant execute.evaluation
```
#
If you want to try only some features, every class with main has its own command. Every command will also compile the code and download the dependencies if needed. All the results are printed in the standard output.
#
```sh
$ ant execute.XPathQueries
$ ant execute.XMLhandling
$ ant execute.JSONhandling
```
Every targets call the main() of the corrisponding class. The main of XPathQueries executes tasks #1 #2 #3, the main of XMLhandling executes task #4 generating ThreeNewPeople.xml and the main of JSON handling executes tasks #5 #6, generating TheSameThreePeople.json. Since task #5 requires ThreeNewPeople.xml, execute.JSONhandling depends to execute.XMLhandling target.

## Additional notes
  - The code is Javadoc commented. The Javadoc documentation can be created in a newly created "doc" folder executing the following [Ant] command. Since JAXB generates the classes with Javadoc comments, the command depends on "generate" target in order to create the documentation for the generated classes too.
```sh
$ ant doc
```
  - The target ant.clean deletes the build folder and the two file generated, in order to have a fresh start for executinf single class targets. This doesn't clean the doc folder, as the Javadoc generation of ant.doc overrides all previous Javadoc for the same classes. This mean that if a class is deleted, its Javadoc must be deleted by hand.

[//]: # (Even if is not specifically requested by the site, thanks to dillinger.io for its md editor)

[Jackson]: <https://github.com/FasterXML/jackson>
[Ant]: <http://ant.apache.org/>
[IntroSDE course]: <https://sites.google.com/a/unitn.it/introsde_2017-18/>
[Unitn]: <unitn.it>
[translate()]: <https://developer.mozilla.org/en-US/docs/Web/XPath/Functions/translate>

