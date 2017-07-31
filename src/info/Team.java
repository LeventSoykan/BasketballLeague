package info;

import com.fbasketball.ExcelFileOperator;
import com.fbasketball.RandomParam;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Team implements Serializable {
    private String mTeamName;
    private int mWinCount = 0;
    private Player mPlayer;
    private List<Player> mTeamPlayerList = new ArrayList<Player>();
    private RandomParam mRandomParam = new RandomParam();
    private Random mRandom = new Random();
    private int mTeamPoints;
    private int mScoreAgainst;
    private int mTeamScore;

    public int getTeamPoints() {
        return mTeamPoints;
    }

    public int getTeamScore() {
        return mTeamScore;
    }

    public Team(String teamName) {
        mTeamName = teamName;
    }

    public List<Player> createPlayers () {
     Scanner mScanner = new Scanner(System.in);
     for (int i=1; i<6; i++) {
         int playerNumber = 0;
         System.out.println("Please enter player number: ");
         String playerNumberString = mScanner.nextLine();
         while (!playerNumberString.matches("^\\d{2}$|^\\d{1}$")) {
             System.out.println("This is not a valid number. Please try again: ");
             playerNumberString = mScanner.nextLine();
         }
         playerNumber = Integer.parseInt(playerNumberString);
         System.out.println("Please enter player first name: ");
         String firstName = mScanner.nextLine();
         System.out.println("Please enter player last name: ");
         String lastName = mScanner.nextLine();
         mPlayer = new Player(playerNumber, firstName, lastName);
         mTeamPlayerList.add(mPlayer);
     }
     return mTeamPlayerList;
    }

    public List<Player> createRandomPlayers () {
        for (int i=1; i<6; i++) {
            int playerNumber = mRandomParam.randomNumber();
            String firstName = mRandomParam.randomWord();
            String lastName = mRandomParam.randomWord();
            mPlayer = new Player(playerNumber, firstName, lastName);
            mTeamPlayerList.add(mPlayer);
        }
        return mTeamPlayerList;
    }



    public List<Player> selectPlayersFromList (List<Player> allPlayerList) throws IOException {
        Scanner mScanner = new Scanner(System.in);
        int count = 0;
        System.out.println("List of players below: ");

        for (int i=1; i<3; i++) {
            for(Player player : allPlayerList) {
            count++;
            System.out.printf("%d %s %n", count, player.toString());
            }
            System.out.println("Please select next player: ");
            //Player choice = allPlayerList.get(mScanner.nextInt()-1);
            Player choice = allPlayerList.get(1);
            mTeamPlayerList.add(choice);
            allPlayerList.remove(choice);
            choice.setTeamName(getTeamName());
            count = 0;
        }
        return mTeamPlayerList;
    }

    public int netTeamScore () {
        return mTeamPoints - mScoreAgainst;
    }


    @Override
    public String toString() {
        return "" + mTeamName;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public List<Player> getTeamPlayerList() {
        return mTeamPlayerList;
    }

    public void setTeamPlayerList(List<Player> teamPlayerList) {
        mTeamPlayerList = teamPlayerList;
    }

    public int getWinCount() {
        return mWinCount;
    }

    public void addWinPoints() {
        mTeamPoints = mTeamPoints + 3;
        mWinCount++;

    }

    public void addLosePoints() {
        mTeamPoints++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;

        Team team = (Team) o;

        if (!getTeamName().equals(team.getTeamName())) return false;
        return getTeamPlayerList().equals(team.getTeamPlayerList());
    }

    @Override
    public int hashCode() {
        return getTeamName().hashCode();
    }

    public int getScoreAgainst() {
        return mScoreAgainst;
    }

    public void addScores(int scoreFor, int scoreAgainst) {
        mTeamScore = mTeamScore + scoreFor;
        mScoreAgainst = mScoreAgainst + scoreAgainst;
        updatePlayerPoints(scoreFor);
    }

    private void updatePlayerPoints (int scoreFor) {
        while (scoreFor > 0) {
        for (Player player : mTeamPlayerList)  {
            int randomPts = mRandom.nextInt(20);
            if (randomPts > scoreFor) {
               randomPts = scoreFor;
            }
            player.addPoints(randomPts);
            scoreFor -= randomPts;
        } }
    }

}
