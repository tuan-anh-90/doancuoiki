package com.project.urban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(
		basePackageClasses = { DoanCalendaApplication.class, Jsr310JpaConverters.class }
	)
@SpringBootApplication
public class DoanCalendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoanCalendaApplication.class, args);
	}

}
