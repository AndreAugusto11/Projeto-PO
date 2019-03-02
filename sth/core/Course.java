package sth.core;

import sth.core.exception.NoSuchDisciplineIdException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Course implements java.io.Serializable {

	private static final long serialVersionUID = 201810051538L;
	
	private String _name;
	private Integer _numberRepresentatives;
	private Set<Student> _studentsList;
	private List<Discipline> _disciplinesList;

	/* package */ Course(String name){
		_numberRepresentatives = 0;
		_studentsList = new HashSet<>();
		_disciplinesList = new ArrayList<>();
		_name = name;
	}

	/* package */ String getName(){
		return _name;
	}

	/* package */ Integer getNumberRepresentatives(){
		return _numberRepresentatives;
	}

	/* package */ void increaseNumRepresentatives(){
		_numberRepresentatives++;
	}

	/* package */ void decreaseNumRepresentatives(){
		_numberRepresentatives--;
	}

	/* package */ List<Student> getListRepresentatives(){
		List<Student> _representatives = new ArrayList<>();
		for (Student st: _studentsList)
			if (st.isRepresentative())
				_representatives.add(st);

		return _representatives;
	}

	/* package */ Discipline getDiscipline(String discName) throws NoSuchDisciplineIdException{
		for (Discipline disc:_disciplinesList){
			if (disc.getName().equals(discName))
				return disc;
		}

		throw new NoSuchDisciplineIdException(discName);
	}

	/* package */ Discipline parseDiscipline(String discipline){
		for (Discipline disc: _disciplinesList) {
			if (disc.getName().compareTo(discipline) == 0)
				return disc;
		}
		Discipline d = new Discipline(discipline, 200, this);
		_disciplinesList.add(d);
		return d;
	}

	/* package */ void addStudent(Student student){
		_studentsList.add(student);
	}
}
