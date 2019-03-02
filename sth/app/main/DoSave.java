package sth.app.main;

import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;
import sth.core.SchoolManager;

//FIXME import other classes if needed

/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<SchoolManager> {

	private Input<String> _fileName;

    /**
    * @param receiver
    */
    public DoSave(SchoolManager receiver) {
        super(Label.SAVE, receiver);
		_fileName = _form.addStringInput(Message.newSaveAs());
    }

    /** @see pt.tecnico.po.ui.Command#execute() */
    @Override
    public final void execute() {
	    try {
		    if (_receiver.fileDoesNotExist()) {
			    _form.parse();
			    _receiver.save(_fileName.toString());
		    }
			else
			    _receiver.save();

	    } catch (IOException e){
	    	e.printStackTrace();
	    }
    }
}
