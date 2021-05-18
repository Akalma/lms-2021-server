package com.lms.app.to;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppUsersTo implements Serializable {

	private static final long serialVersionUID = -5926451873659768275L;

	private Long id;
	private String name;
	private String email;
	private Long type;
	private long createdOn;
	private String remarks;
	private Status status;
	
}
