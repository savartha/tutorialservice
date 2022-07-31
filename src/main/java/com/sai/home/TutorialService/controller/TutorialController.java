package com.sai.home.TutorialService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sai.home.TutorialService.exception.ResourceNotFoundException;
import com.sai.home.TutorialService.model.Tutorial;
import com.sai.home.TutorialService.repository.TutorialRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/tutorials")
@Api(value = "tutorials", description = "Tutorials API")
public class TutorialController {
	  @Autowired
	  TutorialRepository tutorialRepository;

	  @GetMapping("/")
	  @ApiOperation(value = "Retrieves all tutorials", response = Tutorial.class, responseContainer = "List")
	  public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
	    List<Tutorial> tutorials = new ArrayList<Tutorial>();

	    if (title == null)
	      tutorialRepository.findAll().forEach(tutorials::add);
	    else
	      tutorialRepository.findByTitleContaining(title).forEach(tutorials::add);

	    if (tutorials.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    return new ResponseEntity<>(tutorials, HttpStatus.OK);
	  }

	  @GetMapping("/{id}")
	  @ApiOperation(value = "Retrieves a Tutorial associated with tutorialId", response = Tutorial.class)
	  public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
	    Tutorial tutorial = tutorialRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

	    return new ResponseEntity<>(tutorial, HttpStatus.OK);
	  }

	  @PostMapping("/")
	  @ApiOperation(value = "Create a new Tutorial", response = Tutorial.class, notes = "The newly created tutorial id will be sent in the location response header")
	  public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
		  System.out.println("Title: "+tutorial.getTitle());
	    Tutorial _tutorial = tutorialRepository.save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished()));
	    return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
	  }

	  @PutMapping("/{id}")
	  @ApiOperation(value = "Modifies tutorial associated with tutorialId", response = Tutorial.class)
	  public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
	    Tutorial _tutorial = tutorialRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

	    _tutorial.setTitle(tutorial.getTitle());
	    _tutorial.setDescription(tutorial.getDescription());
	    _tutorial.setPublished(tutorial.isPublished());
	    
	    return new ResponseEntity<>(tutorialRepository.save(_tutorial), HttpStatus.OK);
	  }

	  @DeleteMapping("/{id}")
	  @ApiOperation(value = "Deletes tutorial associated with tutorialId")
	  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
	    tutorialRepository.deleteById(id);
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @DeleteMapping("/")
	  @ApiOperation(value = "Deletes all tutorails")
	  public ResponseEntity<HttpStatus> deleteAllTutorials() {
	    tutorialRepository.deleteAll();
	    
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

	  @GetMapping("/published")
	  @ApiOperation(value = "Retrieves all tutorials which were published", response = Tutorial.class, responseContainer = "List")
	  public ResponseEntity<List<Tutorial>> findByPublished() {
	    List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

	    if (tutorials.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    
	    return new ResponseEntity<>(tutorials, HttpStatus.OK);
	  }

}
