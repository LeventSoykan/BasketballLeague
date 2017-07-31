package info;

import com.fbasketball.ContextData;
import group.League;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

public class Player implements Serializable, Comparable<Player>{

    private League league = League.getInstance();
    private int mPlayerNumber;
    private double mPointsPerGame;

    public int getPlayerNumber() {
        return mPlayerNumber;
    }

    public String getPlayerNo () { return "#" + mPlayerNumber; }

    public void setPlayerNumber(int playerNumber) {
        mPlayerNumber = playerNumber;
    }


    @Override
    public String toString() {
        if (mTeamName == null) {
            return "#" + mPlayerNumber +
                    " " + mFirstName  +
                    " " + mLastName;
        } else {
            return "#" + mPlayerNumber +
                    " " + mFirstName +
                    " " + mLastName +
                    " " + mTeamName;
        }
    }

    private String mFirstName;
    private String mLastName;
    private String mTeamName;
    private int mTotalPoints;


    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public String getPlayerName() { return mFirstName + " " + mLastName; }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public void setPointsPerGame(double pointsPerGame) {
        mPointsPerGame = pointsPerGame;
    }

    public Player(int playerNumber, String firstName, String lastName) {
        mPlayerNumber = playerNumber;
        mFirstName = firstName;

        mLastName = lastName;
    }

    public int getTotalPoints() {
        return mTotalPoints;
    }

    public double getPointsPerGame () {
        if (league.getGameCount() != 0) {
            mPointsPerGame = (double) mTotalPoints / league.getGameCount();
            return mPointsPerGame;
        } else {
            return 0;
        }
    }

    public void addPoints (int point){
        mTotalPoints += point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (getPlayerNumber() != player.getPlayerNumber()) return false;
        if (!getFirstName().equals(player.getFirstName())) return false;
        return getLastName().equals(player.getLastName());
    }

    @Override
    public int hashCode() {
        int result = getPlayerNumber();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        return result;
    }

    @Override
    public int compareTo(Player o) {
        if (this.getTotalPoints() > o.getTotalPoints()) {
            return -1;
        } if (this.getTotalPoints() < o.getTotalPoints()) {
            return 1;
        }
        if (this.mPlayerNumber > o.mPlayerNumber) {
            return -1;
        }
        if (this.mPlayerNumber < o.mPlayerNumber) {
            return 1;
        } else {return 0;}
    }
}
