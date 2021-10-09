package com.lms.app.repository;

import java.util.Date;
import java.util.List;

import com.lms.app.entity.LeadView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.lms.app.entity.Lead;

@Repository
public interface DownloadLeadCsvRepository extends JpaRepository<Lead, Long> {

	@Query(value = "select * from vw_leads where created_date between :from and :to and city=:city order by created_date desc", nativeQuery = true)
	public List<LeadView> findLeadByDate(@Param("from") Date from, @Param("to") Date to, @Param("city") String city);

	@Query(value = "select * from vw_leads where created_date between :from and :to and added_by =:id and city=:city order by created_date desc", nativeQuery = true)
	public List<LeadView> findAllByDateAndId(@Param("from") Date from, @Param("to") Date to, @Param("id") Integer id,@Param("city") String city);

}
