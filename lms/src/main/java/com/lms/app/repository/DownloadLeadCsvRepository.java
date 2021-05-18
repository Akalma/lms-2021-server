package com.lms.app.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lms.app.entity.Lead;

@Repository
public interface DownloadLeadCsvRepository extends JpaRepository<Lead, Long> {

	@Query(value = "select * from lead where creared_date between :from and :to order by creared_date desc", nativeQuery = true)
	public List<Lead> findLeadByDate(@Param("from") Date from, @Param("to") Date to);

	@Query(value = "select * from lead where creared_date between :from and :to and added_by =:id order by creared_date desc", nativeQuery = true)
	public List<Lead> findAllByDateAndId(@Param("from") Date from, @Param("to") Date to, @Param("id") Integer id);

}
