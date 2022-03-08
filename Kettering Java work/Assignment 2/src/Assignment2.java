//Braxton Friend
//CS 102-01
//Assignment 1


import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseException;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisPlayer;
import TennisDatabase.TennisMatch;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


import java.io.File;
import java.util.Scanner;

public class Assignment2 extends Application {

    TennisDatabase database = new TennisDatabase();

    public static void main(String[] args) throws TennisDatabaseException {
        launch(args);
    }
 /*       Scanner getInput = new Scanner(System.in);
        int userInput;

        TennisDatabase database = new TennisDatabase();

        String fileName;
        try {
             fileName = args[0];
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("***ERROR*** Please put a valid input file name into the command arguments.");
        }

        try {
            database.loadFromFile(args[0]); // need to create loadFromFile
        }
        catch (TennisDatabaseRuntimeException e) {
            System.out.println("***ERROR*** Runtime Load Error");
        }
        catch (TennisDatabaseException e) {
            System.out.println("***ERROR*** Load Error");
        }
        do {
            printMenu();
            userInput = getInput.nextInt();
            switch (userInput) {
                case 1:
                    System.out.println("**Print all tennis players selected.**");
                    String[] playerOutputArray = database.getPlayerStringArray(); //array of tennis play strings
                    for (int i = 0; i < playerOutputArray.length; i++) {
                        System.out.println(playerOutputArray[i]);
                    }
                    break;
                case 2:
                    System.out.println("**Print all tennis matches by player selected.**");
                    System.out.println("Enter a player ID");
                    String playerIDInput = getInput.next();
                    playerIDInput = playerIDInput.toUpperCase();
                    try {
                        String[] playerMatchStringArray = database.getMatchesOfPlayerString(playerIDInput); //array of match strings

                        for (int i = 0; i < playerMatchStringArray.length; i++) {
                            System.out.println(playerMatchStringArray[i]);
                        }

                    }
                    catch (TennisDatabaseRuntimeException e) {
                        System.out.println("...");
                    }
                    break;
                case 3: //prints all matches
                    System.out.println("**Print all tennis matches selected**");
                    String[] matchesArray = database.getAllMatchesString();
                    try {
                        if (database.getMatchCount() == 0) {
                            System.out.println("No Matches in System");
                        } else {
                            for (int i = 0; i < matchesArray.length; i++) {
                                System.out.println(matchesArray[i]);
                            }
                        }
                    }
                    catch (NullPointerException e){

                    }
                    break;
                case 4: //inserts a new player
                    System.out.println("**Insert tennis player selected**");
                    System.out.println("*Input player's ID*");
                    String playerID = getInput.next();
                    System.out.println("*Input player first name*");
                    String playerFirstName = getInput.next();
                    System.out.println("*Input player last name*");
                    String playerLastName = getInput.next();
                    System.out.println("*Input player birth year*");
                    int playerBirthYear = getInput.nextInt();
                    System.out.println("*Input player country*");
                    String playerCountry = getInput.next();
                    System.out.println("**Thank you, player added**");
                    database.insertPlayer(playerID, playerFirstName, playerLastName, playerBirthYear, playerCountry);
                    break;
                case 5: //inserts a new match
                    System.out.println("**Insert tennis match selected*");
                    System.out.println("*Input player 1 id*");
                    String playerId1 = getInput.next().toUpperCase();
                    System.out.println("*Input player 2 id*");
                    String playerId2 = getInput.next().toUpperCase();
                    System.out.println("*Input year of match*");
                    int matchYear = getInput.nextInt();
                    System.out.println("*Input month of match*");
                    int matchMonth = getInput.nextInt();
                    System.out.println("*Input day of match*");
                    int matchDay = getInput.nextInt();
                    System.out.println("*Input tournament name*");
                    getInput.nextLine();
                    String tournamentName = getInput.nextLine().toUpperCase();
                    System.out.println("*Input scores separated by commas*");
                    String tournamentScore = getInput.next();
                    database.insertMatch(playerId1, playerId2, matchYear, matchMonth, matchDay, tournamentName, tournamentScore);
                    System.out.println("**Thank you, Match added**");
                    break;
                case 0: //exits
                    System.out.println("**Exit Chosen. Goodbye**");
                    System.exit(0);
                default:
                    System.out.println("**Invalid input. Please enter a valid selection**");
            }
        }
        while(userInput != 0);
    }

    public static void printMenu() {           //Prints a menu for the User to choose from
        System.out.println("1: Print Players");
        System.out.println("2: Print Matches by Player");
        System.out.println("3: Print All Matches");
        System.out.println("4: New Tennis Player");
        System.out.println("5: New Tennis Match");
        System.out.println("0: Exit the Program");
        System.out.println("****Please Enter a Selection on the Menu.****");
    }

}
*/
    @Override
    public void start(Stage primaryStage) throws Exception {



        TableColumn<TennisPlayer, String> idColumn = new TableColumn("ID");
        idColumn.setMinWidth(10);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TennisPlayer, String> firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setMinWidth(100);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<TennisPlayer, String> lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setMinWidth(100);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<TennisPlayer, String> birthYearColumn = new TableColumn("Birth Year");
        birthYearColumn.setMinWidth(100);
        birthYearColumn.setCellValueFactory(new PropertyValueFactory<>("birthYear"));

        TableColumn<TennisPlayer, String> countryColumn = new TableColumn("Country of Origin");
        countryColumn.setMinWidth(100);
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        TableColumn<TennisPlayer, String> winsColumn = new TableColumn("Wins");
        winsColumn.setMinWidth(100);
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<TennisPlayer, String> lossesColumn = new TableColumn("Losses");
        lossesColumn.setMinWidth(100);
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));



        TableColumn<TennisMatch, String> p1Column = new TableColumn("Player 1");
        p1Column.setMinWidth(100);
        p1Column.setCellValueFactory(new PropertyValueFactory<>("IdPlayer1"));

        TableColumn<TennisMatch, String> p2Column = new TableColumn("Player 2");
        p2Column.setMinWidth(100);
        p2Column.setCellValueFactory(new PropertyValueFactory<>("idPlayer2"));

        TableColumn<TennisMatch, String> dateColumn = new TableColumn("Date");
        dateColumn.setMinWidth(100);
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("DateString"));

        TableColumn<TennisMatch, String> tournamentColumn = new TableColumn("Tournament");
        tournamentColumn.setMinWidth(100);
        tournamentColumn.setCellValueFactory(new PropertyValueFactory<>("Tournament"));

        TableColumn<TennisMatch, String> scoreColumn = new TableColumn("Score");
        scoreColumn.setMinWidth(100);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("MatchScore"));


        TableView<TennisPlayer> playerTableView = new TableView<TennisPlayer>();
        playerTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        playerTableView.setMaxHeight(800);
        playerTableView.setMinSize(500.0 ,500.0);

        TableView<TennisMatch> matchTableView = new TableView<TennisMatch>();
        matchTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        matchTableView.setMaxHeight(800);
        matchTableView.setMinSize(500.0 ,500.0);


        playerTableView.getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, birthYearColumn, countryColumn, winsColumn, lossesColumn);
        matchTableView.getColumns().addAll(p1Column, p2Column, dateColumn, tournamentColumn, scoreColumn);

        HBox hbox1 = new HBox (15);
        Text hiWorld = new Text("Hi World");

        HBox hbox2 = new HBox(15);
        Text hiWorld2 = new Text("Yo what's good");
        hbox2.getChildren().setAll();

        HBox hbox3 = new HBox(15);

        HBox hbox4 = new HBox(15);

        VBox vbox = new VBox();
        vbox.getChildren().setAll(hbox1, hbox2, hbox3, hbox4);

        TextField playerIDDelete = new TextField();
        playerIDDelete.setPromptText("Player ID");

        TextField playerId = new TextField();
        playerId.setPromptText("Player ID");

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");

        TextField birthYear = new TextField();
        birthYear.setPromptText("Birth Year");

        TextField country = new TextField();
        country.setPromptText("Country");

        TextField matchp1 = new TextField();
        matchp1.setPromptText("Player 1 ID");

        TextField matchp2 = new TextField();
        matchp2.setPromptText("Player 2 ID");

        TextField year = new TextField();
        year.setPromptText("Year");

        TextField month = new TextField();
        month.setPromptText("Month");

        TextField day = new TextField();
        day.setPromptText("Day");

        TextField tournament = new TextField();
        tournament.setPromptText("Tournament");

        TextField score = new TextField();
        score.setPromptText("Score");

        TextField saveToFile = new TextField();
        saveToFile.setPromptText("Save Name");

        Button insertMatchButton = new Button("Insert Match");
        insertMatchButton.setOnAction(e -> {
            try {
                database.insertMatch(matchp1.getText(), matchp2.getText(), Integer.valueOf(year.getText()), Integer.valueOf(month.getText()), Integer.valueOf(day.getText()), tournament.getText(), score.getText());
                matchTableView.setItems((this.getMatches()));
                playerTableView.setItems(this.getPlayers());
                playerTableView.refresh();
            }
            catch (TennisDatabaseException l) {
                System.out.println(l.getMessage());

            }
        });

        Button insertPlayerButton = new Button ( "Insert Player");
        insertPlayerButton.setOnAction(e -> {
            try {
                database.insertPlayer(playerId.getText(), firstName.getText(), lastName.getText(), Integer.valueOf(birthYear.getText()), country.getText());
                matchTableView.setItems((this.getMatches()));
                playerTableView.setItems(this.getPlayers());
                playerTableView.refresh();
            }
            catch (TennisDatabaseException o) {

            }
        });

        Button resetButton = new Button ( "Reset");
        resetButton.setOnAction(e -> {
            database.reset();
            matchTableView.setItems((this.getMatches()));
            playerTableView.setItems(this.getPlayers());
            playerTableView.refresh();
            matchTableView.refresh();

        });

        Button saveToFileButton = new Button ( "Save File");
        saveToFileButton.setOnAction(e -> {
            try {
                database.saveToFile(saveToFile.getText());
            }
            catch (TennisDatabaseException p) {

            }
        });

        Button deletePlayerButton = new Button ( "Delete Player");
        deletePlayerButton.setOnAction(e -> {
            database.deletePlayer(playerIDDelete.getText());
            matchTableView.setItems((this.getMatches()));
            playerTableView.setItems(this.getPlayers());
            playerTableView.refresh();
        });

        Button importFileButton = new Button ( "Import File");
        importFileButton.setOnAction(e -> {
            File file;
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(.txt)","txt");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showDialog(null,"Import");

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                try{
                    database.loadFromFile(file.toString());
                }
                catch (TennisDatabaseException y){

                }
                playerTableView.setItems(this.getPlayers());
                matchTableView.setItems((this.getMatches()));
            }

        });

        playerTableView.setItems(this.getPlayers());

        matchTableView.setItems((this.getMatches()));

        hbox1.getChildren().setAll(playerTableView, matchTableView);

        hbox2.getChildren().setAll(insertMatchButton, matchp1, matchp2, year, month, day, tournament, score);

        hbox3.getChildren().setAll(saveToFile,saveToFileButton, importFileButton, resetButton);

        hbox4.getChildren().setAll(insertPlayerButton, playerId, firstName, lastName, birthYear, country, deletePlayerButton, playerIDDelete);

        BorderPane primaryBorderPane = new BorderPane();
       // primaryBorderPane.setTop(menuBar);
        primaryBorderPane.setCenter(vbox);
       // primaryBorderPane.getBottom(errorText);

        Scene primaryScene = new Scene(primaryBorderPane);
        primaryStage.setScene(primaryScene);
        primaryStage.show();

        File file;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(.txt)","txt");
        chooser.setFileFilter(filter);

        int returnVal = chooser.showDialog(null,"Import");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            database.loadFromFile(file.toString());
            playerTableView.setItems(this.getPlayers());
            matchTableView.setItems((this.getMatches()));
        }

    }

    public ObservableList getPlayers() throws TennisDatabaseRuntimeException {
        ObservableList<TennisPlayer> players = FXCollections.observableArrayList();
        try {
            players.addAll(database.getAllPlayers());
        }
        catch (TennisDatabaseRuntimeException e){
            throw e;
        }
        return players;
    }

    public ObservableList getMatches() throws TennisDatabaseRuntimeException {
        ObservableList<TennisMatch> matches = FXCollections.observableArrayList();
        try {
            matches.addAll(database.getAllMatches());
        }
        catch (TennisDatabaseRuntimeException e){
            throw e;
        }
        return matches;
    }


}
