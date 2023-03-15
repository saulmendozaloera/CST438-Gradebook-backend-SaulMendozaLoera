package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.BDDMockito.given;

import com.cst438.controllers.AssignmentController;
import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentGradeRepository;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.Enrollment;
import com.cst438.services.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ContextConfiguration(classes = { AssignmentController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
class JunitAssignmentTest {
	static final String URL = "http://localhost:8080";
	public static final int TEST_COURSE_ID = 2468;
	public static final String TEST_COURSE_INTRUCTOR = "test@csumb.edu";
	public static final String TEST_COURSE_SEMESTER = "Spring";
	public static final int TEST_COURSE_YEAR = 2023;
	public static final int TEST_ASSIGNMENT_ID = 1;
	public static final String TEST_ASSIGNMENT_NAME = "hw 2 - Probabilities";
	
	
	@MockBean
	AssignmentRepository assignmentRepository;
	
	@MockBean
	CourseRepository courseRepository;
	
	@MockBean
	AssignmentGradeRepository assignmentGradeRepository;
	
	@MockBean
	RegistrationService registrationService;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	@DirtiesContext
	public void addAssignment() throws Exception{
		MockHttpServletResponse response;
		
		Course course = new Course();
		course.setCourse_id(TEST_COURSE_ID);
		course.setSemester(TEST_COURSE_SEMESTER);
		course.setYear(TEST_COURSE_YEAR);
		course.setInstructor(TEST_COURSE_INTRUCTOR);
		course.setEnrollments(new java.util.ArrayList<Enrollment>());
		course.setAssignments(new java.util.ArrayList<Assignment>());		
		
		Assignment assignment = new Assignment();
		assignment.setDueDate(Date.valueOf("2023-03-29"));
		assignment.setName(TEST_ASSIGNMENT_NAME);
		assignment.setNeedsGrading(1);
		assignment.setCourse(course);
	
		given(courseRepository.findById(TEST_COURSE_ID)).willReturn(Optional.of(course));
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(Optional.of(assignment));
		
		response = mvc.perform(MockMvcRequestBuilders.post("/assignment/create").param("id", asJsonString(TEST_COURSE_ID)).param("name",TEST_ASSIGNMENT_NAME).param("due","2023-03-29").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		assertEquals(200, response.getStatus());
		
	}

	@Test
	@DirtiesContext
	public void updateAssignmentName() throws Exception{
		MockHttpServletResponse response;
		
		Course course = new Course();
		course.setCourse_id(TEST_COURSE_ID);
		course.setSemester(TEST_COURSE_SEMESTER);
		course.setYear(TEST_COURSE_YEAR);
		course.setInstructor(TEST_COURSE_INTRUCTOR);
		course.setEnrollments(new java.util.ArrayList<Enrollment>());
		course.setAssignments(new java.util.ArrayList<Assignment>());		
		
		Assignment assignment = new Assignment();
		assignment.setId(TEST_ASSIGNMENT_ID);
		assignment.setDueDate(Date.valueOf("2023-03-29"));
		assignment.setName(TEST_ASSIGNMENT_NAME);
		assignment.setNeedsGrading(1);
		assignment.setCourse(course);
		
		
		given(courseRepository.findById(TEST_COURSE_ID)).willReturn(Optional.of(course));
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(Optional.of(assignment));
		
		response = mvc.perform(MockMvcRequestBuilders.put("/assignment/update").param("id", asJsonString(TEST_ASSIGNMENT_ID)).param("name","lab2 - Statistical Probabilities").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		assertEquals(200, response.getStatus());
		
	}
	
	@Test
	@DirtiesContext
	public void deleteAssignment() throws Exception{
		MockHttpServletResponse response;
		
		Course course = new Course();
		course.setCourse_id(TEST_COURSE_ID);
		course.setSemester(TEST_COURSE_SEMESTER);
		course.setYear(TEST_COURSE_YEAR);
		course.setInstructor(TEST_COURSE_INTRUCTOR);
		course.setEnrollments(new java.util.ArrayList<Enrollment>());
		course.setAssignments(new java.util.ArrayList<Assignment>());		
		
		Assignment assignment = new Assignment();
		assignment.setId(TEST_ASSIGNMENT_ID);
		assignment.setDueDate(Date.valueOf("2023-03-29"));
		assignment.setName(TEST_ASSIGNMENT_NAME);
		assignment.setNeedsGrading(1);
		assignment.setCourse(course);
		assignmentRepository.save(assignment);
		
		given(courseRepository.findById(TEST_COURSE_ID)).willReturn(Optional.of(course));
		given(assignmentRepository.findById(TEST_ASSIGNMENT_ID)).willReturn(Optional.of(assignment));
		response = mvc.perform(MockMvcRequestBuilders.delete("/assignment/delete").param("id", asJsonString(TEST_ASSIGNMENT_ID)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		assertEquals(200, response.getStatus());
	}

	
	private static String asJsonString(final Object obj) {
		try {

			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
