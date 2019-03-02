package sth.app.person;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;


/**
 * 4.2.2. Change phone number.
 */
public class DoChangePhoneNumber extends Command<SchoolManager> {

    private Input<Integer> _input;

    /**
     * @param receiver
     */
     public DoChangePhoneNumber(SchoolManager receiver) {
        super(Label.CHANGE_PHONE_NUMBER, receiver);
        _input = _form.addIntegerInput(Message.requestPhoneNumber());
     }

    /** @see pt.tecnico.po.ui.Command#execute() */
	@Override
	public final void execute() {
        String finalString;
	    _form.parse();
	    finalString = _receiver.changePhoneNumber(_input.value());

	    _display.add(finalString);
	    _display.display();
	}
}
