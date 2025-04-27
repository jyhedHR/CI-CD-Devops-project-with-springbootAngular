package tn.esprit.spring.services;

import tn.esprit.spring.entities.*;

import java.util.List;

public interface IRegistrationServices {

	List<Registration> retrieveAllRegistrations();

	Registration  addRegistration(Registration  registration);

	Registration updateRegistration(Registration registration);
	void deleteRegistration(Long numRegistration);

	Registration retrieveRegistration(Long numRegistration);
	Registration addRegistrationAndAssignToSkier(Registration registration, Long numSkier);
	Registration assignRegistrationToCourse(Long numRegistration, Long numCourse);
	Registration addRegistrationAndAssignToSkierAndCourse(Registration registration, Long numSkier, Long numCourse);
	List<Integer> numWeeksCourseOfInstructorBySupport(Long numInstructor, Support support);
}

