package com.lms.app.value;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExceptionDetails {
	private HttpStatus status;
	private String message;
	private LocalDateTime timestamp;
}
