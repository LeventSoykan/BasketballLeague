package com.fbasketball;

import group.League;
import info.Player;
import info.Team;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.omg.CORBA.CODESET_INCOMPATIBLE;

import java.io.IOException;
import java.util.List;

import static com.fbasketball.Controller.leaguePlayed;


public class InfoController {

    private League league = League.getInstance();
    private Team selectedTeam;
    private ObservableList<Player> mPlayerList = FXCollections.observableArrayList();
    private ObservableList<Player> mAllPlayersList = FXCollections.observableArrayList();

    @FXML
    private Label chooser;

    @FXML
    private Button backButtonTeam;

    @FXML
    private TextField winsText, totalScoreText;

    @FXML
    private TableColumn<Player, String> playerNameColumn, playerNumberColumn, pointsColumn, teamColumn;

    @FXML
    private ChoiceBox<Team> choiceBox;

    @FXML
    private TableView<Player> teamTable;



    @FXML
    public void initialize() {

       choiceBox.getItems().clear();
        for (Team team: league.getTeams()){
            choiceBox.getItems().add(team);
        }

    }



    public void handleTextForTeam (Team team) {
        totalScoreText.setText("" + team.getTeamScore());
        winsText.setText("" + team.getWinCount());
    }



    public void handleTeamChoice(ActionEvent actionEvent) {
        selectedTeam = choiceBox.getSelectionModel().getSelectedItem();
        updateTeamTable(selectedTeam);
        handleTextForTeam(selectedTeam);
    }

    public void updateTeamTable (Team team) {
        teamTable.getItems().clear();
        for (Player player: team.getTeamPlayerList()) {
            mPlayerList.add(player);}
        teamTable.setItems(mPlayerList);
        playerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("playerNo"));
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));

        teamTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Player player = teamTable.getSelectionModel().getSelectedItem();
                Stage stage = (Stage) teamTable.getScene().getWindow();
                Toast.makeText(stage, "PointsPerGame: " + player.getPointsPerGame(),
                        1000,100,100);
            }
        });
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("league_details.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) backButtonTeam.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        stage.setTitle("League Details");
        leaguePlayed = true;
        stage.show();
    }


}
