package com.fbasketball;

import group.League;
import info.Player;
import info.Team;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fbasketball.Controller.leaguePlayed;

public class PlayerController {

    private League league = League.getInstance();
    private ExcelFileOperator efo = new ExcelFileOperator();

    @FXML
    private TableColumn<Player, String> playerNoCol, playerNameCol, ppgCol, teamCol;

    @FXML
    private CheckComboBox<Player> playerCombo;

    @FXML
    private TableView<Player> playerTable;


    @FXML
    private Button backButtonPlayer,downloadButton;

    @FXML
    public void initialize() {
        playerCombo.getItems().clear();
        for (Team team : league.getTeams()) {
            for (Player player : team.getTeamPlayerList()) {
                playerCombo.getItems().add(player);
            }
        }
        updatePlayerTable(playerCombo.getCheckModel().getCheckedItems());
    }

    public void updatePlayerTable (ObservableList<Player> playerList) {
        playerTable.getItems().clear();
        playerTable.setItems(playerList);
        playerNoCol.setCellValueFactory(new PropertyValueFactory<>("playerNo"));
        playerNameCol.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        ppgCol.setCellValueFactory(new PropertyValueFactory<>("pointsPerGame"));
        teamCol.setCellValueFactory(new PropertyValueFactory<>("teamName"));
    }

    public void handleBack(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("league_details.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) backButtonPlayer.getScene().getWindow();
        Scene scene = stage.getScene();
        scene.setRoot(root);
        stage.setTitle("League Details");
        leaguePlayed = true;
        stage.show();
    }

    public void handleDownload () {
        try {
            efo.exportTabletoExcel(playerCombo.getCheckModel().getCheckedItems());
        } catch (IOException e) {
            e.printStackTrace();
            Stage stage = (Stage) downloadButton.getScene().getWindow();
            Toast.makeText(stage, "Problem writing file!",
                    1000, 100, 100
            );
        }
    }
}

