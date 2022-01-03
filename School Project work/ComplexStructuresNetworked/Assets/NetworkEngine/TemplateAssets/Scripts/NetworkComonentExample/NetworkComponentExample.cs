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
    public bool DidChange = false;
    private List<Item> inventory;
    public int Cursor = 0;
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

    public override string ToString()
    {
        string tmp = "";
        tmp += Cursor.ToString();
        for(int i = 0; i< inventory.Count; i++)
        {
            tmp += ";" + inventory[i].ToString();
        }

        return tmp;
    }
    public static Inventory Parse(string value)
    {
        Inventory ret = new Inventory();
        string[] arg = value.Split(':');
        for(int i = 0; i< arg.Length; i++)
        {
            if(i == 0)
            {
                ret.Cursor = int.Parse(arg[0]);
            }
            else
            {
                ret.Append(Item.Parse(arg[i]));
            }
        }
        return ret;
    }

}

public class NetworkComponentExample : NetworkComponent
{
    public int Score;
    public float Speed;
    public Inventory MyInventory = new Inventory();
    
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
        if(flag == "INV")
        {
            if (IsClient)
            {
                MyInventory = Inventory.Parse(value);
            }
            if (IsServer)
            {
                if (MyInventory.DidChange)
                {

                }
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
                if (MyInventory.DidChange)
                {
                    SendUpdate("INV", MyInventory.ToString());
                    MyInventory.DidChange = false;
                }
                if (IsDirty)
                {
                    SendUpdate("SCORE", Score.ToString());
                    SendUpdate("INV", MyInventory.ToString());
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
