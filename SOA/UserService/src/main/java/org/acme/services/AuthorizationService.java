package org.acme.services;

import org.acme.domain.*;
import org.acme.repositories.AuthorizationRepository;
import org.acme.repositories.UserRepository;
import org.acme.resources.AuthorizationDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AuthorizationService {

    @Inject
    AuthorizationRepository authorizationRepository;

    @Inject
    UserRepository userRepository;

    public AuthorizationDTO findById(Long authorizationId){

        Authorization authorization = authorizationRepository.findAuthorizationById(authorizationId);

        if(authorization == null ) return null;

        return new AuthorizationDTO(authorization);

    }

    public List<AuthorizationDTO> listInvestorAuthorizations(Long userId) {

        List<Authorization> authorizations;

        authorizations = authorizationRepository.findInvestorAuthorizations(userId);
        if(authorizations == null) return null;


        return AuthorizationDTO.listToDTOList(authorizations);
    }

    public List<AuthorizationDTO> listBrokerAuthorizations(Long userId) {

        List<Authorization> authorizations;

        authorizations = authorizationRepository.findBrokerAuthorizations(userId);
        if(authorizations == null) return null;


        return AuthorizationDTO.listToDTOList(authorizations);
    }

    public Boolean createAuthorization(AuthorizationDTO authorizationDTO){

        Investor investor = userRepository.findInvestorByID(authorizationDTO.getInvestorId());
        Broker broker = userRepository.findBrokerByID(authorizationDTO.getBrokerId());

        Authorization authorization = new Authorization(investor, broker,
                authorizationDTO.getStartDate(), authorizationDTO.getEndDate(),
                authorizationDTO.getActive());

        return authorizationRepository.saveAuthorization(authorization);

    }

    public Boolean isAuthorized(Long investorId, Long brokerId){

        List<Authorization> authorizations = authorizationRepository.findPairAuthorizationList(investorId, brokerId);

        if(authorizations.size() == 0) return false;


        // Parse authorization list (there could be multiple instances)
        for(Authorization authorization: authorizations){
            // if auth is not active continue
            if(!authorization.getActive()) continue;
            LocalDateTime now = LocalDateTime.now();

            //if auth is not active yet, continue
            if(now.isBefore(authorization.getStartDate())) continue;

            // if auth is expired, continue
            if(now.isAfter(authorization.getEndDate())) continue;

            return true;
        }

        return false;



    }
}
