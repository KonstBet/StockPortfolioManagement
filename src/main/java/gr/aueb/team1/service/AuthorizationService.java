package gr.aueb.team1.service;

import gr.aueb.team1.dao.AuthorizationDAO;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.*;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationService {

    private AuthorizationDAO ad;

    public AuthorizationService(AuthorizationDAO ad) {
        this.ad = ad;
    }

    public List<Authorization> showAuthorizations(Integer userid) {

        User u = getUser(userid);

        List<Authorization> results = new ArrayList<>();
        for (Authorization a : u.getAuthorizations()) {
            results.add(ad.findById(a.getId()));
        }

        return results;
    }

    public AuthStock getAuthStock(Integer userid, Integer aid) {

        User u = getUser(userid);

        AuthStock result = null;
        result = ad.findStockAuthById(aid);

        if (result.getInvestor().getId().equals(u.getId()) || result.getBroker().getId().equals(u.getId()))
            return result;

        return null;
    }

    public AuthCapital getAuthCapital(Integer userid, Integer aid) {

        User u = getUser(userid);

        AuthCapital result = null;
        result = ad.findCapitalAuthById(aid);

        if (result.getInvestor().getId().equals(u.getId()) || result.getBroker().getId().equals(u.getId()))
            return result;

        return null;
    }



    private User getUser(Integer userid) {
        UserDAO ud = new UserDAOImpl();
        User u = ud.findById(userid);
        return u;
    }

    private Broker getBroker(Integer userid) {
        UserDAO ud = new UserDAOImpl();
        Broker u = ud.findBrokerById(userid);
        return u;
    }

    private Investor getInvestor(Integer userid) {
        UserDAO ud = new UserDAOImpl();
        Investor u = ud.findInvestorById(userid);
        return u;
    }

//    Broker broker = getBroker(userid);
//        if (broker != null) {
//        List<Authorization> results = new ArrayList<>();
//        for (Authorization sh : broker.getAuthorizations()) {
//            results.add(ad.findById(sh.getId()));
//        }
//
//        return results;
//    }
//        else {
//
//    }
}
