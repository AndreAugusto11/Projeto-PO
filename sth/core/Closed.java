package sth.core;

import sth.core.exception.NoSurveyIdException;

import java.io.Serializable;

public class Closed implements State, Serializable {

	private static final long serialVersionUID = 201810051538L;

	public void answerSurvey(Survey survey, int hours, String comment) throws NoSurveyIdException {
		throw new NoSurveyIdException();
	}

	public State openSurvey(Survey survey){
		return new Opened();
	}

	public State closeSurvey(Survey survey){
		return this;
	}

	public State cancelSurvey(Survey survey){
		return new Opened();
	}

	public State finishSurvey(Survey survey){
		return new Finished();
	}

	public String showSurveyResults(Survey survey, String discName, Project project){
		return discName + " - " + project.getName() + " (fechado)\n";
	}

	public String showSurveyResultsTeacher(Survey survey, String discName, Project proj){
		return discName + " - " + proj.getName() + " (fechado)\n";
	}

	public String showSurveyResultsRepresentative(Survey survey, String discName, Project proj){
		return discName + " - " + proj.getName() + " (fechado)\n";
	}
}
