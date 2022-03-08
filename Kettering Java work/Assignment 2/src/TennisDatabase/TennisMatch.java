//Braxton Friend
//CS102
//Assignment 1
package TennisDatabase;

public class TennisMatch implements TennisMatchInterface {
    private String idPlayer1;
    private String idPlayer2;
    private int year;
    private int month;
    private int day;
    private String tournament;
    private String score;

    private int winner;

    public TennisMatch(String idPlayer1, String idPlayer2, int year, int month, int day, String tournament, String score) {
        this.idPlayer1 = idPlayer1.toUpperCase();
        this.idPlayer2 = idPlayer2.toUpperCase();
        this.year = year;
        this.month = month;
        this.day = day;
        this.tournament = tournament;
        this.score = score;
        try {
            this.winner = TennisMatchInterface.processMatchScore(this.score);
        }
        catch (TennisDatabaseRuntimeException e) {
            throw new TennisDatabaseRuntimeException("Match creation failed, invalid score");
        }
    }

    //copy the constructor
    public TennisMatch(TennisMatch match) {
        this.idPlayer1 = match.idPlayer1;
        this.idPlayer2 = match.idPlayer2;
        this.year = match.year;
        this.month = match.month;
        this.day = match.day;
        this.tournament = match.tournament;
        this.score = match.score;
        this.winner = match.winner;
    }

    @Override
    public String getIdPlayer1() {
        return idPlayer1;
    }

    @Override
    public String getIdPlayer2() {
        return idPlayer2;
    }

    @Override
    public int getDateYear() {
        return year;
    }

    @Override
    public int getDateMonth() {
        return month;
    }

    @Override
    public int getDateDay() {
        return day;
    }

    public String getDateString() {
        String output = day + "/" + month + "/" + year;
        return output;
    }

    @Override
    public String getTournament() {
        return tournament;
    }

    @Override
    public String getMatchScore() {
        return score;
    }

    @Override
    public int getWinner() {
        return winner;
    }

    @Override
    public int compareTo(TennisMatch inMatch) throws NullPointerException {
        if (this.year > inMatch.year) {
            return 1;
        }
        else if(this.year < inMatch.year) {
            return -1;
        }
        else {
            if (this.month > inMatch.month) {
                return 1;
            }
            else if(this.month < inMatch.month) {
                return -1;
            }
            else {
                if (this.day > inMatch.day) {
                    return 1;
                }
                else if (this.day < inMatch.day) {
                    return -1;
                }
                else {
                    return 1;
                }
            }
        }
    }

    public String getExport() {
        String outputYear = String.format("%04d", year);
        String outputMonth = String.format("%02d", month);
        String outputDay = String.format("%02d", day);

        String output = "MATCH/" + idPlayer1 + "/" + idPlayer2 + "/" + outputYear + outputMonth + outputDay + "/" + tournament + "/" + score;

        return output;
    }
}
