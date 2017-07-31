package com.fbasketball;

import group.League;
import info.Player;
import info.Team;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class Controller {



    //TODO: Make the user select filename and destination.
    //TODO: StringProperty oku;
    //TODO: Learn about Serialver
    //TODO: Use timeline animation for size of initial image (should grow by time)

    private League league = League.getInstance();
    private String leagueName;
    public static boolean leaguePlayed = false;
    private Dialog<Team> mTeamDialog;
    private Dialog<Player> mPlayerDialog;
    public Stage mStage;
    private ExcelFileOperator efo = new ExcelFileOperator();


    @FXML
    private ListView<String> mStats;

    @FXML
    private VBox container;

    @FXML
    private TextField leagueNameText;

    @FXML
    private Button leagueButton;

    @FXML
    public Button startButton;

    @FXML
    private Button playerPageButton;

    @FXML
    private Button teamPageButton;

    @FXML
    private ImageView mainImage;

    @FXML
    private Label resultsLabel;

    @FXML
    private DialogPane promptCreate;

    @FXML
    private ImageView ballImage;

    @FXML
    private Button downloadResults;

    @FXML
    private Label winnerLabel;


    @FXML
    public void initialize() {
        //league.importAll();
        if (leaguePlayed) {
            leagueButton.setText("League already played");
            Team winner = league.getWinnerTeam();
            winnerLabel.setText("WINNER: " + winner.getTeamName());
            teamPageButton.setVisible(true);
            playerPageButton.setVisible(true);
            downloadResults.setVisible(true);
        }
    }


    public Controller() {
    }

    public void updatePlayerStats (List<Player> players) {
        mStats.getItems().clear();
        if (players != null) {
            for (Player player : players) {
                String playerStat = String.format("%s   %d%n", player.toString(), player.getTotalPoints());
                mStats.getItems().add(playerStat);
            }
        }
        mStats.setOrientation(Orientation.VERTICAL);

    }

    public void updateMatchResults (List<String> strings) {
        mStats.getItems().clear();
        if (strings != null) {
            for (String string: strings) {
                mStats.getItems().add(string);
            }
        }
        mStats.setOrientation(Orientation.VERTICAL);

    }

    public void updateTeamStandings (List<Team> teams) {
        mStats.getItems().clear();
        mStats.getItems().add(String.format("TeamName   Points  TotalFor TotalAgainst"));
        if (teams != null) {
            Collections.sort(teams, new Comparator<Team>() {
                public int compare(Team tm1, Team tm2) {
                    if(tm1.getTeamPoints() > tm2.getTeamPoints()) { return -1;}
                    if(tm1.getTeamPoints() < tm2.getTeamPoints()) {return 1;}
                    if(tm1.netTeamScore() > tm2.netTeamScore()) {return  -1;}
                    if(tm1.netTeamScore() < tm2.netTeamScore()) {return 1;}
                    else {return 0;}
                }
            });
            for (Team team: teams) {
                String teamStat = String.format("%s      %d          %d        %d %n", team.getTeamName(), team.getTeamPoints(),
                        team.getTeamScore(), team.getScoreAgainst() );
                mStats.getItems().add(teamStat);
            }
        }
        mStats.setOrientation(Orientation.VERTICAL);

    }

    public void handleLeague (ActionEvent actionEvent) throws IOException {
        if (leaguePlayed) {
            leagueButton.setText("League already played");
            playBallAnimation();
        } else {
        //league.setLeagueName(leagueName);
        //league.selectTeamsFromFile();
        league.playAllMatches();
        playBallAnimation();}
        Team winner = league.getWinnerTeam();
        winnerLabel.setText("WINNER: " + winner.getTeamName());
        leaguePlayed = true;
        teamPageButton.setVisible(true);
        playerPageButton.setVisible(true);
        downloadResults.setVisible(true);
        //league.displayMatchResults();
        //league.displayTeamStandings();
    }

    public void handleTeam (ActionEvent actionEvent) throws IOException {
        createNewView("team_details.fxml", "Team Details", teamPageButton);
    }

    public void handlePlayer (ActionEvent actionEvent) throws IOException {
        createNewView("player_details.fxml", "Player Details", playerPageButton);
    }

    public void handleStart(ActionEvent actionEvent) throws IOException {
        setLeagueName();
        //createNewView("chooser.fxml", "Choose Teams and Players", startButton);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chooser.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent, 150, 75);
        stage.setScene(scene);
        stage.setTitle("Choose  Teams and Players");
        stage.show();
        startButton.getScene().getWindow().hide();
        Toast.makeText(stage, "League " + leagueName + " is created.",1000, 200,200 );
    }

    private void setLeagueName () {
        if(leagueNameText.getText().isEmpty()) {
            leagueName = "First League";
        } else {
            leagueName = leagueNameText.getText();
            league.setLeagueName(leagueName);
        }
    }


    private void createNewView(String url, String pageTitle, Node node) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
        Parent parent = loader.load();
        Scene scene = new Scene(parent, 700, 600);
        mStage = (Stage) node.getScene().getWindow();
        mStage.setScene(scene);
        mStage.setTitle(pageTitle);
        mStage.show();
    }

    public void displayPlayerStats(ActionEvent actionEvent) {
        updatePlayerStats(league.listAllActivePlayers());

    }

    public void displayMatchResults(ActionEvent actionEvent) {
        updateMatchResults(league.getMatchResults());
    }

    public void displayStandings(ActionEvent actionEvent) {
        updateTeamStandings(league.getTeams());
    }

    public void exportDataToExcel(ActionEvent actionEvent) {
        List<String> stats = new ArrayList<String>();
        stats = mStats.getItems();
        try {
            efo.exportStatsToExcel(stats);
        } catch (IOException e) {
            e.printStackTrace();
            Stage stage = (Stage) downloadResults.getScene().getWindow();
            Toast.makeText(stage, "Problem writing file!",
                    1000, 100, 100
            );
        }
    }

    private void playBallAnimation () {
        ballImage.setVisible(true);
        Timeline jump = new Timeline();
        jump.setCycleCount(4);
        jump.setAutoReverse(true);
        jump.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(ballImage.translateYProperty(), 293)));
        //jump.play();

        Timeline roll = new Timeline();
        roll.setCycleCount(Animation.INDEFINITE);
        roll.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25),
                new KeyValue(ballImage.rotateProperty(), 360)));
        //roll.play();

        ParallelTransition parallelTransition= new ParallelTransition(jump,roll);
        parallelTransition.play();
        jump.setOnFinished(event -> {parallelTransition.stop(); ballImage.setVisible(false);});
    }
}
