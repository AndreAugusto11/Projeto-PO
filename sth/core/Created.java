package sth.core;

import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.NonEmptySurveyIdException;

import java.io.Serializable;

public class Created implements State, Serializable {

	private static final long serialVersionUID = 201810051538L;

	public void answerSurvey(Survey survey, int hours, String comment) throws NoSurveyIdException{
		throw new NoSurveyIdException();
	}

	public State openSurvey(Survey survey){
		return new Opened();
	}

	public State closeSurvey(Survey survey) throws ClosingSurveyIdException {
		throw new ClosingSurveyIdException();
	}

	public State cancelSurvey(Survey survey) throws NonEmptySurveyIdException {
		if (survey.getNumberAnswers() > 0)
			throw new NonEmptySurveyIdException();

		return null;
	}

	public State finishSurvey(Survey survey) throws FinishingSurveyIdException {
		throw new FinishingSurveyIdException();
	}

	public String showSurveyResults(Survey survey, String discName, Project project){
		return discName + " - " + project.getName() + " (por abrir)\n";
	}

	public String showSurveyResultsTeacher(Survey survey, String discName, Project proj){
		return discName + " - " + proj.getName() + " (por abrir)\n";
	}

	public String showSurveyResultsRepresentative(Survey survey, String discName, Project proj){
		return discName + " - " + proj.getName() + " (por abrir)\n";
	}
}
