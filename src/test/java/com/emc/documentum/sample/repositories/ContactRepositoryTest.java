package com.emc.documentum.sample.repositories;

import com.emc.documentum.sample.TestConfig;
import com.emc.documentum.sample.domain.Contact;
import com.emc.documentum.springdata.core.Documentum;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for Spring Data Contact Repository
 *
 * @author Simon O'Brien
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = {Application.class})
//@WebAppConfiguration
@SpringApplicationConfiguration(classes = {TestConfig.class})
public class ContactRepositoryTest {

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
     * Setup Documentum Credentials
     */
    @Before
    public void setup() {
        documentum.setCredentials(new UserCredentials(repositoryUsername, repositoryPassword));
        documentum.setDocBase(repositoryName);
    }

    /**
     * Create a contact for use in tests
     *
     * @return the test contact
     */
    protected Contact createTestContact() {

        Contact testContact = new Contact();
        testContact.setName(RandomStringUtils.randomAlphanumeric(255));
        testContact.setEmail(RandomStringUtils.randomAlphanumeric(255));

        List<String> groups = new ArrayList<String>();
        groups.add("friends");
        groups.add("colleagues");
        groups.add("university");

        testContact.setGroups(groups);

        return testContact;    }

    /**
     * Test the creation of a contact
     */
    @Test
    public void createContact() {

        Contact contact = createTestContact();
        Contact createdContact = null;

        try {

            // create a test contact
            createdContact = contactRepository.save(contact);

            // check the contact was created and its attributes were set correctly
            assertThat(createdContact, is(notNullValue()));
            assertThat(createdContact.getName(), is(equalTo(contact.getName())));
            assertThat(createdContact.getEmail(), is(equalTo(contact.getEmail())));
            assertThat(createdContact.getGroups(), is(equalTo(contact.getGroups())));

        } finally {

            // clean up the contact
            contactRepository.delete(createdContact);
        }
    }

    /**
     * Test to find a contact by its ID
     */
    @Test
    public void findContactById() {

        Contact contact = createTestContact();
        Contact createdContact = null;

        try {

            // create a test contact
            createdContact = contactRepository.save(contact);

            // check the contact was created
            assertThat(createdContact, is(notNullValue()));

            // try and find the new contact using the ID
            Contact foundContact = contactRepository.findOne(createdContact.getId());

            // check the contact was found and its attributes were set correctly
            assertThat(foundContact, is(notNullValue()));
            assertThat(foundContact.getName(), is(equalTo(contact.getName())));
            assertThat(foundContact.getEmail(), is(equalTo(contact.getEmail())));
            assertThat(foundContact.getGroups(), is(equalTo(contact.getGroups())));

        } finally {

            // clean up the contact
            contactRepository.delete(createdContact);
        }
    }

    /**
     * Test the deletion of a contact
     */
    @Test
    public void deleteContact() {

        // create a test contact
        Contact contact = createTestContact();
        Contact createdContact = contactRepository.save(contact);

        // check the contact was created
        assertThat(createdContact, is(notNullValue()));

        // delete the contact
        contactRepository.delete(createdContact);

        // try and find the deleted contact
        Contact deletedContact = contactRepository.findOne(createdContact.getId());

        // check that no contact was found
        assertThat(deletedContact, is(nullValue()));
    }

    /**
     * Test the update of a contacts properties
     */
    @Test
    public void updateContact() {

        Contact contact = createTestContact();
        Contact createdContact = null;

        try {

            // create a test contact
            createdContact = contactRepository.save(contact);

            // check the contact was created
            assertThat(createdContact, is(notNullValue()));

            // update the contact attributes
            createdContact.setName(RandomStringUtils.randomAlphanumeric(255));
            createdContact.setEmail(RandomStringUtils.randomAlphanumeric(255));

            List<String> groups = new ArrayList<String>();
            groups.add("group1");
            groups.add("group2");
            groups.add("group3");

            createdContact.setGroups(groups);

            // update the contact
            Contact updatedContact = contactRepository.save(createdContact);

            // check the contact was updated and its attributes were set correctly
            assertThat(updatedContact, is(notNullValue()));
            assertThat(updatedContact.getName(), is(equalTo(createdContact.getName())));
            assertThat(updatedContact.getEmail(), is(equalTo(createdContact.getEmail())));
            assertThat(updatedContact.getGroups(), is(equalTo(createdContact.getGroups())));

        } finally {

            // clean up the contact
            contactRepository.delete(createdContact);
        }
    }

