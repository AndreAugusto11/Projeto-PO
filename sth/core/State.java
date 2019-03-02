package sth.core;

import sth.core.exception.*;

/* package */ interface State {

	/* package */ void answerSurvey(Survey survey, int hours, String comment) throws NoSurveyIdException;
	/* package */ State openSurvey(Survey survey) throws OpeningSurveyIdException;
	/* package */ State closeSurvey(Survey survey) throws ClosingSurveyIdException;
	/* package */ State cancelSurvey(Survey survey) throws NonEmptySurveyIdException, SurveyFinishedIdException;
	/* package */ State finishSurvey(Survey survey) throws FinishingSurveyIdException;
	/* package */ String showSurveyResults(Survey survey, String discName, Project project);
	/* package */ String showSurveyResultsTeacher(Survey survey, String discName, Project proj);
	/* package */ String showSurveyResultsRepresentative(Survey survey, String discName, Project proj);
}
