package site.dopplerxd.matchingsystem.service.impl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchingPool extends Thread {
    private static List<Player> players = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();

    public void addPlayer(Integer userId, Integer rating) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, 0));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            players.removeIf(player -> player.getUserId().equals(userId));
        } finally {
            lock.unlock();
        }
    }

    private void increaseWaitTime() {
        lock.lock();
        try {
            for (Player player : players) {
                player.setWaitTime(player.getWaitTime() + 1);
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean checkMatch(Player a, Player b) { // 判断两名玩家是否匹配
        return Math.abs(a.getRating() - b.getRating()) <= 100;
    }

    private void sendResult(Player a, Player b) { // 返回匹配结果

    }

    private void matchPlayers() { // 尝试匹配所有玩家
        boolean[] used = new boolean[players.size()];
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                increaseWaitTime();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
