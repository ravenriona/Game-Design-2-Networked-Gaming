//Braxton Friend
//CS102
//Assignment 1
package TennisDatabase;

class TennisPlayerContainer implements TennisPlayerContainerInterface {
    private TennisPlayerContainerNode root;
    private int playerCount;

    TennisPlayerContainer() {
        this.playerCount = 0;
        this.root = null;
    }

    // Desc.: Returns the number of players in this container.
    // Output: The number of players in this container as an integer.
    public int getNumPlayers() { return this.playerCount; }

    // Desc.: Returns an iterator object ready to be used to iterate this container.
    // Output: The iterator object configured for this container.
    public TennisPlayerContainerIterator iterator() { return new TennisPlayerContainerIterator(this.root); }

    //Searches through nodes to find player. Throws exception if id does not exist
    public TennisPlayer getPlayer(String id) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode playerNode = getPlayerNodeRec(this.root, id.toUpperCase());
        if (playerNode == null) {
            throw new TennisDatabaseRuntimeException("***Player Not Found***");
        }
        else {
            return playerNode.getPlayer();
        }
    }

    public TennisPlayerContainerNode getPlayerNodeRec(TennisPlayerContainerNode root, String id) {
        if (root == null) {
            return null;
        }
        else {
            int compare = root.getPlayer().getId().compareTo(id);
            if (compare == 0) {
                return root;
            }
            else if (compare < 0 ) {
                return getPlayerNodeRec(root.getLeft(), id);
            }
            else {
                return getPlayerNodeRec(root.getRight(), id);
            }
        }
    }

    public void deletePlayer(String id) throws TennisDatabaseRuntimeException {
        this.root = deletePlayerNodeRec(this.root, id);
        playerCount--;
    }

    public TennisPlayerContainerNode deletePlayerNodeRec(TennisPlayerContainerNode root, String id) throws TennisDatabaseRuntimeException {
       if (root == null) {
           throw new TennisDatabaseRuntimeException("***Player Not Found***");
       }
       else {
           int compare = root.getPlayer().getId().compareTo(id);
           if (compare == 0) {
               return deleteNode(root);
           }
           else if (compare < 0) {
               TennisPlayerContainerNode newRightChild = deletePlayerNodeRec(root.getRight(), id);
               root.setRight(newRightChild);
               return root;
           }
           else {
               TennisPlayerContainerNode newLeftChild = deletePlayerNodeRec(root.getLeft(), id);
               root.setLeft(newLeftChild);
               return root;
           }
       }
    }

    public TennisPlayerContainerNode deleteNode(TennisPlayerContainerNode root) {
        if (root.getLeft() == null && root.getRight() == null) {
            return null;
        }
        else if (root.getLeft() != null && root.getRight() == null) {
            return root.getLeft();
        }
        else if (root.getRight() == null && root.getRight() != null) {
            return root.getRight();
        }
        else {
            TennisPlayerContainerNode leftMostNode = findLeftMostNode(root.getRight());
            root.setPlayer(leftMostNode.getPlayer());
            root.setMatchList(leftMostNode.getMatchList());
            TennisPlayerContainerNode deletedNode = deleteLeftMostNodeRec(root.getRight());
            root.setRight(deletedNode);
            return deletedNode;
        }
    }

    private TennisPlayerContainerNode deleteLeftMostNodeRec(TennisPlayerContainerNode root) {
        if (root == null) { return null; }
        else if (root.getLeft() == null) { return root.getRight(); }
        else {
            TennisPlayerContainerNode newLeftNode = deleteLeftMostNodeRec(root.getRight());
            root.setLeft(newLeftNode);
            return root;
        }
    }

    private TennisPlayerContainerNode findLeftMostNode(TennisPlayerContainerNode root) {
        if (root.getLeft() == null) {
            return root;
        }
        else {
            return findLeftMostNode(root.getLeft());
        }
    }

    public void insertPlayer(TennisPlayer p) throws TennisDatabaseException {
        this.root = insertPlayerRec(this.root, p);
        playerCount++;
    }

    private TennisPlayerContainerNode insertPlayerRec(TennisPlayerContainerNode root, TennisPlayer p) throws TennisDatabaseException {
        if (root == null) {
            return new TennisPlayerContainerNode(p);
        }
        else {
            int compare = root.getPlayer().compareTo(p);
            if (compare == 0) {
                throw new TennisDatabaseException("...");
            }
            else if (compare < 0) {
                root.setLeft(insertPlayerRec(root.getLeft(), p));
                return root;
            }
            else {
                root.setRight(insertPlayerRec(root.getRight(), p));
                return root;
            }
        }
    }


    //Adds match to proper player nodes
    public void insertMatch(TennisMatch m) throws TennisDatabaseException {
        // Extract the ids of player1 and player2 of the input match "m"
        String idPlayer1 = m.getIdPlayer1();
        String idPlayer2 = m.getIdPlayer2();

        TennisPlayerContainerNode p1Node = getPlayerNodeRec(this.root, idPlayer1);
        TennisPlayerContainerNode p2Node = getPlayerNodeRec(this.root, idPlayer2);

        if ((p1Node != null) && (p2Node != null)) {
            p1Node.insertMatch(m);
            p2Node.insertMatch(m);
        }
        else {
            throw new TennisDatabaseException("***One of the players could not be found***");
        }

    }


    public TennisPlayer[] getAllPlayers() {
        TennisPlayerContainerIterator iterator = this.iterator();
        iterator.setInorder();
        TennisPlayer[] outputArray = new TennisPlayer[playerCount];
        int index = 0;
        while (iterator.hasNext()) {
            outputArray[index] = iterator.next();
            index++;
        }
        return  outputArray;
    }

    public TennisMatch[] getMatchesOfPlayer(String playerId) throws TennisDatabaseRuntimeException {
        TennisPlayerContainerNode playerNode = getPlayerNodeRec(this.root, playerId);

        return playerNode.getMatches();
    }


}
