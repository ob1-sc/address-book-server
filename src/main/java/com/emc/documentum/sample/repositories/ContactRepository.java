package com.emc.documentum.sample.repositories;

import com.emc.documentum.sample.domain.Contact;
import com.emc.documentum.springdata.repository.DctmRepository;

public interface ContactRepository extends DctmRepository<Contact, String> {
	

}
