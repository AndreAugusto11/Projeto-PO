package sth.core.exception;

public class DuplicateProjectIdException extends Exception {

	private static final long serialVersionUID = 201810051538L;

	private String _disciplineName;
	private String _projectName;

	public DuplicateProjectIdException(String disc, String proj) {
		_disciplineName = disc;
		_projectName = proj;
	}

	public String getId() {
		return _disciplineName;
	}
}