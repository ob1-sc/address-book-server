package com.emc.documentum.sample.repositories;

import com.emc.documentum.sample.domain.Student;
import com.emc.documentum.springdata.repository.DctmRepository;

public interface AddressRepository extends DctmRepository<Student, String> {
	

}
