package sth.core;

import sth.core.exception.BadEntryException;

import java.util.Comparator;


public abstract class Person implements java.io.Serializable {

	private static final long serialVersionUID = 201810051538L;

	private String _name;
	private Integer _phoneNumber;
	private Integer _id;

	/**
	 * Parses the context information for a person from the import file.
	 * This method defines the default behavior: no extra information is needed
	 * thus it throws the exception.
	 **/
	/* package */ void parseContext(String context, School school) throws BadEntryException {
		throw new BadEntryException("Should not have extra context: " + context);
	}

	/* package */ Person(Integer id, Integer phoneNumber, String name){
		_name = name;
		_phoneNumber = phoneNumber;
		_id = id;
	}

	/* package */ String getName(){
		return _name;
	}

	/* package */ Integer getPhoneNumber(){
		return _phoneNumber;
	}

	/* package */ void changePhoneNumber(Integer phoneNumber){
		_phoneNumber = phoneNumber;
	}

	public Integer getId(){
		return _id;
	}

	/* package */ static class CompareById implements Comparator<Person> {
		@Override
		public int compare(Person p1, Person p2) {
			return p1.getId() - p2.getId();
		}
	}

	/* package */ static class CompareByName implements Comparator<Person> {
		@Override
		public int compare(Person p1, Person p2) {
			return p1.getName().compareTo(p2.getName());
		}
	}

	/* package */ abstract String getHeader();

	/* package */ abstract String getBody();

	public final String toString(){
		String finalString = "";

		finalString += getHeader();
		finalString += getBody();

		return finalString;
	}
}