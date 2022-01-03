using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using NETWORK_ENGINE;

public class Item
{
    public int ItemId;
    public int ItemQuantity;

    public Item(int i, int q)
    {
        ItemId = i;
        ItemQuantity = q;
    }
    public override string ToString()
    {
       return ItemId.ToString() + "," + ItemQuantity.ToString();
    }

    public static Item Parse(string v)
    {
        string[] temp = v.Split(',');
        Item ret = new Item(int.Parse(temp[0]), int.Parse(temp[1]));
        return ret;
    }
}

public class Inventory
{
    public List<Item> inventory;
    public void Append(Item i)
    {
        inventory.Add(i);
    }
    int Count
    {
        get { return inventory.Count; }
    }
    public Inventory()
    {
        inventory = new List<Item>();
    }

}

public class NetworkComponentExample : NetworkComponent
{
    public int Score;
    public float Speed;
    
    private int hp = 0;
    public int HP
    {
        get { return hp; }
        set {
                if (IsServer)
                {
                hp = value;
                SendUpdate("HP", hp.ToString());
                }
            }
    }
    public override void HandleMessage(string flag, string value)
    {
        //Recieve Messages

        if(flag == "SCORE")
        {
            if (IsServer)
            {
                Score++;
                SendUpdate("SCORE", Score.ToString());

            }
            else
            {
                Score = int.Parse(value);
            }
        }
        if(flag == "HP")
        {
            if (IsClient) {
                hp = int.Parse(value);
            }
        }

    }

    public override void NetworkedStart()
    {
        //Initialize elements
        if (IsServer)
        {
            Score = 0;
            HP = 10;
            SendUpdate("SCORE", Score.ToString());
        }
        if (IsClient)
        {

        }
        if (IsLocalPlayer)
        {

        }

    }

    public override IEnumerator SlowUpdate()
    {
        //
        while (true)
        {
            if (IsServer)
            {
                //Game Logic and send update
                if (IsDirty)
                {
                    SendUpdate("SCORE", Score.ToString());
                    IsDirty = false;
                }
            }
            if (IsClient)
            {
                //Update visual queue
            }
            if (IsLocalPlayer)
            {
                //collect input and send commands
                if (Input.GetAxis("Jump") > 0)
                {
                    //Pressed or holding space
                    //Score++;
                    SendCommand("SCORE", "");

                }
            }
            yield return new WaitForSeconds(.1f);
        }


    }

    // Start is called before the first frame update
    void Start()
    {
        Item x = new Item(2, 10);

        Debug.Log(x.ToString());

        Item y = Item.Parse(x.ToString());
        Debug.Log(y.ToString());
    }

    // Update is called once per frame
    void Update()
    {
        if (IsClient)
        {
            //Update animations based on movement
            //Update and spawn client side visual FX ONLY
            //DO NOT SEND UPDATES OR COMMANDS HERE
        }

    }
}
