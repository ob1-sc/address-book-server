package com.emc.documentum.sample.domain;

import com.emc.documentum.springdata.entitymanager.mapping.DctmAttribute;
import com.emc.documentum.springdata.entitymanager.mapping.DctmEntity;
import org.springframework.data.annotation.Id;

/**
 * Contact domain object represents contact repository object
 *
 * @author Simon O'Brien
 */
@DctmEntity(repository = "contact")
public class Contact {

	@Id
	protected String id;

	@DctmAttribute(value="object_name")
	private String name;

	private String email;

	public Contact(String name, String email) {
		this.email =  email;
		this.name = name;
	}

	public Contact() {
	}

	/**
	 * Get the ID
	 *
	 * @return the ID
	 */
    public String getId() {
        return id;
    }

	/**
	 * Set the ID
	 *
	 * @param id the ID
	 */
    public void setId(String id) {
        this.id = id;
    }

	/**
	 * Get the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name
	 *
	 * @param name the name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the email address
	 *
	 * @return the email address
	 */
    public String getEmail() {
        return email;
    }

	/**
	 * Set the email address
	 *
	 * @param email the email address
	 */
    public void setEmail(String email) {
        this.email = email;
    }
}
