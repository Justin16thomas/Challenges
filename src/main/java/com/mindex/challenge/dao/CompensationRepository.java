package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Compensation documents.
 * We provide a finder method to lookup a compensation record by the associated employee's employeeId.
 * The query method name 'findByEmployeeEmployeeId' targets the nested employee.employeeId field.
 */
@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    Compensation findByEmployeeEmployeeId(String employeeId);
}
