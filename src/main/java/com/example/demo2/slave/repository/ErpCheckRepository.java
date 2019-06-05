package com.example.demo2.slave.repository;

import com.example.demo2.slave.model.ErpCheck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ErpCheckRepository extends CrudRepository<ErpCheck, Long> {
    /**
     * findCheckById
     * @param id id
     * @return ErpCheck
     */
	@Query(nativeQuery = true, value="select * from erp_check a where a.id = ?1")
	ErpCheck findCheckById(Long id);
}
