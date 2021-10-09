package com.lms.app.repository;

import com.lms.app.entity.Lead;
import com.lms.app.entity.LeadView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LeadViewRepository extends JpaRepository<LeadView, Long> {
	/**
	 * Finds all reports according to the dates Specified.
	 * 
	 * @param from From Date
	 * @param to   To Date
	 * @return List of Reports.
	 */
	@Query(value = "select * from vw_leads where city like :city% and created_date between :from and :to order by created_date desc", nativeQuery = true)
	public List<LeadView> findAllByDate(@Param("from") Date from, @Param("to") Date to,@Param("city") String city, Pageable pageable);

	/**
	 * Finds all reports according to Dates specified and AppUser Id.
	 * 
	 * @param from From Date
	 * @param to   To Date
	 * @param id   AppUser Id
	 * @return List of Reports.
	 */
	@Query(value = "select * from vw_leads where city like :city% and  created_date between :from and :to and added_by =:id order by created_date desc", nativeQuery = true)
	public List<LeadView> findAllByDateAndId(@Param("from") Date from, @Param("to") Date to, @Param("id") Integer id,@Param("city") String city,
			Pageable pageable);

	/**
	 * Returns count of Lead reports between given dates.
	 * @param from From Date
	 * @param to TO date
	 * @return {@link Integer}
	 */
	@Query(value = "select count(*) from vw_leads where created_date between :from and :to", nativeQuery = true)
	public Integer findNumberOfReportsByDate(@Param("from") Date from, @Param("to") Date to);

	@Query(value = "select count(*) from vw_leads where created_date between :from and :to and added_by =:id", nativeQuery = true)
	public Integer findNumberOfReportsByDateAndId(@Param("from") Date from, @Param("to") Date to, @Param("id") Integer id);

}
