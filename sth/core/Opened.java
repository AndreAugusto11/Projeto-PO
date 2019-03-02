package sth.core;

import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NonEmptySurveyIdException;

import java.io.Serializable;

public class Opened implements State, Serializable {
	
	private static final long serialVersionUID = 201810051538L;

	public void answerSurvey(Survey survey, int hours, String comment) {
		if (survey.getNumberAnswers() == 0) {
			survey.setMin(hours);
			survey.setMax(hours);
		}

		if (hours < survey.getMin())
			survey.setMin(hours);

		if (hours > survey.getMax())
			survey.setMax(hours);

		survey.addOneSubmission();
		survey.addHours(hours);
		survey.addAnswer(comment);
	}

	public State openSurvey(Survey survey){
		return this;
	}

	public State closeSurvey(Survey survey){
		return new Closed();
	}

	public State cancelSurvey(Survey survey) throws NonEmptySurveyIdException{
		if (survey.getNumberAnswers() > 0)
			throw new NonEmptySurveyIdException();

		return null;
	}

	public State finishSurvey(Survey survey) throws FinishingSurveyIdException{
		throw new FinishingSurveyIdException();
	}

	public String showSurveyResults(Survey survey, String discName, Project project){
		return discName + " - " + project.getName() + " (aberto)\n";
	}

	public String showSurveyResultsTeacher(Survey survey, String discName, Project proj){
		return discName + " - " + proj.getName() + " (aberto)\n";
	}

	public String showSurveyResultsRepresentative(Survey survey, String discName, Project proj){
		return discName + " - " + proj.getName() + " (aberto)\n";
	}
}