package com.emc.documentum.sample.repositories;

import com.emc.documentum.sample.TestConfig;
import com.emc.documentum.sample.domain.Contact;
import com.emc.documentum.springdata.core.Documentum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test
    public void createContact() {

        Contact contact = new Contact();
        contact.setName("Simon O'Brien");
        contact.setEmail("simon@the-obriens.net");
        Contact createdContact = contactRepository.save(contact);

        assertThat(createdContact, is(notNullValue()));
        assertThat(createdContact.getName(), is(equalTo(contact.getName())));
        assertThat(createdContact.getEmail(), is(equalTo(contact.getEmail())));
    }
}
