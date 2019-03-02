package sth.core;

import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.NonEmptySurveyIdException;
import sth.core.exception.OpeningSurveyIdException;
import sth.core.exception.SurveyFinishedIdException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Survey implements Serializable, Observable {

	private static final long serialVersionUID = 201810051538L;

	private State _state;
	private Set<Integer> _alreadyAnswered;
	private Set<Observer> _observers;
	private List<String> _answers;
	private int _numberAnswers;
	private int _min;
	private int _max;
	private int _total;

	/* package */ Survey(Set<Observer> observersList){
		_answers = new ArrayList<>();
		_alreadyAnswered = new HashSet<>();
		_observers = observersList;
		_state = new Created();
		_min = 0;
		_max = 0;
		_total = 0;
	}

	/* package */ int getMin(){
		return _min;
	}

	/* package */ int getMax(){
		return _max;
	}

	/* package */ int getAverage(){
		if (_numberAnswers == 0)
			return 0;
		return _total/_numberAnswers;
	}

	/* package */ int getNumberAnswers(){
		return _numberAnswers;
	}

	/* package */ void addOneSubmission(){
		_numberAnswers += 1;
	}

	/* package */ void addHours(int hours){
		_total += hours;
	}

	/* package */ void setMin(int min){
		_min = min;
	}

	/* package */ void setMax(int max){
		_max = max;
	}

	/* package */ void addAnswer(String answer){
		_answers.add(answer);
	}

	private boolean studentAlreadyAnswered(int id){
		return _alreadyAnswered.contains(id);

	}

	/* package */ void answerSurvey(int id, int hours, String comment) throws NoSurveyIdException{
		if (!studentAlreadyAnswered(id)){
			_state.answerSurvey(this, hours, comment);
			_alreadyAnswered.add(id);
		}
	}

	/* package */ void openSurvey(String discName, String projName) throws OpeningSurveyIdException {
		_state = _state.openSurvey(this);
		notifyObservers(discName, projName);
	}

	/* package */ void closeSurvey() throws ClosingSurveyIdException {
		_state = _state.closeSurvey(this);
	}

	/* package */ void cancelSurvey() throws NonEmptySurveyIdException, SurveyFinishedIdException{
		_state = _state.cancelSurvey(this);
	}

	/* package */ void finishSurvey(String discName, String projName) throws FinishingSurveyIdException {
		_state = _state.finishSurvey(this);
		notifyObservers(discName, projName);
	}

	/* package */ String  showSurveyResults(String discName, Project proj){
		return _state.showSurveyResults(this, discName, proj);
	}

	/* package */ String  showSurveyResultsTeacher(String discName, Project proj){
		return _state.showSurveyResultsTeacher(this, discName, proj);
	}

	/* package */ String  showSurveyResultsRepresentative(String discName, Project proj){
		return _state.showSurveyResultsRepresentative(this, discName, proj);
	}

	public State getState(){
		return _state;
	}

	public void attach(Observer observer){
		_observers.add(observer);
	}

	public void dettach(Observer observer){
		_observers.remove(observer);
	}

	public void notifyObservers(String discName, String projName){
		for (Observer observer: _observers){
			observer.updateMessages(this, discName, projName);
		}
	}
}
