package sth.core;

import sth.core.exception.DuplicateProjectIdException;
import sth.core.exception.NoSuchProjectIdException;
import sth.core.exception.OpeningSurveyIdException;

import java.util.*;

public class Discipline implements java.io.Serializable {

	private static final long serialVersionUID = 201810051538L;

	private String _name;
	private Integer _numberMaxInsc;
	private Map<Integer, Teacher> _teachers;
	private Map<Integer, Student> _students;
	private Map<String, Project> _projects;
	private Course _assocCourse;


	/* package */ Discipline(String name, Integer num, Course course){
		_name = name;
		_numberMaxInsc = num;
		_teachers = new HashMap<>();
		_students = new HashMap<>();
		_projects = new HashMap<>();
		_assocCourse = course;
	}

	/* package */ String getName(){
		return _name;
	}

	/* package */ Course getCourse(){
		return _assocCourse;
	}

	/* package */ void addTeacher(Teacher teacher){
		_teachers.put(teacher.getId(), teacher);
	}

	/* package */ List<Teacher> getTeachersList(){
		return new ArrayList<>(_teachers.values());
	}

	/* package */ void enrollStudent(Student student){
		if (_students.size() < _numberMaxInsc)
			_students.put(student.getId(), student);
	}

	/* package */ Set<Observer> getSetAllObservers(){
		Set<Observer> observerSet = new HashSet<>();
		observerSet.addAll(getStudentsEnrolled());
		observerSet.addAll(getTeachersList());
		observerSet.addAll(_assocCourse.getListRepresentatives());
		return observerSet;
	}

	/* package */ void createProject(String projName) throws DuplicateProjectIdException {

		Project project = _projects.get(projName);

		if (project != null)
			throw new DuplicateProjectIdException(_name, projName);

		project = new Project(projName);
		_projects.put(projName, project);
	}


	/* package */ void closeProject(String discName, String projName) throws NoSuchProjectIdException, OpeningSurveyIdException {
		Project project = _projects.get(projName);
		if (project == null) {
			throw new NoSuchProjectIdException(projName);
		}
		project.closeProject(discName, projName);
	}

	/* package */ Project getProject(String projName) throws NoSuchProjectIdException{
		Project proj = _projects.get(projName);

		if (proj == null)
			throw new NoSuchProjectIdException(projName);

		return proj;
	}

	/* package */ List<Project> getListProjects(){
		Collection<Project> projects = _projects.values();
		return new ArrayList<>(projects);
	}

	/* package */ static class CompareByName implements Comparator<Discipline> {
		@Override
		public int compare(Discipline disc1, Discipline disc2) {
			return disc1.getName().compareTo(disc2.getName());
		}
	}

	/* package */ List<Student> getStudentsEnrolled(){
		return new ArrayList<>(_students.values());
	}

	/* package */ String getProjectSubmissions(String projName) throws NoSuchProjectIdException{
		Project project = _projects.get(projName);

		if (project == null)
			throw new NoSuchProjectIdException(projName);

		return project.getSubmissions(_name, projName);
	}
}
