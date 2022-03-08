//Braxton Friend
//CS102
//Assignment 2
package TennisDatabase;

import java.util.Iterator;
import java.util.ArrayList;


public class TennisMatchContainer implements TennisMatchContainerInterface {

    private ArrayList<TennisMatch> matchesList;

    public TennisMatchContainer() {
        this.matchesList = new ArrayList<TennisMatch>();
    }

    // Desc.: Returns the number of matches in this container.
    // Output: The number of matches in this container as an integer.
    public int getNumMatches(){return this.matchesList.size();}

    // Desc.: Insert a tennis match into this container.
    // Input: A tennis match.
    // Output: Throws a checked (critical) exception if the container is full.
    public void insertMatch( TennisMatch m ) throws TennisDatabaseException {
        this.matchesList.add(m);
    }

    // Desc.: Returns all matches in the database arranged in the output array (sorted by date, most recent first).
    // Output: Throws an exception if there are no matches in this container.
    public TennisMatch[] getAllMatches() throws TennisDatabaseRuntimeException {
        TennisMatch[] matchesArray = new TennisMatch[getNumMatches()];

        for (int i = 0; i < matchesArray.length; i++){
            matchesArray[i] = matchesList.get(i);
        }

        return matchesArray;
    }

    // Desc.: Returns all matches of input player (id) arranged in the output array (sorted by date, most recent first).
    // Input: The id of the tennis player.
    // Output: Throws an unchecked (non-critical) exception if there are no tennis matches in the list.
    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {

        int totalMatches = 0;
        for (int i = 0; i< matchesList.size(); i++) {
            if (matchesList.get(i).getIdPlayer1().equals(playerId) || matchesList.get(i).getIdPlayer2().equals(playerId)) {
                totalMatches++;
            }
        }
        TennisMatch[] output = new TennisMatch[totalMatches];
        int outputIndex = 0;
        for (int i =0; i < matchesList.size(); i++) {
            if (matchesList.get(i).getIdPlayer1().equals(playerId) || matchesList.get(i).getIdPlayer2().equals(playerId)) {
                output[outputIndex] = matchesList.get(i);
                outputIndex++;
            }
        }

        return output;
    }

    // Desc.: Delete all matches of a player by id (if found).
    // Output: Throws an unchecked (non-critical) exception if there is no match with that input id.
    public void deleteMatchesOfPlayer( String playerId ) throws TennisDatabaseRuntimeException {

        for (int i = 0; i < matchesList.size(); i++) {
            if (matchesList.get(i).getIdPlayer1().equals(playerId) || matchesList.get(i).getIdPlayer2().equals(playerId)) {
                matchesList.remove(i);
            }
        }

    }


    // Desc.: Returns an iterator object ready to be used to iterate this container.
    // Output: The iterator object configured for this container.
    public Iterator<TennisMatch> iterator() { return this.matchesList.iterator();


    }

}

