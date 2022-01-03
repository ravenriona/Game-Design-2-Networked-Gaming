using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using NETWORK_ENGINE;

public class Tutorial1 : NetworkComponent
{

    public bool IsReady = false;

    public Text statusMessage;
    public Toggle toggle;
    public override void HandleMessage(string flag, string value)
    {
        if(flag == "TOG")
        {
            if (IsServer)
            {
                IsReady = bool.Parse(value);

                SendUpdate("TOG", value);
            }
            if (IsClient)
            {
                IsReady = bool.Parse(value);
                if (!IsLocalPlayer)
                {
                    toggle.isOn = IsReady;
                }
                if (IsReady)
                {
                    statusMessage.text = "The Player is Ready!";
                }
                else
                {
                    statusMessage.text = "The Player is Not Ready!";
                }
            }
        }
    }

    public override void NetworkedStart()
    {

    }

    public override IEnumerator SlowUpdate()
    {
        this.transform.position = new Vector3(-5 + Owner * 10, 0, 0);
        if (!IsLocalPlayer)
        {
            toggle.interactable = false;
        }
        while (IsServer)
        {
            if (IsDirty)
            {
                SendUpdate("TOG", IsReady.ToString());
                IsDirty = false;
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
        
    }

    public void OnToggleClick(bool t)
    {
        if (MyId.IsInit && IsLocalPlayer)
        {
            SendCommand("TOG", t.ToString());
        }
    }
}
