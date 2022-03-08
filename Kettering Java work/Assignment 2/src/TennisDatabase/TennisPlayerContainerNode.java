//Braxton Friend
//CS 102
//Assignment 2

package TennisDatabase;

class TennisPlayerContainerNode implements TennisPlayerContainerNodeInterface
{

    private TennisPlayerContainerNode left;
    private TennisPlayerContainerNode right;
    private TennisPlayer player;
    private SortedLinkedList<TennisMatch> list; // List of matches of this player.

    public TennisPlayerContainerNode(TennisPlayer inputPlayer)
    {
        this.left = null;
        this.right = null;
        this.player = inputPlayer;
        this.list = new SortedLinkedList<TennisMatch>();

    }


    public TennisPlayer getPlayer()
    {
        return this.player;
    }

    public TennisPlayerContainerNode getLeft()
    {
        return this.left;
    }

    public TennisPlayerContainerNode getRight()
    {
        return this.right;
    }

    public void setPlayer(TennisPlayer player)
    {
        this.player = player;
    }

    public void setLeft(TennisPlayerContainerNode l)
    {
        this.left = l;
    }

    public void setRight(TennisPlayerContainerNode r)
    {
        this.right = r;
    }

    public void insertMatch(TennisMatch m) throws TennisDatabaseException
    {
        try
        {
            list.insert(m);
        } catch (Exception e)
        {
            throw new TennisDatabaseException("");
        }
    }

    @Override
    public TennisMatch[] getMatches() throws TennisDatabaseRuntimeException
    {
        TennisMatch[] a = new TennisMatch[list.size()];
        for(int i = 0; i < list.size();i++)
        {
            a[i] = list.get(i);
        }


        TennisMatch[] b = new TennisMatch[a.length];

        for (int i = 0; i < a.length; i++)
        {
            b[i] = new TennisMatch(a[i]);
        }
        return b;
    }

    public SortedLinkedList<TennisMatch> getMatchList(){
        return this.list;
    }

    public void setMatchList(SortedLinkedList<TennisMatch> matches){
        this.list = matches;
    }
}