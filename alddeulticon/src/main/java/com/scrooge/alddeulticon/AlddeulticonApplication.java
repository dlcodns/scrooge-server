package com.scrooge.alddeulticon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.scrooge.alddeulticon.domain") // 엔티티가 위치한 최상위 패키지
public class AlddeulticonApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlddeulticonApplication.class, args);
	}

}
