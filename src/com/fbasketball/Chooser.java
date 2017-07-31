package com.fbasketball;

import group.League;
import info.Player;
import info.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Chooser {

    private League league = League.getInstance();
    private Team firstTeam, secondTeam;
    private List<Player> allPlayers = new ArrayList<Player>();
    private List<Team> allTeams = new ArrayList<Team>();

    @FXML
    private AnchorPane mAnchorPane;

    @FXML
    private ChoiceBox teamChoice1, teamChoice2;

    @FXML
    private CheckComboBox playerChoice1, playerChoice2;

    @FXML
    private Button confirmChoice1, confirmChoice2, confirmChoice3, confirmChoice4;

    @FXML
    private Label label;

    @FXML
    public void initialize () {
        league.importAll();
        allPlayers = league.getAllPlayerList();
        allTeams = league.getAllTeamsList();
        teamChoice1.getItems().clear();
        for (Team team: league.getAllTeamsList()) {
            teamChoice1.getItems().add(team);
        }
    }

    public void firstTeam (ActionEvent actionEvent){
        Stage stage = (Stage) teamChoice1.getScene().getWindow();
        if (teamChoice1.getSelectionModel().getSelectedItem() == null) {
            Toast.makeText(stage, "You must select a team", 1000, 200, 200);
        } else {
            firstTeam = (Team) teamChoice1.getSelectionModel().getSelectedItem();
            league.getTeams().add(firstTeam);
            teamChoice1.setVisible(false);
            confirmChoice1.setVisible(false);
            league.getAllTeamsList().remove(firstTeam);
            playerChoice1.getItems().clear();
            for (Player player : league.getAllPlayerList()) {
                playerChoice1.getItems().add(player);
            }
            label.setText("Select Players");
            playerChoice1.setVisible(true);
            confirmChoice2.setVisible(true);
        }

    }

    public void firstPlayerList (ActionEvent actionEvent) {
        List<Player> playerList = new ArrayList<Player>();
        playerList = playerChoice1.getCheckModel().getCheckedItems();
        Stage stage = (Stage) playerChoice1.getScene().getWindow();
        if (playerList.size() != 3) {
            Toast.makeText(stage, "3 players should be selected", 1000, 200, 200);
        } else {
            firstTeam.getTeamPlayerList().addAll(playerList);
            league.getAllPlayerList().removeAll(playerList);
            playerChoice1.setVisible(false);
            confirmChoice2.setVisible(false);
            teamChoice2.getItems().clear();
            for (Team team : league.getAllTeamsList()) {
                teamChoice2.getItems().add(team);
            }
            label.setText("Please Select Team Two");
            teamChoice2.setVisible(true);
            confirmChoice3.setVisible(true);
        }
    }

    public void secondTeam (ActionEvent actionEvent) {
        Stage stage = (Stage) teamChoice2.getScene().getWindow();
        if (teamChoice2.getSelectionModel().getSelectedItem() == null) {
            Toast.makeText(stage, "You must select a team", 1000, 200, 200);
        } else {
            secondTeam = (Team) teamChoice2.getSelectionModel().getSelectedItem();
            league.getTeams().add(secondTeam);
            teamChoice2.setVisible(false);
            confirmChoice3.setVisible(false);
            league.getAllTeamsList().remove(secondTeam);
            playerChoice2.getItems().clear();
            for (Player player : league.getAllPlayerList()) {
                playerChoice2.getItems().add(player);
            }
            label.setText("Select Players");
            playerChoice2.setVisible(true);
            confirmChoice4.setVisible(true);
        }
    }

    public void secondPlayerList (ActionEvent actionEvent) throws IOException {
        List<Player> playerList = new ArrayList<Player>();
        playerList = playerChoice2.getCheckModel().getCheckedItems();
        Stage stage = (Stage) playerChoice1.getScene().getWindow();
        if (playerList.size() != 3) {
            Toast.makeText(stage, "3 players should be selected", 1000, 200, 200);
        } else {
            secondTeam.getTeamPlayerList().addAll(playerList);
            assignTeams(firstTeam);
            assignTeams(secondTeam);
            league.getAllPlayerList().removeAll(playerList);
            goBack();
        }
    }

    public void assignTeams (Team team) {
        for (Player player : team.getTeamPlayerList()) {
            player.setTeamName(team.getTeamName());
        }
    }

    public void goBack () throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("league_details.fxml"));
        Parent parent = loader.load();
        Stage stage = (Stage) confirmChoice4.getScene().getWindow();
        Scene scene = new Scene(parent, 700, 600);
        stage.setScene(scene);
        stage.setTitle("League Details");
        stage.show();
    }

}
