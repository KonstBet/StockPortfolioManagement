package org.acme.services;

import org.acme.domain.*;
import org.acme.repositories.AuthorizationRepository;
import org.acme.repositories.UserRepository;
import org.acme.resources.AuthorizationDTO;

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

    public List<AuthorizationDTO> list(Integer userid) {
        List<AuthorizationDTO> authorizationDTOList;

        List <Authorization> authorizationList;

        authorizationList = authorizationRepository.findAllAuthCapitalsByUserID(userid);
        authorizationDTOList = AuthorizationListToAuthorizationDTOList(authorizationList,"AuthCapital");

        authorizationList = authorizationRepository.findAllAuthStocksByUserID(userid);
        authorizationDTOList.addAll(AuthorizationListToAuthorizationDTOList(authorizationList,"AuthStock"));

        return authorizationDTOList;
    }

    public AuthorizationDTO get(Integer id) {
        AuthCapital ac = authorizationRepository.findAuthCapitalByID(id);
        if (ac != null)
            return new AuthorizationDTO(ac.getStartdate(),ac.getEnddate(),ac.getInvestor().getId(),
                ac.getBroker().getId(),"AuthCapital",ac.getAmount());

        AuthStock as = authorizationRepository.findAuthStockByID(id);
        if (as != null) {
            return new AuthorizationDTO(as.getStartdate(),as.getEnddate(),as.getInvestor().getId(),
                    as.getBroker().getId(),"AuthCapital",Double.valueOf(as.getAmount()),as.getStockholdingid());
        }

        return null;
    }

    public Boolean create(AuthorizationDTO authorizationDTO) {
        if (authorizationDTO.getType().equals("AuthCapital")) {
            Investor investor = userRepository.findInvestorByID(authorizationDTO.getInvestorid());
            Broker broker = userRepository.findBrokerByID(authorizationDTO.getBrokerid());

            AuthCapital authCapital = new AuthCapital(investor,broker, LocalDateTime.now(),authorizationDTO.getEnddate(),authorizationDTO.getAmount());

            investor.getAuthorizations().add(authCapital);
            investor.setCommittedBalance(investor.getCommittedBalance() + authCapital.getAmount());
            broker.getAuthorizations().add((authCapital));

            authorizationRepository.saveAuthorization(authCapital);
            return true;
        }
        if (authorizationDTO.getType().equals("AuthStock")) {
            Investor investor = userRepository.findInvestorByID(authorizationDTO.getInvestorid());
            Broker broker = userRepository.findBrokerByID(authorizationDTO.getBrokerid());

            AuthStock authStock = new AuthStock(investor,authorizationDTO.getStockholdingid(),broker, LocalDateTime.now()
                    ,authorizationDTO.getEnddate(),authorizationDTO.getAmount().intValue());

            investor.getAuthorizations().add(authStock);
            broker.getAuthorizations().add((authStock));

            authorizationRepository.saveAuthorization(authStock);
            return true;
        }
        return false;
    }





    //--------------------------------------------------------------------------

    private List<AuthorizationDTO> AuthorizationListToAuthorizationDTOList(List<Authorization> authorizationList, String type) {
        List<AuthorizationDTO> authorizationDTOList = new ArrayList<>();

        for(Authorization auth : authorizationList) {

            if (type.equals("AuthCapital")) {//AuthCapital
                AuthCapital ac = (AuthCapital) auth;
                authorizationDTOList.add(new AuthorizationDTO(ac.getStartdate(),ac.getEnddate(),ac.getInvestor().getId(),
                        ac.getBroker().getId(),"AuthCapital",ac.getAmount()));
            }
            else if (type.equals("AuthStock")) {//AuthStock
                AuthStock as = (AuthStock) auth;
                authorizationDTOList.add(new AuthorizationDTO(as.getStartdate(),as.getEnddate(),as.getInvestor().getId(),
                        as.getBroker().getId(),"AuthCapital",Double.valueOf(as.getAmount()),as.getStockholdingid()));
            }
            else return null;
        }
        return authorizationDTOList;
    }
}
