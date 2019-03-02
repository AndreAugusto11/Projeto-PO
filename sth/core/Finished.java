package sth.core;

import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.NoSurveyIdException;
import sth.core.exception.OpeningSurveyIdException;
import sth.core.exception.SurveyFinishedIdException;

import java.io.Serializable;

public class Finished implements State, Serializable {

	private static final long serialVersionUID = 201810051538L;
	
	public void answerSurvey(Survey survey, int hours, String comment) throws NoSurveyIdException {
		throw new NoSurveyIdException();
	}

	public State openSurvey(Survey survey) throws OpeningSurveyIdException{
		throw new OpeningSurveyIdException();
	}

	public State closeSurvey(Survey survey) throws ClosingSurveyIdException {
		throw new ClosingSurveyIdException();
	}

	public State cancelSurvey(Survey survey) throws SurveyFinishedIdException{
		throw new SurveyFinishedIdException();
	}

	public State finishSurvey(Survey survey){
		return this;
	}

	public String showSurveyResults(Survey survey, String discName, Project project){
		String results = discName + " - " + project.getName() + "\n";
		results += " * Número de respostas: " + survey.getNumberAnswers() + "\n";
		results += " * Tempo médio (horas): " + survey.getAverage() + "\n";
		return results;
	}

	public String showSurveyResultsTeacher(Survey survey, String discName, Project proj){
		String results = discName + " - " + proj.getName() + "\n";
		results += " * Número de submissões: " + proj.getNumberSubmissions() + "\n";
		results += " * Número de respostas: " + survey.getNumberAnswers() + "\n";

		if (survey.getNumberAnswers() == 0)
			results += " * Tempos de resolução (horas) (mínimo, médio, máximo): " + survey.getMin() + ", " + survey.getAverage() + ", " + survey.getMax() + "\n";
		else
			results += " * Tempos de resolução (horas) (mínimo, médio, máximo): " + survey.getMin() + ", " + survey.getAverage() + ", " + survey.getMax() + "\n";

		return results;
	}

	public String showSurveyResultsRepresentative(Survey survey, String discName, Project proj){
		return discName + " - " + proj.getName() + " - " + survey.getNumberAnswers() + " respostas - " + survey.getAverage() + " horas\n";
	}
}