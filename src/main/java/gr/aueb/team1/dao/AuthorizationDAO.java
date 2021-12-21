package gr.aueb.team1.dao;

import gr.aueb.team1.domain.AuthCapital;
import gr.aueb.team1.domain.AuthStock;
import gr.aueb.team1.domain.Authorization;

import java.util.List;

public interface AuthorizationDAO extends DAO<Authorization> {
    public List<Authorization> findAllByBrokerID(Integer id);
    public AuthStock findStockAuthById(Integer id);
    public AuthCapital findCapitalAuthById(Integer id);
    public List<Authorization> findAllByInvestorID(Integer id);
}
