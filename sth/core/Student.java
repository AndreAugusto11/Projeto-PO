package sth.core;

import sth.core.exception.BadEntryException;
import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.DuplicateSurveyIdException;
import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.NonEmptySurveyIdException;
import sth.core.exception.OpeningSurveyIdException;
import sth.core.exception.SurveyFinishedIdException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Student extends Person implements Observer{

	private static final long serialVersionUID = 201810051538L;
	
	private Course _course;
	private List<Discipline> _disciplinesList;
	private List<String> _messagesList;
	private boolean _representative;

	@Override
	/* package */ void parseContext(String lineContext, School school) throws BadEntryException {
		String components[] =  lineContext.split("\\|");

		if (components.length != 2)
			throw new BadEntryException("Invalid line context " + lineContext);

		if (_course == null) {
			_course = school.parseCourse(components[0]);
			_course.addStudent(this);
		}

		Discipline dis = _course.parseDiscipline(components[1]);

		dis.enrollStudent(this);
		this.enrollDiscipline(dis);
	}


	/* package */ Student(Integer id, Integer phoneNumber, String name, Boolean Representative){
		super(id, phoneNumber, name);
		_disciplinesList = new ArrayList<>();
		_messagesList = new ArrayList<>();
		_representative = Representative;
	}


	/* package */ boolean isRepresentative(){
		return _representative;
	}


	/* package */ void setRepresentative(){
		_representative = true;
		/* change course */
	}


	/* package */ void setNotRepresentative(){
		_representative = false;
		/* change course */
	}


	/* package */ List<Discipline> getDisciplinesList(){
		return _disciplinesList;
	}


	/* package */ void submitProject(String discName, String projName, String answer) throws NoSuchDisciplineIdException, NoSuchProjectIdException {
		Discipline discipline = getEnrolledDiscipline(discName);
		Project project = discipline.getProject(projName);

		project.receiveSubmission(getId(), answer);
	}


	private void enrollDiscipline(Discipline disc){
		_disciplinesList.add(disc);
	}


	private Discipline getEnrolledDiscipline(String discName) throws NoSuchDisciplineIdException{

		for (Discipline disc: _disciplinesList){
			if (discName.equals(disc.getName())){
				return disc;
			}
		}

		throw new NoSuchDisciplineIdException(discName);
	}


	/* package */ void cancelSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException, NonEmptySurveyIdException, SurveyFinishedIdException {

		Discipline assocDisc = _course.getDiscipline(discName);
		Project assocProject = assocDisc.getProject(projName);

		assocProject.cancelSurvey();
	}


	/* package */ void createSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, DuplicateSurveyIdException {

		Discipline assocDisc = _course.getDiscipline(discName);
		Project assocProject = assocDisc.getProject(projName);

		assocProject.createSurvey(assocDisc.getSetAllObservers());
	}


	/* package */ void openSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, OpeningSurveyIdException, NoSurveyIdException{

		Discipline assocDisc = _course.getDiscipline(discName);
		Project assocProject = assocDisc.getProject(projName);

		assocProject.openSurvey(discName, projName);
	}


	/* package */ void closeSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, ClosingSurveyIdException, NoSurveyIdException{

		Discipline assocDisc = _course.getDiscipline(discName);
		Project assocProject = assocDisc.getProject(projName);

		assocProject.closeSurvey();
	}


	/* package */ void finishSurvey(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, FinishingSurveyIdException, NoSurveyIdException{

		Discipline assocDisc = _course.getDiscipline(discName);
		Project assocProject = assocDisc.getProject(projName);

		assocProject.finishSurvey(discName, projName);
	}


	/* package */ void answerSurvey(String discName, String projName, int hours, String comment) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException{
		Discipline discipline = getEnrolledDiscipline(discName);
		Project assocProject = discipline.getProject(projName);

		assocProject.answerSurvey(getId(), hours, comment);
	}

	/* package */ String showSurveyResults(String discName, String projName) throws NoSuchDisciplineIdException, NoSuchProjectIdException, NoSurveyIdException{
		Discipline discipline = getEnrolledDiscipline(discName);
		Project assocProject = discipline.getProject(projName);

		return assocProject.showSurveyResults(discName, projName);
	}

	/* package */ String showSurveyResultsDiscipline(String discName) throws NoSuchDisciplineIdException {
		StringBuilder result = new StringBuilder();
		Discipline assocDisc = _course.getDiscipline(discName);
		List<Project> _projects = new ArrayList<>(assocDisc.getListProjects());

		_projects.sort(new Comparator<Project>() {
			@Override
			public int compare(Project p1, Project p2) {
				return p1.getName().compareTo(p2.getName());
			}
		});

		for (Project proj: _projects){
			result.append(proj.showSurveyResultsRepresentative(discName));
		}

		return result.toString();
	}

	/* package */ Course getCourse(){
		return _course;
	}

	public void updateMessages(Observable observable, String discName, String projName){
		if (observable.getState() instanceof Opened){
			_messagesList.add("Pode preencher inquérito do projecto " + projName + " da disciplina " + discName + "\n");
		}
		if (observable.getState() instanceof Finished) {
			_messagesList.add("Resultados do inquérito do projecto " + projName + " da disciplina " + discName + "\n");
		}
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
		if (_representative)
			return "DELEGADO|" + getId() + "|" + getPhoneNumber() + "|" + getName() + "\n";
		else
			return "ALUNO|" + getId() + "|" + getPhoneNumber() + "|" + getName() + "\n";

	}

	/* package */ String getBody(){
		StringBuilder sentence = new StringBuilder();

		for (Discipline disc: _disciplinesList) {
			sentence.append("* ").append(disc.getCourse().getName()).append(" - ").append(disc.getName()).append("\n");
		}

		return sentence.toString();
	}
}