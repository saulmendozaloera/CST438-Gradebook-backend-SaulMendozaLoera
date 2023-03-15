package com.cst438.controllers;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	//addAssignment lets intructor add a new assignment to the course. 
	@PostMapping("/assignment/create")
	@Transactional 
	public void addAssignment(@RequestParam("id") int courseId, @RequestParam("name") String name, @RequestParam("due") String date) {
		Course course = courseRepository.findById(courseId).orElse(null);
		
		if(course != null) {
			Assignment assignment = new Assignment();
			assignment.setDueDate(Date.valueOf(date));
			assignment.setName(name);
			assignment.setNeedsGrading(1);
			assignment.setCourse(course);
			assignmentRepository.save(assignment);
		}
		else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment cannot be created for this course. "+courseId);
		}
		
	}
	//updateAssignmentName allows instructor to change the name of assignment. 
	@PutMapping("/assignment/update")
	@Transactional
	public void updateAssignmentName(@RequestParam("id") Integer assignmentId, @RequestParam("name") String name) {
		Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null); 
		if(assignment !=null) {
			assignment.setName(name);
			assignmentRepository.save(assignment);
		}
		else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment cannot name cannot be updated "+assignmentId);
		}
		
	}
	//deleteAssignment allows instructor to delete an assignment only if it hasnt been graded yet. 
	@DeleteMapping("/assignment/delete")
	@Transactional
	public void deleteAssignment(@RequestParam("id") int assignmentId) {
		Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);
		if(assignment.getNeedsGrading() == 1) {
			assignmentRepository.deleteById(assignmentId);
		}
		else {
			throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "Assignment cannot be deleted. "+assignmentId );
		}
	}
	
	
}