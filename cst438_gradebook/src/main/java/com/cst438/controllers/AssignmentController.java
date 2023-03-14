package com.cst438.controllers;

import java.sql.Date;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class AssignmentController {
	
	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@PostMapping("/assignment/create")
	public void addAssignment() {
		Course course = courseRepository.findById(123456).orElse(null);
		Assignment assignment = new Assignment();
		assignment.setId(3);
		assignment.setDueDate(Date.valueOf("2023-03-29"));
		assignment.setName("hw2 - Probabilities");
		assignment.setNeedsGrading(1);
		assignment.setCourse(course);
		assignmentRepository.save(assignment);
		
	}
	
	@PutMapping("/assignment/update/{id}")
	public void updateAssignmentName(@PathVariable("id") Integer assignmentId) {
		Assignment assignment = assignmentRepository.findById(3).orElse(null); 
		assignment.setName("lab2 - statistical probabilities");
		assignmentRepository.save(assignment);
	}
	
	@DeleteMapping("/assignment/delete/{id}")
	public void deleteAssignment(@PathVariable("id") int assignmentId) {
		Assignment assignment = assignmentRepository.findById(3).orElse(null);
		if(assignment.getNeedsGrading() == 1) {
			assignmentRepository.deleteById(3);
		}
		else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment cannot be deleted. "+assignmentId );
		}
	}
	
	
}