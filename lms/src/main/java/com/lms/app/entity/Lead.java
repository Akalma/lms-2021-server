package com.lms.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lead")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Lead implements Serializable {
	private static final long serialVersionUID = 8285246441163008859L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "area")
	private String area;
	@Column(name = "existing_broadband")
	private String existingBroadband;
	@Column(name = "lead_type")
	private String leadType;
	@Column(name = "creared_date")
	private Date date;
	@Column(name = "remarks")
	private String remarks;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "added_by", referencedColumnName = "id", nullable = false)
	private AppUsers appUsers;

}
