package gr.aueb.team1.service;

import gr.aueb.team1.dao.StockHoldingDAO;
import gr.aueb.team1.dao.UserDAO;
import gr.aueb.team1.dao.impl.UserDAOImpl;
import gr.aueb.team1.domain.StockHolding;
import gr.aueb.team1.domain.User;

import java.util.ArrayList;
import java.util.List;

public class StockHoldingService {

    private StockHoldingDAO shd;

    public StockHoldingService(StockHoldingDAO shd) {
        this.shd = shd;
    }

    public List<StockHolding> showStockHoldings(Integer userid) {

        User u = getUser(userid);

        List<StockHolding> results = new ArrayList<>();
        for (StockHolding sh : u.getStockHoldings().values()) {
            results.add(shd.findById(sh.getId()));
        }

        return results;
    }

    public StockHolding getStockHolding(Integer userid,Integer shid) {

        User u = getUser(userid);

        StockHolding result = null;
        result = shd.findById(shid);

        if (result != null)
            if (result.getUser().equals(u))
                return result;

        return null;
    }

    private User getUser(Integer userid) {
        UserDAO ud = new UserDAOImpl();
        User u = ud.findById(userid);
        return u;
    }
}
