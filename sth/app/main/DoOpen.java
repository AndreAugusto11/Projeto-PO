package sth.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import sth.app.exception.NoSuchPersonException;
import sth.core.SchoolManager;
import sth.core.Student;
import sth.core.Teacher;
import sth.core.exception.NoSuchPersonIdException;

/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<SchoolManager> {

  private Input<String> _fileName;

  /**
   * @param receiver
   */
  public DoOpen(SchoolManager receiver) {
    super(Label.OPEN, receiver);
    _fileName = _form.addStringInput(Message.openFile());
  }

    /** @see pt.tecnico.po.ui.Command#execute() */
	@Override
    public final void execute() throws DialogException{
	    String messages = "";
		_form.parse();

		try {
			_receiver.open(_fileName.toString());

			if (_receiver.isLoggedUserStudent()){
				messages = ((Student) _receiver.getLoggedUser()).getMessages();
			}

			if (_receiver.isLoggedUserProfessor()) {
				messages = ((Teacher) _receiver.getLoggedUser()).getMessages();
			}

			_display.add(messages);
			_display.display();
		} catch (FileNotFoundException fnfe) {
		    _display.popup(Message.fileNotFound());
	    } catch (ClassNotFoundException | IOException e) {
		    e.printStackTrace();
	    } catch (NoSuchPersonIdException nspie){
	  	    throw new NoSuchPersonException(_receiver.getLoggedUser().getId());
	    }
    }
}
