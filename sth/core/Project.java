package sth.core;

import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.DuplicateSurveyIdException;
import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.NonEmptySurveyIdException;
import sth.core.exception.OpeningSurveyIdException;
import sth.core.exception.SurveyFinishedIdException;

import java.util.*;

public class Project implements java.io.Serializable {

	private static final long serialVersionUID = 201810051538L;

	private Map<Integer, String> _submissions;
	private String _name;
	private boolean _state;
	private Survey _survey = null;

	/* package */ Project(String name){
		_submissions = new HashMap<>();
		_name = name;
		_state = true;
	}

	/* package */ void closeProject(String discName, String projName) throws OpeningSurveyIdException{
		_state = false;
		if (_survey != null)
			_survey.openSurvey(discName, projName);
	}

	/* package */ int getNumberSubmissions(){
		return _submissions.size();
	}

	/* package */ String getName(){
		return _name;
	}

	/* package */ Survey getSurvey(){
		return _survey;
	}

	private void checkSurveyNull() throws NoSurveyIdException{
		if (_survey == null)
			throw new NoSurveyIdException();
	}


	/* package */ void receiveSubmission(Integer id, String answer) throws NoSuchProjectIdException{
		if (_state)
			_submissions.put(id, answer);
		else
			throw new NoSuchProjectIdException(_name);
	}

	/* package */ String getSubmissions(String discName, String projName){

		StringBuilder finalString = new StringBuilder(discName + " - " + projName + "\n");

		Set<Integer> idsSet = _submissions.keySet();
		List<Integer> sortedList = new ArrayList<>(idsSet);
		Collections.sort(sortedList);

		for (int i: sortedList){
			finalString.append("* ").append(i).append(" - ").append(_submissions.get(i)).append("\n");
		}

		return finalString.toString();
	}

	/* package */ void createSurvey(Set<Observer> observersList) throws DuplicateSurveyIdException, NoSuchProjectIdException{
		if (!_state){
			throw new NoSuchProjectIdException(_name);
		}

		if (_survey != null){
			throw new DuplicateSurveyIdException();
		}

		_survey = new Survey(observersList);
	}

	/* package */ void cancelSurvey() throws NoSurveyIdException, NonEmptySurveyIdException, SurveyFinishedIdException{
		checkSurveyNull();
		_survey.cancelSurvey();
		if (_survey.getState() == null)
			_survey = null;
	}

	/* package */ void openSurvey(String discName, String projName) throws NoSurveyIdException, OpeningSurveyIdException{
		checkSurveyNull();
		if (!_state)
			_survey.openSurvey(discName, projName);
		throw new OpeningSurveyIdException();
	}

	/* package */ void closeSurvey() throws NoSurveyIdException, ClosingSurveyIdException{
		checkSurveyNull();
		_survey.closeSurvey();
	}

	/* package */ void finishSurvey(String discName, String projName) throws NoSurveyIdException, FinishingSurveyIdException{
		checkSurveyNull();
		_survey.finishSurvey(discName, projName);
	}

	/* package */ void answerSurvey(int id, int hours, String comment) throws NoSurveyIdException, NoSuchProjectIdException{
		checkSurveyNull();

		if (_submissions.containsKey(id))
			_survey.answerSurvey(id, hours, comment);

		else
			throw new NoSuchProjectIdException(_name);
	}

	/* package */ String showSurveyResultsTeacher(String discName, String projName) throws NoSurveyIdException{
		checkSurveyNull();
		return _survey.showSurveyResultsTeacher(discName, this);
	}

	/* package */ String showSurveyResults(String discName, String projName) throws NoSurveyIdException{
		checkSurveyNull();
		return _survey.showSurveyResults(discName, this);
	}

	/* package */ String showSurveyResultsRepresentative(String discName){
		if (_survey == null)
			return "";

		return _survey.showSurveyResultsRepresentative(discName, this);
	}
}