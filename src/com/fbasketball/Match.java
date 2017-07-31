package com.fbasketball;

import info.Team;

public class Match {
    private Team mHomeTeam;
    private Team mAwayTeam;
    private int mHomeScore;
    private int mAwayScore;
    private RandomParam mRandomParam = new RandomParam();

    public Match(Team homeTeam, Team awayTeam) {
        mHomeTeam = homeTeam;
        mAwayTeam = awayTeam;
    }

    public void playMatch () {
        setHomeScore();
        setAwayScore();
        //showFinalScore();
        //System.out.println("And the winner is: " + winnerTeam().getTeamName());
    };

    public String matchResult () {
        return String.format("%n %s : %d, %s: %d", mHomeTeam.getTeamName(), mHomeScore,
                mAwayTeam.getTeamName(), mAwayScore);
    }

    private void showFinalScore() {
        System.out.printf("%n %s : %d, %s: %d %n%n", mHomeTeam.getTeamName(), mHomeScore,
                mAwayTeam.getTeamName(), mAwayScore);
    }

    public void setHomeScore() {
        mHomeScore = mRandomParam.randomScore();
    }

    public void setAwayScore() {
        mAwayScore = mRandomParam.randomScore();
    }

    public int getHomeScore() {
        return mHomeScore;
    }

    public int getAwayScore() {
        return mAwayScore;
    }

    public Team winnerTeam () {
        if (mHomeScore > mAwayScore) {
            return mHomeTeam;
        } else { return mAwayTeam; }
    };


    @Override
    public String toString() {
        return "Match: " + mHomeTeam +
                " vs. " + mAwayTeam ;
    }

    public Team getHomeTeam() {
        return mHomeTeam;
    }

    public Team getAwayTeam() {
        return mAwayTeam;
    }

    public Team loserTeam() {
        if (mHomeScore <= mAwayScore) {
        return mHomeTeam;
    } else { return mAwayTeam; }
    }
}
