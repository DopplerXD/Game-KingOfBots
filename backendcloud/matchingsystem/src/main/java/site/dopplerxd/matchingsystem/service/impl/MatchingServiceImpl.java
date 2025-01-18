package site.dopplerxd.matchingsystem.service.impl;

import org.springframework.stereotype.Service;
import site.dopplerxd.matchingsystem.service.MatchingService;
import site.dopplerxd.matchingsystem.service.impl.utils.MatchingPool;

@Service
public class MatchingServiceImpl implements MatchingService {
    public final static MatchingPool matchingPool = new MatchingPool();

    @Override
    public String addPlayer(Integer userId, Integer rating) {
        System.out.println("addPlayer: " + userId + " " + rating);
        matchingPool.addPlayer(userId, rating);
        return "add player success";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("removePlayer: " + userId);
        matchingPool.removePlayer(userId);
        return "remove player success";
    }
}
