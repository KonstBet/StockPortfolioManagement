package com.example.app.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AuthCapital")
public class AuthCapital extends Authorization {

	public AuthCapital(Investor investor, Broker broker, LocalDateTime startdate, LocalDateTime enddate, Integer amount) {
		super(investor, broker, startdate, enddate, amount);
	}
}
