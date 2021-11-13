package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AuthCapital")
public class AuthCapital extends Authorization {

	public AuthCapital(LocalDateTime startdate, LocalDateTime enddate, Integer amount) {
		super(startdate, enddate, amount);
	}
}
