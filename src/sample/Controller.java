package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import sample.model.Game;

public class Controller {

    @FXML
    public Label attemptsLabel;
    @FXML
    private Label movieLabel;
    @FXML
    private Label gameStateLabel;
    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;
    @FXML
    private HBox hbox3;
    @FXML
    private BorderPane borderPane;

    private Game game;


    public void initialize() {
        game = new Game();
        gameStateLabel.setText("Let's play!");
        movieLabel.setText(game.getGuessingMovie());
        attemptsLabel.setText("Wrong guesses: 0/5");
        borderPane.requestFocus();
    }

    @FXML
    public void openNewGame() {
        initialize();
        hbox1.getChildren().forEach(node -> node.setDisable(false));
        hbox2.getChildren().forEach(node -> node.setDisable(false));
        hbox3.getChildren().forEach(node -> node.setDisable(false));
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

    @FXML
    public void handleMouseClick(ActionEvent actionEvent) {
        ToggleButton clickedButton = (ToggleButton) actionEvent.getTarget();
        clickedButton.setSelected(false);

        if (game.isWin() || game.isGameOver()) {
            return;
        }
        gameStateLabel.setText("");
        clickedButton.setDisable(true);
        checkGuess(clickedButton.getText());
    }

    @FXML
    public void handleKeyClick(KeyEvent keyEvent) {

        if (game.isWin() || game.isGameOver()) {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                openNewGame();
            } else return;
        }
        gameStateLabel.setText("");
        if (keyEvent.getCode().isLetterKey()) {
            ToggleButton clickedButton;
            if (keyEvent.getCode().ordinal() < 44) {
                clickedButton = (ToggleButton) hbox1.getChildren().get(keyEvent.getCode().ordinal() - 36);
            } else if (keyEvent.getCode().ordinal() < 53) {
                clickedButton = (ToggleButton) hbox2.getChildren().get(keyEvent.getCode().ordinal() - 44);
            } else {
                clickedButton = (ToggleButton) hbox3.getChildren().get(keyEvent.getCode().ordinal() - 53);
            }

            if (clickedButton.isDisable()) {
                return;
            }
            clickedButton.setDisable(true);
            checkGuess(clickedButton.getText());
        }
    }

    private void checkGuess(String letter) {
        if (game.isCorrectLetter(letter)) {
            movieLabel.setText(game.getGuessingMovie());
            if (game.isWin()) {
                showWinAlert();
            }
        } else {
            updateMovesLabel();
            if (game.isGameOver()) {
                showGameOverAlert();
            }
        }
        borderPane.requestFocus();
    }

    private void updateMovesLabel() {
        attemptsLabel.setText("Wrong guesses: " + String.valueOf(game.getAttempts()) + "/5");
    }

    private void showGameOverAlert() {
        gameStateLabel.setText("GAME OVER!!!");
        attemptsLabel.setText(game.getMovie());
    }

    private void showWinAlert() {
        gameStateLabel.setText("VICTORY!!!");
    }
}
