using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using NETWORK_ENGINE;

public class ChatBubbleScript : NetworkComponent
{

    public string Message = "";

    public Text statusMessage;
    public InputField input;
    public override void HandleMessage(string flag, string value)
    {
        if (flag == "CHAT")
        {
            if (IsServer)
            {
                Message = value.ToString();
                statusMessage.text = Message;
                SendUpdate("CHAT", value);
            }
            if (IsClient)
            {
                Message = value.ToString();
                statusMessage.text = Message;
                if (!IsLocalPlayer)
                {
                    //Message = value.ToString();
                }
                /*if (IsReady)
                {
                    statusMessage.text = "The Player is Ready!";
                }
                else
                {
                    statusMessage.text = "The Player is Not Ready!";
                }*/

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
            input.gameObject.SetActive(false);
        }
        while (IsServer)
        {
            if (IsDirty)
            {
                SendUpdate("CHAT", Message);
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

    public void OnPressEnter(string t)
    {
        if (MyId.IsInit && IsLocalPlayer)
        {
            SendCommand("CHAT", t.ToString());
        }
    }
}
