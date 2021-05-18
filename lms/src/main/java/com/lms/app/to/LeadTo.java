package com.lms.app.to;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "Base Entity Model",description = "The entity object which needed to be given "
		+ "during saving Lead data")
public class LeadTo implements Serializable {
	private static final long serialVersionUID = -1030522666688378860L;
	@ApiModelProperty(value = "This is the primary key of the entity.This would be auto generated when requested "
			+ " for saving Lead data.")
	private Long id;
	@ApiModelProperty(value = "This is first name to be given.",required=true)
	private String firstName;
	@ApiModelProperty(value = "This is last name to be given.",required=true)
	private String lastName;
	@ApiModelProperty(value = "This is mobile number to be given.",required=true)
	private String mobile;
	@ApiModelProperty(value = "This is Area to be given.",required = true)
	private String area;
	@ApiModelProperty(value = "This is Existing brodband to be given.",required=true)
	private String existingBroadband;
	@ApiModelProperty(value = "This is Lead type to be given.",required=true)
	private String leadType;
	@ApiModelProperty(value = "This will auto save current timestamp for Lead data sent for saving.")
	private long date;
	@ApiModelProperty(value = "This is remarks to be given while saving Lead data.")
	private String remarks;
	@ApiModelProperty(value = "This is association which will take AppUser id."
			+ " Id of logged in user need to be given to know who is saving the Lead data",required = true)
	private String addedBy;


}
