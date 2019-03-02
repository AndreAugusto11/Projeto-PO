package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.FinishingSurveyException;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

//FIXME import other classes if needed

import sth.core.exception.FinishingSurveyIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.6.5. Finish survey.
 */
public class DoFinishSurvey extends sth.app.common.ProjectCommand {

    /**
    * @param receiver
    */
    public DoFinishSurvey(SchoolManager receiver) {
      super(Label.FINISH_SURVEY, receiver);
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
    	try{
		    _receiver.finishSurvey(_discipline.value(), _project.value());
	    } catch (FinishingSurveyIdException fsie){
    		throw new FinishingSurveyException(_discipline.value(), _project.value());
	    } catch (NoSurveyIdException nsie) {
		    throw new NoSurveyException(_discipline.value(), _project.value());
	    }
    }

}
