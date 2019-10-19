/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author rmatt
 */
public class TicTacToe extends Application {

    /**
     * Sets up variable for player turns, label for messages, and cell array
     */
    private String player = "X";
    Label label = new Label("Player X goes first!");
    Cell[][] cell = new Cell[3][3];

    /**
     * Sets up cells on the board
     *
     * @return a pane that represents the tic tac toe game with cells populating
     * a 3 x 3 grid
     */
    private Parent grid() {
        Pane root = new Pane();
        root.setPrefSize(600, 600);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cell[i][j] = new Cell();
                cell[i][j].setTranslateX(j * 200);
                cell[i][j].setTranslateY(i * 200);
                root.getChildren().add(cell[i][j]);
            }
        }
        return root;
    }

    @Override
    public void start(Stage primaryStage) {

        BorderPane gameBoard = new BorderPane();
        Button button = new Button("Restart");
        gameBoard.setCenter(grid());
        gameBoard.setBottom(label);
        gameBoard.setTop(button);

        label.setFont(new Font("Arial", 50));
        
         Media song = new Media(getClass().getResource("song.mp3").toString());
        
        MediaPlayer play = new MediaPlayer(song);

        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(new Scene(gameBoard));
        primaryStage.show();
        play.play();
        
        
        

        
        /**
         * Allows for game to be restarted
         */
        button.setOnAction(e -> {
            System.out.println("Restarting app!");
            primaryStage.close();
            Platform.runLater(() -> new TicTacToe().start(new Stage()));
        });
        
       

    }

    /**
     * Tests if the board is full
     *
     * @return ture if board is full
     */
    public boolean isDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cell[i][j].getCellMark() == " ") {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tests the board winning grouping of cells
     *
     * @param player
     * @return true if there is a winning combination
     */
    public boolean winner(String player) {
        for (int i = 0; i < 3; i++) {
            if (cell[i][0].getCellMark() == player
                    && cell[i][1].getCellMark() == player
                    && cell[i][2].getCellMark() == player) {
                return true;
                
            }
        }

        for (int j = 0; j < 3; j++) {
            if (cell[0][j].getCellMark() == player
                    && cell[1][j].getCellMark() == player
                    && cell[2][j].getCellMark() == player) {
                return true;
            }
        }

        if (cell[0][0].getCellMark() == player
                && cell[1][1].getCellMark() == player
                && cell[2][2].getCellMark() == player) {
            return true;
        }

        if (cell[0][2].getCellMark() == player
                && cell[1][1].getCellMark() == player
                && cell[2][0].getCellMark() == player) {
            return true;
        }
        return false;

    }

    /**
     * Method to implement the isDraw and winner methods
     */
    public void checkResult() {
        if (winner(player)) {
            label.setText(player + " has won!");
            player = " ";

        } else if (isDraw()) {
            label.setText("Game is a draw!");
        }
    }

    private class Cell extends StackPane {

        private Text text = new Text();
        private String cellMark = " ";

        //set move
        public Cell() {

            //Set up empty text
            text.setText(" ");

            //set up boxes for gameboard
            Rectangle box = new Rectangle(200, 200, Color.BLUE);
            box.setStroke(Color.BLACK);
            setAlignment(Pos.CENTER);
            text.setFont(Font.font(100));

            //set text for player moves and check for win or draw
            setOnMouseClicked(e -> {
                if (text.getText().equals(" ")) {
                    setCellMark(player);
                    checkResult();
                    setPlayer();
                } 
            });
            getChildren().addAll(box, text);
        }

        /**
         * Checks the current value of the cell
         *
         * @return a string of either X or O
         */
        public String getCellMark() {
            return cellMark;
        }

        /**
         * Sets the cellMark to the string passed in
         *
         * @param s a string that is displayed on the game board.
         */
        public void setCellMark(String s) {
            cellMark = s;
            if (cellMark.equals("X")) {
                text.setText("X");
                label.setText("Player O goes next!");
            } else if (cellMark.equals("O")) {
                text.setText("O");
                label.setText("Player X goes next!");
            }
        }

        /**
         * Alternates between players
         */
        public void setPlayer() {
            if (player.equals("X")) {
                player = "O";
            } else if (player.equals("O")) {
                player = "X";
            }
        }

    }

    public static void main(String[] args) {

        launch(args);
    }

}