    /**
     * Test to find all contacts
     *
     * TODO: update with attribute value match check due to repeating attribute bug and rever
     */
    @Test
    public void findAllContacts() {

        List<Contact> contacts = new ArrayList<Contact>();

        try {

            // create test contacts
            for(int i=0; i<100; i++) {

                // create a test contact
                Contact createdContact = contactRepository.save(createTestContact());

                // check the contact was created
                assertThat(createdContact, is(notNullValue()));

                // store the contact
                contacts.add(createdContact);
            }

            // find all contacts
            Iterable<Contact> contactIterator = contactRepository.findAll();
            assertThat(contactIterator, is(notNullValue()));

            int size = 0;

            // iterate through the contacts
            for (Contact contact : contactIterator) {
                size++;
            }

            // check that the number of contacts found is at least as big as the number created for the test
            assertThat(size, is(greaterThanOrEqualTo(contacts.size())));

        } finally {

            // clean up the contacts
            for(Contact contact : contacts) {
                contactRepository.delete(contact);
            }
        }
    }

    /**
     * Test to find a contact by its exact Name
     */
    @Test
    public void findContactByName() {

        Contact contact = createTestContact();
        Contact createdContact = null;

        try {

            // create a test contact
            createdContact = contactRepository.save(contact);

            // check the contact was created
            assertThat(createdContact, is(notNullValue()));

            // try and find the new contact using the name
            Iterable<Contact> foundContacts = contactRepository.findByName(createdContact.getName());

            // check at least one contact was found
            assertThat(foundContacts, is(notNullValue()));

            boolean testContactWasFound = false;

            // iterate through the contacts
            for (Contact foundContact : foundContacts) {

                // check if the test contact was found
                if(foundContact.getName().equals(createdContact.getName())) {
                    testContactWasFound = true;
                    break;
                }
            }

            assertThat(testContactWasFound, is(true));

        } finally {

            // clean up the contact
            contactRepository.delete(createdContact);
        }
    }

    /**
     * Test to find a contact by part of its name
     */
    @Test
    public void findContactByNameContaining() {

        Contact contact = createTestContact();
        Contact createdContact = null;

        try {

            // create a test contact
            createdContact = contactRepository.save(contact);

            // check the contact was created
            assertThat(createdContact, is(notNullValue()));

            String nameSubstring = createdContact.getName().substring(4,176);

            // try and find the new contact using the name
            Iterable<Contact> foundContacts = contactRepository.findByNameContaining(nameSubstring);

            // check at least one contact was found
            assertThat(foundContacts, is(notNullValue()));

            boolean testContactWasFound = false;

            // iterate through the contacts
            for (Contact foundContact : foundContacts) {

                // check if the test contact was found
                if(foundContact.getName().contains(nameSubstring)) {
                    testContactWasFound = true;
                    break;
                }
            }

            assertThat(testContactWasFound, is(true));

        } finally {

            // clean up the contact
            contactRepository.delete(createdContact);
        }
    }

    /**
     * Test to find a contact where it contains a given group
     */
    @Test
    public void findContactByGroup() {

        Contact contact = createTestContact();
        Contact createdContact = null;

        try {

            // create a test contact
            createdContact = contactRepository.save(contact);

            // check the contact was created
            assertThat(createdContact, is(notNullValue()));

            // try and find the new contact using the group
            Iterable<Contact> foundContacts = contactRepository.findByGroups("friends");

            // check at least one contact was found
            assertThat(foundContacts, is(notNullValue()));

            boolean testContactWasFound = false;

            // iterate through the contacts
            for (Contact foundContact : foundContacts) {

                // check if the test contact was found
                if(foundContact.getGroups().contains("friends")) {
                    testContactWasFound = true;
                    break;
                }
            }

            assertThat(testContactWasFound, is(true));

        } finally {

            // clean up the contact
            contactRepository.delete(createdContact);
        }
    }
}
