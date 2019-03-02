package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

/**
 * 4.2.1. Show person.
 */
public class DoShowPerson extends Command<SchoolManager> {
    private Input<Integer> _personId;

    /**
    * @param receiver
    */
    public DoShowPerson(SchoolManager receiver) {
        super(Label.SHOW_PERSON, receiver);
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
	public final void execute() throws DialogException {
	    _display.add(_receiver.showPerson());
	    _display.display();
	}
}
