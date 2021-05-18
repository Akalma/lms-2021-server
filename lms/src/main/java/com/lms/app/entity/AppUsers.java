package com.lms.app.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lms.app.to.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "appusers")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class AppUsers implements Serializable {

	private static final long serialVersionUID = -4823506842358084496L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "type")
	private Long type;
	@Column(name = "created")
	private Date createdOn;
	@Column(name = "remarks")
	private String remarks;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

}
