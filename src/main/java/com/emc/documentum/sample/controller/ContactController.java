package com.emc.documentum.sample.controller;

import com.documentum.fc.common.DfException;
import com.emc.documentum.sample.domain.Contact;
import com.emc.documentum.sample.repositories.ContactRepository;
import com.emc.documentum.springdata.core.Documentum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

/**
 * Controller class to expose the REST services for interacting with contact resources
 *
 * @author Simon O'Brien
 */
@RestController()
@RequestMapping(value="/api/contacts")
public class ContactController {

    @Autowired
    @Qualifier("repositoryName")
    private String repositoryName;

    @Autowired
    @Qualifier("repositoryUsername")
    private String repositoryUsername;

    @Autowired
    @Qualifier("repositoryPassword")
    private String repositoryPassword;

    @Autowired
    private Documentum documentum;

    @Autowired
    private ContactRepository contactRepository;

    /**
     * init post construction
     */
    @PostConstruct
    public void postConstruct(){
        setDCTMCredentials();
    }

    /**
     * Method to map requests to GET contacts
     *
     * @param name if provided only contacts that contain name will be returned
     * @param group if provided only contacts that have the specified group will be returned
     * @return contacts
     * @throws DfException
     */
    @RequestMapping(value={"/",""}, method= RequestMethod.GET)
    public Iterable<Contact> getAllContacts(@RequestParam(value = "name", required = false) final String name,
                                            @RequestParam(value = "group", required = false) final String group) throws DfException {

        Iterable<Contact> contacts = null;

        /*
         * if the name URI param is supplied then only get contacts where the name contains the
         * value in the URI param, else if the group URI param is supplied then only get contacts
         * that are part of the group specified by the group URI param
         */
        if(StringUtils.hasText(name)) {
            contacts = contactRepository.findByNameContaining(name);
        } else if(StringUtils.hasText(group)) {
            contacts = contactRepository.findByGroups(group);
        } else {
            contacts = contactRepository.findAll();
        }

        return contacts;
    }

    /**
     * Method to map requests to POST a new contact
     *
     * @param contact The contact to create
     * @return The new contact
     * @throws DfException
     */
    @RequestMapping(value="", method=RequestMethod.POST)
    public Contact createContact(@RequestBody @Valid final Contact contact) throws DfException {
        contact.setId(null); // make sure no ID is set so that new object is created
        return contactRepository.save(contact);
    }

    /**
     * Method to map requests to DELETE an existing contact
     *
     * @param id The ID of the contact to delete
     * @throws DfException
     */
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void deleteContact(@PathVariable String id) throws DfException {
        contactRepository.delete(id);
    }

    /**
     * Method to map requests to POST an update to an existing contact
     *
     * @param id The ID of the contact to update
     * @param contact The contact updates
     * @return The updated contact
     * @throws DfException
     */
    @RequestMapping(value="/{id}", method=RequestMethod.POST)
    public Contact updateContact(@PathVariable String id, @RequestBody @Valid final Contact contact) throws DfException {
        contact.setId(id);
        return contactRepository.save(contact);
    }

    /*
     * setup the Documentum username/password/repository
     */
    private void setDCTMCredentials() {
        documentum.setCredentials(new UserCredentials(repositoryUsername, repositoryPassword));
        documentum.setDocBase(repositoryName);
    }
}
