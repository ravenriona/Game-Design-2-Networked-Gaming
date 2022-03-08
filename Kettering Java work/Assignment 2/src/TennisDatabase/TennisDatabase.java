//Braxton Friend
//CS102
//Assignment 2
package TennisDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Iterator;


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

    // Desc.: Saves data to file following the format described in the specifications.
    // Output: Throws a checked (critical) exception if the file open for writing fails.
    @Override
    public void saveToFile( String fileName ) throws TennisDatabaseException {

        PrintStream output;

        try {
            output = new PrintStream(fileName);
        }
        catch (FileNotFoundException e) {
            throw new TennisDatabaseException("***FILE NOT FOUND***");
        }

        TennisPlayerContainerIterator playersIterator = playerContainer.iterator();
        playersIterator.setInorder();

        while (playersIterator.hasNext()) {
            output.println(playersIterator.next().getExport());
        }
        Iterator<TennisMatch> matchesIterator = matchContainer.iterator();

        while (matchesIterator.hasNext()) {
            output.println((matchesIterator.next().getExport()));
        }
    }

    // Desc.: Resets the database, making it empty.
    @Override
    public void reset() {
        this.playerContainer = new TennisPlayerContainer();
        this.matchContainer = new TennisMatchContainer();

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
        return playerContainer.getMatchesOfPlayer(playerID);
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

        if (match.getWinner() == 1) {
            getPlayer(match.getIdPlayer1()).addWin();
            getPlayer(match.getIdPlayer2()).addLoss();
        }
        else {
            getPlayer(match.getIdPlayer1()).addLoss();
            getPlayer(match.getIdPlayer2()).addWin();
        }
    }

    // Desc.: Search for a player in the database by id, and delete it with all his matches (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no player with that input id.
    @Override
    public void deletePlayer( String playerId ) throws TennisDatabaseRuntimeException {
        matchContainer.deleteMatchesOfPlayer(playerId.toUpperCase());
        playerContainer.deletePlayer(playerId.toUpperCase());
    }

    public int[] splitDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);

        int[] output = new int[3];
        output[0] = Integer.parseInt(year);
        output[1] = Integer.parseInt(month);
        output[2] = Integer.parseInt(day);

        return output;
    }

}
