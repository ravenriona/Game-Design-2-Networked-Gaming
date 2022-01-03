using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using NETWORK_ENGINE;

public class Net_Character_Controller : NetworkComponent
{

    public int Team;
    public override void HandleMessage(string flag, string value)
    {
    }

    public override void NetworkedStart()
    {
    }

    public override IEnumerator SlowUpdate()
    {
        foreach (LobbyPlayerScript lp in GameObject.FindObjectsOfType<LobbyPlayerScript>())
        {
            if(lp.Owner == this.Owner)
            {
                yield return new WaitUntil(() => lp.IsReady);
                switch (Team)
                {
                    case 0:
                        this.GetComponent<Renderer>().material.color = new Color32(255, 0, 0, 255);
                        break;
                    case 1:
                        this.GetComponent<Renderer>().material.color = new Color32(0, 0, 255, 255);
                        break;
                    case 2:
                        this.GetComponent<Renderer>().material.color = new Color32(0, 255, 0, 255); 
                        break;
                    case 3:
                        this.GetComponent<Renderer>().material.color = new Color32(255, 102, 178, 128);
                        break;

                }
            }
        }
        yield return new WaitForSeconds(.1f);
    }

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
