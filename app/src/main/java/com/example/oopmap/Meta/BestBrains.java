package com.example.oopmap.Meta;

import android.util.Pair;

import com.example.oopmap.Crits.Brain;
import com.example.oopmap.Crits.Creature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestBrains {
    static Map<Class, Pair<Double, List<Brain>>> best = new HashMap<>();

    public static void addBrain(Creature crit, Class cl) {
        if (best.get(cl) != null) {
            if (((crit.getLifetime() + crit.getHunger() / 10) * (crit.getChildAmount() * 0.1 + 1)) > best.get(cl).first) {
                best.put(cl, Pair.create((crit.getLifetime() + crit.getHunger() / 10) * (crit.getChildAmount() * 0.1 + 1), crit.getBrains()));
            }
        } else
            best.put(crit.getClass(), Pair.create((crit.getLifetime() + crit.getHunger() / 10) * (crit.getChildAmount() * 0.1 + 1), crit.getBrains()));
    }

    public static List<Brain> getBest(Class cl) {
        return best.get(cl).second;
    }

    public static boolean hasClass(Class cl) {
        return best.containsKey(cl);
    }
}
