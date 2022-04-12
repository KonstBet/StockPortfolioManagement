package org.acme.resources;

import org.acme.domain.Authorization;
import org.acme.domain.Broker;
import org.acme.domain.Investor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationDTO {

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Long investorId;

    private Long brokerId;

    private Boolean isActive;

    public AuthorizationDTO() {}

    public AuthorizationDTO(Authorization authorization){

        this(authorization.getId(), authorization.getStartDate(), authorization.getEndDate(),
                authorization.getInvestor().getId(), authorization.getBroker().getId(), authorization.getActive());
    }

    public AuthorizationDTO(Long id, LocalDateTime startDate, LocalDateTime endDate, Long investorId,
                            Long brokerId, Boolean isActive) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.investorId = investorId;
        this.brokerId = brokerId;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public Long getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Long brokerId) {
        this.brokerId = brokerId;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public static List<AuthorizationDTO> listToDTOList(List<Authorization> authorizations){
        List<AuthorizationDTO> authorizationDTOS = new ArrayList<>();
        for(Authorization auth: authorizations){
            authorizationDTOS.add(new AuthorizationDTO(auth));
        }
        return authorizationDTOS;
    }
}
