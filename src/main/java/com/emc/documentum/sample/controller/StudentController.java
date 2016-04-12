package com.emc.documentum.sample.controller;

import com.documentum.fc.common.DfException;
import com.emc.documentum.sample.domain.Student;
import com.emc.documentum.sample.repositories.StudentRepository;
import com.emc.documentum.springdata.core.DctmTemplate;
import com.emc.documentum.springdata.core.Documentum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController()
@RequestMapping(value="/api/students")
public class StudentController {

    @Autowired
    private Documentum dctm;
    @Autowired
    private DctmTemplate template;
    @Autowired
    private StudentRepository studentRepository;

    @PostConstruct
    public void postConstruct(){
        setDCTMCredentials();
    }


    
    @RequestMapping(value={"/",""}, method= RequestMethod.GET)
    public Iterable<Student> getAllStudents() throws DfException {

        return studentRepository.findAll();
    }



    @RequestMapping(value="", method=RequestMethod.POST)
    public String createStudent(@RequestBody @Valid final Student student) throws DfException {
        String id = studentRepository.save(student).getId();
        return  "{data:"+ id + "}";
    }


    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteStudent(@PathVariable String id) throws DfException {

    	studentRepository.delete(id);

        return id;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Student findStudentById(@PathVariable String id) throws DfException {

    	return studentRepository.findOne(id);
    }

    @RequestMapping(value="/update", method=RequestMethod.POST)
    public Student updateStudent(@RequestBody @Valid final Student student) throws DfException {

        return studentRepository.save(student);
    }



    private void setDCTMCredentials() {
        dctm.setCredentials(new UserCredentials("dmadmin", "Dmadm1n"));
        dctm.setDocBase("projects");
    }

}
