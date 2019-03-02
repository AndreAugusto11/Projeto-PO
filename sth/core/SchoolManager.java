package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.DuplicateProjectIdException;
import sth.core.exception.DuplicateSurveyIdException;
import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.ImportFileException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchPersonIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.NonEmptySurveyIdException;
import sth.core.exception.OpeningSurveyIdException;
import sth.core.exception.SurveyFinishedIdException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * The façade class.
 */
public class SchoolManager {

	private School _school;
	private Person _person;
	private String _fileName;

	public SchoolManager() {
		_school = new School();
		_fileName = "";
	}

	/**
	 * @param datafile name of the file
	 * @throws ImportFileException
	 * @throws InvalidCourseSelectionException
	 */
	public void importFile(String datafile) throws ImportFileException {
		try {
			_school.importFile(datafile);
		} catch (IOException | BadEntryException e) {
			throw new ImportFileException(e);
		}
	}

	public Person getLoggedUser() {
		return _person;
	}

	public void open(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException, NoSuchPersonIdException {
		_fileName = fileName;
		ObjectInputStream loader = new ObjectInputStream(new FileInputStream((_fileName)));
		School _schoolAux = (School) loader.readObject();
		_person = _schoolAux.getPerson(_person.getId());
		_school = _schoolAux;
		loader.close();
	}

	public void save(String fileName) throws IOException {
		ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream((fileName)));
		saver.writeObject(_school);
		saver.close();
		_fileName = fileName;
	}

	public void save() throws IOException {
		save(_fileName);
	}

	public boolean fileDoesNotExist() {
		return (_fileName.equals(""));
	}

	/**
	 * Do the login of the user with the given identifier.
	 *
	 * @param id identifier of the user to login
	 * @throws NoSuchPersonIdException if there is no users with the given identifier
	 */
	public void login(int id) throws NoSuchPersonIdException {
		_person = _school.getPerson(id);
	}

	/**
	 * @return true when the currently logged in person is an administrative
	 */
	public boolean isLoggedUserAdministrative() {
		return _person instanceof Employee;
	}

	/**
	 * @return true when the currently logged in person is a professor
	 */
	public boolean isLoggedUserProfessor() {
		return _person instanceof Teacher;
	}

	/**
	 * @return true when the currently logged in person is a student
	 */
	public boolean isLoggedUserStudent() {
		return _person instanceof Student;
	}

	/**
	 * @return true when the currently logged in person is a representative
	 */
	public boolean isLoggedUserRepresentative() {
		if (_person instanceof Student) {
			return ((Student) _person).isRepresentative();
		}
		return false;
	}

	/**
	 * @return a String with the information about the currently logged in person
	 */
	public String showPerson() {
		_school.sortPerson(_person);
		return _person.toString();
	}

	/**
	 * Replaces the currently logged in person's phone number
	 *
	 * @param newPhoneNumber the new number to replace
	 * @return a String with the information updated about the currently logged in person
	 */
	public String changePhoneNumber(Integer newPhoneNumber) {
		_person.changePhoneNumber(newPhoneNumber);
		return _person.toString();
	}

	/**
	 * Get all users in _school ordered by Id
	 *
	 * @return a String with the information about all users in _school
	 */
	public String showAllPersons() {
		return _school.getAllUsers();
	}

	/**
	 * Searches all users in _school that have a determinate substring in their names
	 *
	 * @param string substring to check
	 * @return a String with the information about the users that have string in their names
	 */
	public String searchPerson(String string) {
		return _school.searchPerson(string);
	}

	/**
	 * Get all users in a certain discipline if it exists and if the user teaches that Discipline
	 *
	 * @param disciplineName Discipline's name
	 * @return String with the information about the users in Discipline disciplineName
	 * @throws NoSuchDisciplineIdException if there is no discipline with disciplineName name in _person's disciplines list
	 */
	public String showDisciplineStudents(String disciplineName) throws NoSuchDisciplineIdException {
		return _school.showDisciplineStudents(_person, disciplineName);
	}

	public void createProject(String discName, String projName) throws NoSuchDisciplineIdException, DuplicateProjectIdException {
		((Teacher) _person).createProject(discName, projName);
	}

	public void closeProject(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, OpeningSurveyIdException {
		((Teacher) _person).closeProject(discName, projName);
	}

	public String showProjectSubmissions(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
		return ((Teacher) _person).showProjectSubmissions(discName, projName);
	}

	public void deliverProject(String discName, String projName, String submission) throws NoSuchProjectIdException, NoSuchDisciplineIdException {
		((Student) _person).submitProject(discName, projName, submission);
	}

	public void createSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateSurveyIdException {
		((Student) _person).createSurvey(discName, projName);
	}

	public void cancelSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, NonEmptySurveyIdException, SurveyFinishedIdException {
		((Student) _person).cancelSurvey(discName, projName);
	}

	public void openSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, OpeningSurveyIdException {
		((Student) _person).openSurvey(discName, projName);
	}

	public void closeSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, ClosingSurveyIdException {
		((Student) _person).closeSurvey(discName, projName);
	}

	public void finishSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, FinishingSurveyIdException {
		((Student) _person).finishSurvey(discName, projName);
	}

	public void answerSurvey(String discName, String projName, int hours, String comment) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException {
		((Student) _person).answerSurvey(discName, projName, hours, comment);
	}

	public String showSurveyResults(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException {
		if (isLoggedUserProfessor())
			return ((Teacher) _person).showSurveyResults(discName, projName);

		return ((Student) _person).showSurveyResults(discName, projName);
	}

	public String showSurveyResultsDiscipline(String discName) throws NoSuchDisciplineIdException {
		return ((Student) _person).showSurveyResultsDiscipline(discName);
	}
}
