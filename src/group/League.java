package group;

import com.fbasketball.ExcelFileOperator;
import com.fbasketball.Match;
import com.fbasketball.RandomParam;
import info.Player;
import info.Team;

import java.io.*;
import java.util.*;

public class League implements Serializable {
    public static final League instance = new League("First League");

    public static League getInstance() {
        return instance;
    }

    public String getLeagueName() {
        return mLeagueName;
    }

    public void setLeagueName(String leagueName) {
        mLeagueName = leagueName;
    }

    private String mLeagueName;

    public List<Team> getTeams() {        return mTeams;    }

    public void setTeams(List<Team> teams) {
        mTeams = teams;
    }

    public void setAllTeamsList(List<Team> allTeamsList) {
        mAllTeamsList = allTeamsList;
    }

    public void setAllPlayerList(List<Player> allPlayerList) {
        mAllPlayerList = allPlayerList;
    }

    public void setAllActivePlayers(List<Player> allActivePlayers) {
        mAllActivePlayers = allActivePlayers;
    }

    private List<Team> mTeams = new ArrayList<Team>();
    private List<Team> mAllTeamsList = new ArrayList<Team>();
    private List<Player> mAllPlayerList = new ArrayList<Player>();
    private List<Player> mAllActivePlayers = new ArrayList<Player>();
    private List<String> mListOfPlayerStat = new ArrayList<String>();
    private RandomParam mRandom = new RandomParam();
    private final Scanner mScanner = new Scanner(System.in);
    private int mGameCount;
    private Team mWinnerTeam;

    public List<String> getMatchResults() {
        return mMatchResults;
    }

    public Team getWinnerTeam () {
        Collections.sort(mTeams, new Comparator<Team>(){
            public int compare(Team tm1, Team tm2) {
                if(tm1.getTeamPoints() > tm2.getTeamPoints()) { return -1;}
                if(tm1.getTeamPoints() < tm2.getTeamPoints()) {return 1;}
                if(tm1.netTeamScore() > tm2.netTeamScore()) {return  -1;}
                if(tm1.netTeamScore() < tm2.netTeamScore()) {return 1;}
                else {return 0;}
            }
        });
        return mTeams.get(0);
    }

    private List<String> mMatchResults = new ArrayList<String>();
    private ExcelFileOperator mEfo = new ExcelFileOperator();


    public League(String leagueName) {
        mLeagueName = leagueName;
    }

    public List<Team> createTeams() {
        mTeams = new ArrayList<Team>();
        for (int i = 1; i<11; i++) {
            System.out.println("Please enter Teamname: ");
            Team team = new Team(mScanner.nextLine());
            team.createPlayers();
            mTeams.add(team);
        }
        return mTeams;
    }

    private void addToGameCount () {
        mGameCount++;
    }

    public int getGameCount () {
        return mGameCount;
    }

    public void importAll() {
        try {
            mAllTeamsList = mEfo.importTeams();
            mAllPlayerList = mEfo.importPlayers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Player> getAllPlayerList() {
        return mAllPlayerList;
    }

    public List<Team> getAllTeamsList() {
        return mAllTeamsList;
    }

    public List<Team> selectTeamsFromFile () throws IOException {
        System.out.println("List of teams below: ");
        int count = 0;

        for (int i=1; i<3; i++) {
            for (Team team: mAllTeamsList) {
            count++;
            System.out.printf("%d %s %n", count, team.getTeamName());
            }
            System.out.printf("Please select team %d %n", i);
            //Team team = mAllTeamsList.get(mScanner.nextInt()-1);
            Team team = mAllTeamsList.get(3);
            mTeams.add(team);
            mAllTeamsList.remove(team);
            team.selectPlayersFromList(mAllPlayerList);
            count = 0;
        }
        return mTeams;
    }

    public List <Team> selectTeamsFromSrt(int teamCount) throws ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(new File("LeagueData.srt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Team> savedTeams = (ArrayList<Team>) ois.readObject();
            if(mTeams == null) {
                mTeams = new ArrayList<Team>();
            }
            for (Team team : savedTeams) {
                System.out.printf("%d %n %s %n%n", savedTeams.indexOf(team), team.getTeamName());
            }
            for (int i =1; i<teamCount+1; i++) {
                System.out.printf("Please select team %d: ", i);
                int teamIndex = mScanner.nextInt();
                if (teamIndex > savedTeams.size()) {
                    System.out.println("Please select again: ");
                } else {
                    mTeams.add(savedTeams.get(teamIndex));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mTeams;
    };

    public void playAllMatches() {
        for (Team team : mTeams) {
            for (int i = 1; i<mTeams.size()+1; i++) {
                if (!team.equals(mTeams.get(i - 1))) {
                    Match match = new Match(team, mTeams.get(i-1));
                    match.playMatch();
                    team.addScores(match.getHomeScore(), match.getAwayScore());
                    mTeams.get(i-1).addScores(match.getAwayScore(), match.getHomeScore());
                    match.winnerTeam().addWinPoints();
                    match.loserTeam().addLosePoints();
                    mMatchResults.add(match.matchResult());
                    addToGameCount();
                }
            }

        }
    }



    public void displayMatchResults () {
        for (String matchResult: mMatchResults) {
            System.out.println(matchResult);
        }
    }

    public void displayTeamStandings() {
        System.out.println("TeamName   Points  TotalFor TotalAgainst");
        Collections.sort(mTeams, new Comparator<Team>() {
            public int compare(Team tm1, Team tm2) {
                if(tm1.getTeamPoints() > tm2.getTeamPoints()) { return -1;}
                if(tm1.getTeamPoints() < tm2.getTeamPoints()) {return 1;}
                if(tm1.netTeamScore() > tm2.netTeamScore()) {return  -1;}
                if(tm1.netTeamScore() < tm2.netTeamScore()) {return 1;}
                else {return 0;}
            }
        });
        for (Team team: mTeams) {
            System.out.printf("%s   %d    %d   %d %n", team.getTeamName(), team.getTeamPoints(),
                    team.getTeamScore(), team.getScoreAgainst() );
        }
    }

    public List<Player> listAllActivePlayers () {
        mAllActivePlayers.clear();
        for (Team team: mTeams) {
            mAllActivePlayers.addAll(team.getTeamPlayerList());
            Collections.sort(mAllActivePlayers, new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    return o1.compareTo(o2);
                }
            });
        }
        return mAllActivePlayers;
    }

    /*public void displayListOfPlayerStat () {
        setAllPlayers();
        System.out.println("Player - Team - Total Points  ");
        for(Player player: mAllActivePlayers) {
            String playerStat = String.format("%s   %d%n", player.toString(), player.getTotalPoints());
            mListOfPlayerStat.add(playerStat);
        }
        return mListOfPlayerStat;
    }*/



    public List<Team> addRandomTeams () {
        mTeams = new ArrayList<Team>();
        for (int i = 1; i<11; i++) {
            Team team = new Team(mRandom.randomWord());
            team.createRandomPlayers();
            mTeams.add(team);
        }
        return mTeams;
    }

    public void  loadTeams () throws IOException, ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(new File("BigLeague.srt"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            mTeams = (ArrayList<Team>) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTeams () throws FileNotFoundException {
        try {
            FileOutputStream fos = new FileOutputStream("BigLeague.srt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mTeams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSelectedTeams () throws FileNotFoundException {
        try {
            FileOutputStream fos = new FileOutputStream("SelectedTeams.srt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mTeams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public String toString() {
        return "League{" +
                "LeagueName='" + mLeagueName + '\'' +
                ", Teams=" + mTeams +
                '}';
    }
}
