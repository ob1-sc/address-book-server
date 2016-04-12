package com.emc.documentum.sample.domain;

import com.emc.documentum.springdata.entitymanager.mapping.DctmAttribute;
import com.emc.documentum.springdata.entitymanager.mapping.DctmEntity;
import org.springframework.data.annotation.Id;

@DctmEntity(repository = "students")
public class Student {
    @Id
	private String id;

    private String name;
	private String department;
    private int rollnumber;
	private String email;
    
    @DctmAttribute(value="ista")
    private Boolean isTA;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



	public int getRollnumber() {
		return rollnumber;
	}



	public void setRollnumber(int rollnumber) {
		this.rollnumber = rollnumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Boolean getIsTA() {
		return isTA;
	}

	public void setIsTA(Boolean isTA) {
		this.isTA = isTA;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Student(String name, String email, String department, Boolean isTA, int rollnumber) {
		this.email =  email;
		this.name = name;
		this.department = department;
		this.isTA = isTA;
		this.rollnumber = rollnumber;
	}
	
	public Student() {
	}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
