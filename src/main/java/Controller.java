import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;


public class Controller {
    @FXML
    private Button btn0 = new Button(), btn1 = new Button(), btn2 = new Button(), btn3 = new Button(), btn4 = new Button(), btn5 = new Button(), btn6 = new Button(), btn7 = new Button(), btn8 = new Button(), reset = new Button();
    @FXML
    private RadioButton rBtnBot, rBtnImpossibleBot, rBtnPlayer;

    boolean playerIsFirst = true;
    int turn = 0;
    private Button mapBtn[][];

    @FXML
    private Label winnerLabel;

    @FXML
    void Test(ActionEvent event) {

        System.out.println(checkIfWon());
    }

    Random random = new Random();

    void makeMove(Button btn) {

        if (rBtnBot.isSelected()) {
            if(btn.getText().equals("")) {
                if (playerIsFirst) {
                    btn.setText("O");
                } else btn.setText("X");
                turn++;
                if (turn < 8)
                    botMakeMove();
            }
        } else {
            if (btn.getText().equals("")) {
                if (turn % 2 == 0) {
                    btn.setText("O");
                } else {
                    btn.setText("X");
                }
                turn++;
            }
        }
        if (checkIfWon()) {
            reset.setVisible(true);
        }
        System.out.println(turn);
        if (turn == 9 && !checkIfWon()) {
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
                mapBtn[x_pos][y_pos].setText("X");
            }
        }

    }

    @FXML
    void rBtnBotClicked(ActionEvent event) {
        reset();
    }

    @FXML
    void rBtnImpossibleBotClicked(ActionEvent event) {
        reset();
    }

    @FXML
    void rBtnPlayerClicked(ActionEvent event) {
        reset();
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
    }

    void reset() {
        winnerLabel.setText("Winner: ");
        turn = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mapBtn[i][j].setText("");
            }
        }
        reset.setVisible(false);
    }

    @FXML
    void btn0Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn0);
    }

    @FXML
    void btn1Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn1);
    }

    @FXML
    void btn2Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn2);
    }

    @FXML
    void btn3Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn3);
    }

    @FXML
    void btn4Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn4);
    }

    @FXML
    void btn5Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn5);
    }

    @FXML
    void btn6Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn6);
    }

    @FXML
    void btn7Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn7);
    }

    @FXML
    void btn8Clicked(ActionEvent event) {
        if (!reset.isVisible())
            makeMove(btn8);
    }

    ToggleGroup group;

    @FXML
    public void initialize() throws IOException {
        reset.setVisible(false);
        group = new ToggleGroup();
        rBtnBot.setToggleGroup(group);
        rBtnImpossibleBot.setToggleGroup(group);
        rBtnPlayer.setToggleGroup(group);
        rBtnPlayer.getStyleClass().add("red-radio-button");
        mapBtn = new Button[][]{
                {btn0, btn1, btn2},
                {btn3, btn4, btn5},
                {btn6, btn7, btn8}
        };

        final Effect glow = new Glow(3.0);

        for (int i = 0; i <3 ; i++) {
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
    AnchorPane anchorPane;
    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
