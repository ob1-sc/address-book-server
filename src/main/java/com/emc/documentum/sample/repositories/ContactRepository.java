package com.emc.documentum.sample.repositories;

import com.emc.documentum.sample.domain.Contact;
import com.emc.documentum.springdata.repository.DctmRepositoryWithContent;
import com.emc.documentum.springdata.repository.Query;

/**
 * Basic Contact Spring Data Repository used for interacting with contact repository objects
 *
 * @author Simon O'Brien
 */
public interface ContactRepository extends DctmRepositoryWithContent<Contact, String> {

    /**
     * Find all contacts where the name matches the given value
     *
     * @param value The value to use when searching
     * @return query results
     *
     * TODO: remove override once queries are built via reflection
     */
    @Query("select r_object_id, object_name, email, telephone, groups, r_content_size from contact where object_name = \'%s\'")
    public Iterable<Contact> findByName(String value);

    /**
     * Find all contacts
     *
     * NOTE: this is an override of the OOTB DctmRepository behaviour as repeating value attributes are not being returned
     *
     * TODO: remove override once repeating attribute bug is fixed
     *
     * @return query results
     */
    @Override
    @Query("select r_object_id, object_name, email, telephone, groups, r_content_size from contact")
    public Iterable<Contact> findAll();

    /**
     * Find all contacts where any group is equal to the given value
     *
     * @param value The value to use when searching
     * @return query results
     */
    @Query("select r_object_id, object_name, email, telephone, groups, r_content_size from contact where any groups = \'%s\'")
    public Iterable<Contact> findByGroups(String value);

    /**
     * Find all contacts where the name contains the given value
     *
     * @param value The value to use when searching
     * @return query results
     */
    @Query("select r_object_id, object_name, email, telephone, groups, r_content_size from contact where object_name like \'%%%s%%\'")
    public Iterable<Contact> findByNameContaining(String value);
}
