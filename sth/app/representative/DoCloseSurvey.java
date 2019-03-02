package sth.app.representative;

import pt.tecnico.po.ui.DialogException;
import sth.app.exception.ClosingSurveyException;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.ClosingSurveyIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.5.4. Close survey.
 */
public class DoCloseSurvey extends sth.app.common.ProjectCommand {
    /**
    * @param receiver
    */
    public DoCloseSurvey(SchoolManager receiver) {
        super(Label.CLOSE_SURVEY, receiver);
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
    	try{
		    _receiver.closeSurvey(_discipline.value(), _project.value());
	    } catch (ClosingSurveyIdException csie){
    		throw new ClosingSurveyException(_discipline.value(), _project.value());
	    } catch (NoSurveyIdException nsie) {
		    throw new NoSurveyException(_discipline.value(), _project.value());
	    }
    }
}
