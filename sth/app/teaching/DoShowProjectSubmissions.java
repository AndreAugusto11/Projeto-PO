package sth.app.teaching;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchDisciplineIdException;
import sth.core.exception.NoSuchProjectIdException;

/**
 * 4.4.3. Show project submissions.
 */
public class DoShowProjectSubmissions extends sth.app.common.ProjectCommand {
	/**
    * @param receiver
    */
    public DoShowProjectSubmissions(SchoolManager receiver) {
        super(Label.SHOW_PROJECT_SUBMISSIONS, receiver);
    }

    /** @see sth.app.common.ProjectCommand#myExecute() */
    @Override
    public final void myExecute() throws DialogException, NoSuchDisciplineIdException, NoSuchProjectIdException {
		String string = _receiver.showProjectSubmissions(_discipline.value(), _project.value());
		_display.add(string);
		_display.display();
    }
}
