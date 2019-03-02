package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.5.2. Answer survey.
 */
public class DoAnswerSurvey extends sth.app.common.ProjectCommand {

	private Input<Integer> _hours;
	private Input<String> _comment;

    /**
    * @param receiver
    */
    public DoAnswerSurvey(SchoolManager receiver) {
        super(Label.ANSWER_SURVEY, receiver);
        _hours = _form.addIntegerInput(Message.requestProjectHours());
        _comment = _form.addStringInput(Message.requestComment());
    }

    /** @see sth.app.common.ProjectCommand#myExecute() */
    @Override
    public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {
    	try{
		    _receiver.answerSurvey(_discipline.value(), _project.value(), _hours.value(), _comment.value());
	    } catch (NoSurveyIdException nsie){
    		throw new NoSurveyException(_discipline.value(), _project.value());
	    }
    }
}