package com.emc.documentum.sample.domain;

import com.emc.documentum.springdata.entitymanager.mapping.DctmAttribute;
import com.emc.documentum.springdata.entitymanager.mapping.DctmEntity;
import com.rsa.cryptoj.o.gr;
import org.springframework.data.annotation.Id;

import java.util.List;

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
	private String telephone;
	private List<String> groups;

	public Contact(String name, String email, String telephone, List<String> groups) {
		this.email =  email;
		this.name = name;
		this.telephone = telephone;
		this.groups = groups;
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

	/**
	 * Get the telephone
	 *
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Set the telephone
	 *
	 * @param telephone the telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Get the groups
	 *
	 * @return the groups
	 */
	public List<String> getGroups() {
		return this.groups;
	}

	/**
	 * Set the groups
	 *
	 * @param groups the groups
	 */
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
}
