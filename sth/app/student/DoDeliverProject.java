package sth.app.student;

import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.NoSuchDisciplineIdException;

/**
 * 4.5.1. Deliver project.
 */
public class DoDeliverProject extends sth.app.common.ProjectCommand {

	Input<String> _submissionText;

    /**
    * @param receiver
    */
    public DoDeliverProject(SchoolManager receiver) {
        super(Label.DELIVER_PROJECT, receiver);
        _submissionText = _form.addStringInput(Message.requestDeliveryMessage());
}

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void myExecute() throws NoSuchProjectIdException, NoSuchDisciplineIdException, DialogException {
    	_receiver.deliverProject(_discipline.value(), _project.value(), _submissionText.value());
    }

}
