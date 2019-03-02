package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchPersonIdException;

import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author André Augusto, 90704
 * @author Pedro Lera, 87695
 */

/**
 * School implementation.
 * @see <a href="https://fenix.tecnico.ulisboa.pt/downloadFile/563568428771583/document.pdf">https://fenix.tecnico.ulisboa.pt/downloadFile/563568428771583/document.pdf</a>
 */
public class School implements java.io.Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    private Map<Integer, Person> _persons;
    private List<Course> _coursesList;

    /* package */ School(){
	    _persons = new HashMap<>();
	    _coursesList = new ArrayList<>();
    }

	/**
	 * Imports the input file
	 * @param filename import file's name
	 * @throws BadEntryException Error parsing import file
	 * @throws IOException Error parsing import file
	 */
    /* package */ void importFile(String filename) throws IOException, BadEntryException {
        Parser parser = new Parser(this);
        parser.parseFile(filename);
    }

	/**
	 * Get the Person with the id personId
	 * @param personId numerical Id of a person
	 * @return person with Id = personId
	 * @throws NoSuchPersonIdException when the person with Id = personId does not exist
	 */
    /* package */ Person getPerson(int personId) throws NoSuchPersonIdException{
        Person person;

    	if (_persons.size() == 0){
            throw new NoSuchPersonIdException(personId);
	    }
		else {
		    person = _persons.get(personId);
		    if (person == null)
			    throw new NoSuchPersonIdException(personId);
			else return person;
	    }
    }

	/**
	 * Sort by disciplines' name in Students. Sort Courses and then Disciplines by their name in this order in Teachers. Employees are sorted by default.
	 * @param person object of Person type to sort
	 */
    /* package */ void sortPerson(Person person){
    	if (person instanceof Student){
    		((Student) person).getDisciplinesList().sort(new Discipline.CompareByName());
	    }

	    else if (person instanceof Teacher){
	    	((Teacher) person).getDisciplinesList().sort((disc1, disc2) -> {
			    if ((disc1.getCourse().getName().equals(disc2.getCourse().getName())))
				    return disc1.getName().compareTo(disc2.getName());

			    return (disc1.getCourse().getName().compareTo(disc2.getCourse().getName()));
		    });
	    }
    }

	/**
	 * Search all Persons with a certain substring in the corresponding name
	 * @param name of the person to search
	 * @return person in String format
	 */
    /* package */ String searchPerson(String name){
	    StringBuilder finalString = new StringBuilder();
    	List<Person> listSameNames = new ArrayList<>();
    	Collection<Person> listPersons = _persons.values();

    	for (Person p: listPersons) {
		    if (p.getName().contains(name)) {
			    sortPerson(p);
			    listSameNames.add(p);
		    }
	    }

	    listSameNames.sort(new Person.CompareByName());

	    for (Person p: listSameNames)
		    finalString.append(p.toString());

	    return finalString.toString();
    }

	/**
	 * Adds a Person to the school list of Persons
	 * @param person Person to add
	 */
    /* package */ void addPerson(Person person){
        _persons.put(person.getId(), person);
    }

	/**
	 * Get all users
	 * @return All Users in String Format
	 */
    /* package */ String getAllUsers(){
	    StringBuilder finalString = new StringBuilder();
	    Collection<Person> person = _persons.values();
	    List<Person> listPerson = new ArrayList<>(person);

	    for (Person p: listPerson){
		    sortPerson(p);
	    }

	   listPerson.sort(new Person.CompareById());

	    for(Person p: listPerson){
		    finalString.append(p.toString());
	    }
	    return finalString.toString();
    }

	/**
	 * Show all Students from a Discipline
	 * @param person Teacher who lectures a Discipline
	 * @param name  name of the discipline
	 * @return  All Students in a Discipline
	 * @throws NoSuchDisciplineIdException
	 */
    /* package */ String showDisciplineStudents(Person person, String name) throws NoSuchDisciplineIdException {
	    StringBuilder finalString = new StringBuilder();
	    List<Student> disciplineStudents = ((Teacher)person).getStudentsOfDiscipline(name);

	    for (Person p: disciplineStudents){
		    sortPerson(p);
	    }

	    disciplineStudents.sort(new Person.CompareById());

	    for(Person p: disciplineStudents){
		    finalString.append(p.toString());
	    }

	    return finalString.toString();
    }

	/**
	 * Parse the course
	 * @param courseName courses' name
	 * @return Course with courseName
	 */
    /* package */ Course parseCourse(String courseName){
        for (Course c: _coursesList) {
            if (c.getName().equals(courseName))
                return c;
        }

        Course c1 = new Course(courseName);
        _coursesList.add(c1);
        return c1;
    }
}