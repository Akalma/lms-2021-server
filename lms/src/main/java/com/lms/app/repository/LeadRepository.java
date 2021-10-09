package com.lms.app.repository;

import java.util.Date;
import java.util.List;

import com.lms.app.entity.LeadView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lms.app.entity.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {

}
