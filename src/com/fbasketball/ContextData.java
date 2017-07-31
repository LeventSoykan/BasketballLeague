package com.fbasketball;

import group.League;
import info.Player;

public class ContextData {
    private static final ContextData instance = new ContextData();

    public static ContextData getInstance() {
        return instance;
    }

    public League mLeague = new League("First League");

    public League currentLeague () {
        return mLeague;
    }

}
