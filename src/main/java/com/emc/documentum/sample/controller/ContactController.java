package com.emc.documentum.sample.controller;

import com.documentum.fc.common.DfException;
import com.emc.documentum.sample.domain.Contact;
import com.emc.documentum.sample.domain.Student;
import com.emc.documentum.sample.repositories.ContactRepository;
import com.emc.documentum.sample.repositories.StudentRepository;
import com.emc.documentum.springdata.core.DctmTemplate;
import com.emc.documentum.springdata.core.Documentum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController()
@RequestMapping(value="/api/contacts")
public class ContactController {

    @Autowired
    private Documentum dctm;

    @Autowired
    private ContactRepository contactRepository;

    @PostConstruct
    public void postConstruct(){
        setDCTMCredentials();
    }

    @RequestMapping(value={"/",""}, method= RequestMethod.GET)
    public Iterable<Contact> getAllContacts() throws DfException {
        return contactRepository.findAll();
    }

    private void setDCTMCredentials() {
        dctm.setCredentials(new UserCredentials("dmadmin", "Dmadm1n"));
        dctm.setDocBase("projects");
    }
}
