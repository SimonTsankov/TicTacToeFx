import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.text.Style;
import java.io.IOException;
import java.util.Random;


public class Controller {
    String botSymbol = "O";
    String playerSymbol = "X";

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Button btn0 = new Button(), btn1 = new Button(), btn2 = new Button(), btn3 = new Button(), btn4 = new Button(), btn5 = new Button(), btn6 = new Button(), btn7 = new Button(), btn8 = new Button(), reset = new Button();
    @FXML
    private RadioButton rBtnBot, rBtnImpossibleBot, rBtnPlayer, rGoFirst, rGoSecond;

    int turn = 0;

    private Button mapBtn[][];

    @FXML
    private Label winnerLabel;

    Random random = new Random();

    //region Moves
    private void botImpossibleMakeMove() {
        char[][] mapChar = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mapBtn[i][j].getText().equals("")) mapChar[i][j] = '_';
                else {
                    mapChar[i][j] = mapBtn[i][j].getText().charAt(0);
                }
            }
        }
        Bot.Move move = Bot.findBestMove(mapChar);
        mapBtn[move.row][move.col].setText(botSymbol);
        mapBtn[move.row][move.col].setStyle("-fx-background-color: #006699s");
        turn++;
    }

    void playerMakeMove(Button btn) {
        btn.setText(playerSymbol);
        btn.setStyle("-fx-background-color: #80d4ff");
        turn++;
    }


    void makeMove(Button btn) {
        if (rBtnImpossibleBot.isSelected()) {// Its 1vbot impossible
            if (btn.getText().equals("")) {
                playerMakeMove(btn);
                if (hasEmpty())
                    botImpossibleMakeMove();
            }
        } else {
            if (rBtnBot.isSelected()) { // Its 1vbot easy
                if (btn.getText().equals("")) {
                    playerMakeMove(btn);
                    if (hasEmpty())
                        botMakeMove();
                }
            } else {    //Its 1v1 players
                if (btn.getText().equals("")) {
                    btn.setText(turn % 2 == 0 ? "X" : "O");
                    btn.setStyle("-fx-background-color: " + (turn % 2 == 0 ? "#b3ffb3" : "#80d4ff"));
                    turn++;
                }
            }
        }
        if (checkIfWon()) {
            reset.setVisible(true);
        }
        if (!hasEmpty() && !checkIfWon()) {
            reset.setVisible(true);
            winnerLabel.setText("Draw :C");
        }
    }


    private void botMakeMove() {
        boolean placed = false;
        turn++;
        while (placed == false) {
            int x_pos = random.nextInt(3);
            int y_pos = random.nextInt(3);
            if (mapBtn[x_pos][y_pos].getText().equals("")) {
                placed = true;
                mapBtn[x_pos][y_pos].setText(botSymbol);
                mapBtn[x_pos][y_pos].setStyle("-fx-background-color: #b3ffb3; ");
            }
        }

    }

    //endregion
    boolean hasEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (mapBtn[i][j].getText().equals("")) return true;
            }
        }
        return false;
    }

    @FXML
    void rGoFirstClicked(ActionEvent event) {
        reset();
        botSymbol = "O";
        playerSymbol = "X";
        Bot.player = 'O';
        Bot.opponent = 'X';
    }

    @FXML
    void rGoSecondClicked(ActionEvent event) {
        reset();
        botSymbol = "X";
        playerSymbol = "O";
        Bot.player = 'X';
        Bot.opponent = 'O';
        if (rBtnImpossibleBot.isSelected()) {
            botMakeMove();
        }
        if (rBtnBot.isSelected())
            botImpossibleMakeMove();
    }

    @FXML
    void rBtnBotClicked(ActionEvent event) {
        reset();
        if(rGoSecond.isSelected())
            botMakeMove();
        rGoSecond.setDisable(false);
        rGoFirst.setDisable(false);}

    @FXML
    void rBtnImpossibleBotClicked(ActionEvent event) {
        reset();
    if(rGoSecond.isSelected())
        botImpossibleMakeMove(); rGoSecond.setDisable(false);
        rGoFirst.setDisable(false); }

    @FXML
    void rBtnPlayerClicked(ActionEvent event) {
        reset();
        rGoSecond.setDisable(true);
        rGoFirst.setDisable(true);
    }

    boolean checkIfWon() {
        for (int i = 0; i < 3; i++) {
            String temp = "";
            String temp2 = "";
            for (int j = 0; j < 3; j++) {
                temp += mapBtn[i][j].getText();
                temp2 += mapBtn[j][i].getText();
            }
            if (temp.equals("XXX") || temp2.equals("XXX")) {
                winnerLabel.setText("Winner: X");
                return true;
            }
            if (temp.equals("OOO") || temp2.equals("OOO")) {
                winnerLabel.setText("Winner: O");
                return true;
            }
        }

        String X = mapBtn[0][0].getText() + mapBtn[1][1].getText() + mapBtn[2][2].getText();
        String X2 = mapBtn[0][2].getText() + mapBtn[1][1].getText() + mapBtn[2][0].getText();


        if (X.equals("XXX") || X2.equals("XXX")) {
            winnerLabel.setText("Winner: X");
            return true;
        }
        if (X.equals("OOO") || X2.equals("OOO")) {
            winnerLabel.setText("Winner: O");
            return true;
        }
        return false;
    }

    @FXML
    void reset(ActionEvent event) {
        reset();
        if (rBtnBot.isSelected() && rGoSecond.isSelected()) {
            botMakeMove();
        }
        if (rBtnImpossibleBot.isSelected() && rGoSecond.isSelected())
            botImpossibleMakeMove();
    }

    void reset() {
        winnerLabel.setText("Winner: ");
        turn = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mapBtn[i][j].setText("");

                mapBtn[i][j].setStyle(defStyle);
            }
        }
        reset.setVisible(false);
    }


    @FXML
    void btnClicked(ActionEvent event) {
        Object node = event.getSource();
        if (!reset.isVisible()) {
            Button b = (Button) node;
            makeMove(b);
        }
    }

    ToggleGroup groupGmode;
    ToggleGroup groupFirstSecond;
    String defStyle;
    void setUpRadioButtons(){
        groupFirstSecond = new ToggleGroup();

        rGoFirst.setToggleGroup(groupFirstSecond);
        rGoSecond.setToggleGroup(groupFirstSecond);
        rGoSecond.setDisable(true);
        rGoFirst.setDisable(true);

        groupGmode = new ToggleGroup();
        rBtnBot.setToggleGroup(groupGmode);
        rBtnImpossibleBot.setToggleGroup(groupGmode);
        rBtnPlayer.setToggleGroup(groupGmode);
        rBtnPlayer.getStyleClass().add("red-radio-button");
    }
    @FXML
    public void initialize() throws IOException {
        setUpRadioButtons();
        defStyle = btn0.getStyle();//needed for reset of button


        mapBtn = new Button[][]{
                {btn0, btn1, btn2},
                {btn3, btn4, btn5},
                {btn6, btn7, btn8}
        };
        reset.setVisible(false);

        final Effect glow = new Glow(3.0);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mapBtn[i][j].setEffect(glow);
            }
        }

    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
