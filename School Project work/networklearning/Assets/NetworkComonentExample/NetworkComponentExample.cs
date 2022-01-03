using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using NETWORK_ENGINE;

public class NetworkComponentExample : NetworkComponent
{
    public int Score;
    
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
        throw new System.NotImplementedException();
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
        throw new System.NotImplementedException();
    }

    public override IEnumerator SlowUpdate()
    {
        //
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
                Score++; 
            }
        }
        yield return new WaitForSeconds(.1f);
        throw new System.NotImplementedException();
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
