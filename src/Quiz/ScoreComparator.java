package Quiz;

import Interfaces.Player;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by Ahmed on 2/27/2015.
 */
public class ScoreComparator implements Comparator<Player> {
    private Map<Player, Integer> map;

    public ScoreComparator(Map<Player, Integer> map) {
        this.map = map;
    }

    @Override
    public int compare(Player o1, Player o2) {
        if(map.get(o1) > map.get(o2)){
            return -1;
        }else if (map.get(o1) < map.get(o2)) {
            return 1;
        }else {
            return 0;
        }
    }
}
