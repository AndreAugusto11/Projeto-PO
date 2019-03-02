package sth.core;

import sth.core.exception.*;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person implements Observer {

	private static final long serialVersionUID = 201810051538L;

	private List<Discipline> _disciplinesList;
	private List<String> _messagesList;

	/* package */ Teacher(Integer id, Integer phoneNumber, String name){
		super(id, phoneNumber, name);
		_disciplinesList = new ArrayList<>();
		_messagesList = new ArrayList<>();
	}

	@Override
	/* package */ void parseContext(String lineContext, School school) throws BadEntryException {
		String components[] =  lineContext.split("\\|");

		if (components.length != 2)
			throw new BadEntryException("Invalid line context " + lineContext);

		Course course = school.parseCourse(components[0]);
		Discipline discipline = course.parseDiscipline(components[1]);

		discipline.addTeacher(this);
		this.addDiscipline(discipline);
	}

	private void addDiscipline(Discipline disc){
		_disciplinesList.add(disc);
	}

	/* package */ List<Discipline> getDisciplinesList(){
		return _disciplinesList;
	}

	private Discipline getDiscipline(String discName) throws NoSuchDisciplineIdException{
		for (Discipline discipline: _disciplinesList)
			if (discipline.getName().equals(discName))
				return discipline;

		throw new NoSuchDisciplineIdException(discName);
	}


	/* package */ void createProject(String disciplineName, String projName) throws NoSuchDisciplineIdException, DuplicateProjectIdException {
		getDiscipline(disciplineName).createProject(projName);
	}


	/* package */ void closeProject(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, OpeningSurveyIdException {
		getDiscipline(discName).closeProject(discName, projName);
	}


	/* package  */ List<Student> getStudentsOfDiscipline(String nameDiscipline) throws NoSuchDisciplineIdException {
		Discipline disciplineAux = getDiscipline(nameDiscipline);

		return new ArrayList<>(disciplineAux.getStudentsEnrolled());
	}


	/* package */ String showProjectSubmissions(String discName, String projName) throws NoSuchProjectIdException, NoSuchDisciplineIdException{
		Discipline discipline = getDiscipline(discName);

		return discipline.getProjectSubmissions(projName);
	}


	/* package */ String showSurveyResults(String discName, String projName) throws NoSuchProjectIdException, NoSuchDisciplineIdException, NoSurveyIdException {
		Project proj = getDiscipline(discName).getProject(projName);

		return proj.showSurveyResultsTeacher(discName, projName);
	}

	public void updateMessages(Observable observable, String discName, String projName){
		if (observable.getState() instanceof Opened)
			_messagesList.add("Pode preencher inquérito do projecto " + projName + " da disciplina " + discName + "\n");
		if (observable.getState() instanceof Finished)
			_messagesList.add("Resultados do inquérito do projecto " + projName + " da disciplina " + discName + "\n");
	}

	public String getMessages(){
		StringBuilder messages = new StringBuilder();
		for(String st: _messagesList){
			messages.append(st);
		}

		clearNotifications();
		return messages.toString();
	}

	private void clearNotifications(){
		_messagesList.clear();
	}

	/* package */ String getHeader(){
		return "DOCENTE|" + getId() + "|" + getPhoneNumber() + "|" + getName() + "\n";
	}

	@Override
	/* package */ String getBody() {
		StringBuilder sentence = new StringBuilder();

		for (Discipline disc: _disciplinesList) {
			sentence.append("* ").append(disc.getCourse().getName()).append(" - ").append(disc.getName()).append("\n");
		}
		return sentence.toString();
	}
}
