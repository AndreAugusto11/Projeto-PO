package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSurveyException;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSurveyIdException;

/**
 * 4.4.5. Show survey results.
 */
public class DoShowSurveyResults extends sth.app.common.ProjectCommand {

    /**
    * @param receiver
    */
    public DoShowSurveyResults(SchoolManager receiver) {
        super(Label.SHOW_SURVEY_RESULTS, receiver);
    }

    /** @see sth.app.common.ProjectCommand#myExecute() */
    @Override
    public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
        try{
            _display.add(_receiver.showSurveyResults(_discipline.value(), _project.value()));
            _display.display();
        } catch (NoSurveyIdException nsie){
      	    throw new NoSurveyException(_discipline.value(), _project.value());
        }
    }
}
