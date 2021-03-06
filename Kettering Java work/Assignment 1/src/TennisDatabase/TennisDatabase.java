//Braxton Friend
//CS102
//Assignment 1
package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TennisDatabase implements TennisDatabaseInterface{

    private TennisMatchContainer matchContainer = new TennisMatchContainer();
    private TennisPlayerContainer playerContainer = new TennisPlayerContainer();

    @Override
    public void loadFromFile(String fileName) throws TennisDatabaseException, TennisDatabaseRuntimeException {

        Scanner scanFile;

        try {
            File databaseFile = new File(fileName);
            scanFile = new Scanner(databaseFile).useDelimiter("[\\r\\n]+");
        }
        catch(FileNotFoundException e) {
            throw new TennisDatabaseException("File not found");

        }

        //inputting players and matches from input file
        while (scanFile.hasNextLine()) {
            String inputString = scanFile.nextLine();
            Scanner inputScan = new Scanner(inputString).useDelimiter("[\\r\\n/]");
            String token = inputScan.next().toUpperCase();

            if (token.equals("PLAYER")) {
                String id = inputScan.next().toUpperCase();
                String firstName = inputScan.next().toUpperCase();
                String lastName = inputScan.next().toUpperCase();
                int year = inputScan.nextInt();
                String country = inputScan.next().toUpperCase();
                insertPlayer(id, firstName, lastName, year, country);
            }

            if (token.equals("MATCH")) {

                String player1 = inputScan.next().toUpperCase();
                String player2 = inputScan.next().toUpperCase();
                int[] date = splitDate(inputScan.next());
                String location = inputScan.next().toUpperCase();
                String score = inputScan.next().toUpperCase();
                insertMatch(player1, player2, date[0], date[1], date[2], location, score);

            }
        }
    }

    @Override
    public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
        return playerContainer.getPlayer(id);
    }

    @Override
    public TennisPlayer[] getAllPlayers() throws TennisDatabaseRuntimeException {
        return playerContainer.getAllPlayers();
    }

    @Override
    public TennisMatch[] getMatchesOfPlayer(String playerID) throws TennisDatabaseRuntimeException {
        return playerContainer.getPlayerMatches(playerID);
    }

    @Override
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
        return matchContainer.getAllMatches();
    }

    @Override
    public void insertPlayer(String id, String firstName, String lastName, int year, String country) throws TennisDatabaseException {
        try {
            TennisPlayer newPlayer = new TennisPlayer(id.toUpperCase(), firstName.toUpperCase(), lastName.toUpperCase(), year, country.toUpperCase());
            playerContainer.insertPlayer(newPlayer);
        }
        catch (TennisDatabaseException e) {
            throw new TennisDatabaseException("Tennis Database Exception for insertPlayer");
        }
    }

    @Override
    public void insertMatch(String player1, String player2, int year, int month, int day, String tournament, String score) throws TennisDatabaseException {
        TennisMatch match = new TennisMatch(player1, player2, year, month, day, tournament, score);

        try {
            matchContainer.insertMatch(match);
            playerContainer.insertMatch(match);
        }
        catch (TennisDatabaseException e) {
            throw new TennisDatabaseException("Invalid Match.");
        }
    }

    public int[] splitDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);

        int[] output = new int[3];
        output[0] = Integer.parseInt(year);
        output[1] = Integer.parseInt(month);
        output[2] = Integer.parseInt(date);

        return output;
    }

    public String[] getMatchesOfPlayerString(String id) {
        TennisMatch[] matchesArray = playerContainer.getPlayerMatches(id);
        if (matchesArray.length == 0) {
            String[] outputArray = new String[1];
            outputArray[0] = ("This player does not have any matches.");
            return outputArray;
        }
        else {
            String[] outputArray = new String[matchesArray.length];
            for (int i = 0; i < matchesArray.length; i++) {
                outputArray[i] = (String.format("%02d", matchesArray[i].getDateYear()) + "/" + String.format("%02d",
                        matchesArray[i].getDateMonth()) + "/" + String.format("%02d", matchesArray[i].getDateMonth()) + "," + " "
                        + playerContainer.getPlayer(matchesArray[i].getIdPlayer1()).getFirstName() + " "
                        + playerContainer.getPlayer(matchesArray[i].getIdPlayer1()).getLastName()
                        + " - " + playerContainer.getPlayer(matchesArray[i].getIdPlayer2()).getFirstName() + " "
                        + playerContainer.getPlayer(matchesArray[i].getIdPlayer2()).getLastName()
                        + "," + " " + matchesArray[i].getTournament() + "," + " " + matchesArray[i].getMatchScore());
            }
            return outputArray;

        }
    }

    public String[] getPlayerStringArray() {
        if (playerContainer.getPlayerCount() == 0) {
            String[] outputArray = new String[1];
            outputArray[0] = "No players found.";
            return outputArray;
        }
        else {
            String[] outputArray = new String[playerContainer.getPlayerCount()];
            TennisPlayer[] playerArray = getAllPlayers();
            calcWinLoss();
            for (int i = 0; i < playerArray.length; i++) {
                outputArray[i] = (playerArray[i].getId() + ": " + playerArray[i].getFirstName() + " " +
                        playerArray[i].getLastName() + ", " + playerArray[i].getBirthYear() + ", " +
                        playerArray[i].getWins() + "/" + playerArray[i].getLosses() + " " + "(WIN/LOSS)");
            }
            return outputArray;
        }
    }

    public void calcWinLoss() {
        TennisMatch[] array = getAllMatches();
        for (int i = 0; i < getMatchCount(); i++) {
            if (array[i].getWinner() == 1) {
                getPlayer(array[i].getIdPlayer1()).addWin();
                getPlayer(array[i].getIdPlayer2()).addLoss();
            }
            else {
                getPlayer(array[i].getIdPlayer1()).addLoss();
                getPlayer(array[i].getIdPlayer2()).addWin();
            }
        }
    }

    public int getMatchCount() {
        return matchContainer.getMatchCount();
    }

    public String[] getAllMatchesString() {
        TennisMatch[] array = getAllMatches();
        int arrayLength = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                arrayLength++;
            }
        }
        String[] output = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            output[i] = (String.format("%02d", array[i].getDateYear()) + "/" + String.format("%02d", array[i].getDateMonth()) +
                    "/" + String.format("%02d", array[i].getDateDay()) + "," + " " + getPlayer(array[i].getIdPlayer1()).getFirstName() + " " +
                    getPlayer(array[i].getIdPlayer1()).getLastName() + " - " + getPlayer(array[i].getIdPlayer2()).getFirstName() + " " +
                    getPlayer(array[i].getIdPlayer2()).getLastName() + "," + " " + array[i].getTournament() + "," + " " + array[i].getMatchScore());
        }
        return output;
    }
}