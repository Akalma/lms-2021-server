package com.lms.app.to;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="Request Model",description = "The request parameters which need to be provided during "
		+ "viewing ,downloading and getting count of the Lead reports.")
public class RequestTo implements Serializable {

	private static final long serialVersionUID = -1346852230107365526L;
	@ApiModelProperty(value = "This is the mandatory value "
			+ "which is needed during viewing,dowloading or getting count of Lead reports.",required=true)
	private Long fromDate;
	@ApiModelProperty(value = "This is mandatory value "
			+ "which is needed during viewing,downloading or getting count of Lead reports.",required=true)
	private Long toDate;
	@ApiModelProperty(value = "This will take AppUser Id. This is mandatory when Lead reports "
			+ "are required to view,download or count based on a specific user.")
	private Integer id;
	@ApiModelProperty(value = "This is page number to be given when viewing Lead reports")
	private int startPage;
	@ApiModelProperty(value = "This is number of data which is needed on a given page.")
	private int noOfData;
}
