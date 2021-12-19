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

    public List<StockHolding> showStocks(Integer userid) {

        User u = getUser(userid);

        List<StockHolding> results = new ArrayList<StockHolding>();
        for (StockHolding sh : u.getStockHoldings().values()) {
            results.add(shd.findById(sh.getId()));
        }

        return results;
    }

    public StockHolding getStock(Integer id) {

        StockHolding result = null;
        result = shd.findById(id); ;

        return result;
    }

    private User getUser(Integer userid) {
        UserDAO ud = new UserDAOImpl();
        User u = ud.findById(userid);
        return u;
    }
}
