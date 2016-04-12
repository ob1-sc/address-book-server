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
@RequestMapping(value="/api/addresses")
public class AddressController {

    @Autowired
    private Documentum dctm;

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


    private void setDCTMCredentials() {
        dctm.setCredentials(new UserCredentials("dmadmin", "Dmadm1n"));
        dctm.setDocBase("projects");
    }
}
