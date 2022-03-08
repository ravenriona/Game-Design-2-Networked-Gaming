//Braxton Friend
//CS 102-01
//Assignment 1


import TennisDatabase.TennisDatabase;
import TennisDatabase.TennisDatabaseRuntimeException;
import TennisDatabase.TennisDatabaseException;
import java.util.Scanner;

public class Assignment1 {



    public static void main(String[] args) throws TennisDatabaseException {
        Scanner getInput = new Scanner(System.in);
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
