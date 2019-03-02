package sth.core;

public class Employee extends Person{

	private static final long serialVersionUID = 201810051538L;

	/* package */ Employee(Integer id, Integer phoneNumber, String name){
		super(id, phoneNumber, name);
	}


	/* package */ String getHeader(){
		return "FUNCIONÁRIO|" + getId() + "|" + getPhoneNumber() + "|" + getName() + "\n";
	}

	/* package */ String getBody(){
		return "";
	}
}
