package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.OpeningSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.OpeningSurveyIdException;

/**
 * 4.4.2. Close project.
 */
public class DoCloseProject extends sth.app.common.ProjectCommand {

    /**
    * @param receiver
    */
    public DoCloseProject(SchoolManager receiver) {
    super(Label.CLOSE_PROJECT, receiver);
  }

    /** @see sth.app.common.ProjectCommand#myExecute() */
    @Override
    public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
        try {
	        _receiver.closeProject(_discipline.toString(), _project.toString());
        } catch (OpeningSurveyIdException osie){
        	throw new OpeningSurveyException(_discipline.value(), _project.value());
        }
    }

}
