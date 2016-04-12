package com.emc.documentum.sample.domain;

import com.emc.documentum.springdata.entitymanager.mapping.DctmAttribute;
import com.emc.documentum.springdata.entitymanager.mapping.DctmEntity;
import org.springframework.data.annotation.Id;

@DctmEntity(repository = "address")
public class Address {

	@Id
	protected String id;
	private String name;
	private String email;

	public Address(String name, String email) {
		this.email =  email;
		this.name = name;
	}

	public Address() {
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
