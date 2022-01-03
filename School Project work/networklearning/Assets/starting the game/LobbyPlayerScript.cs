using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using NETWORK_ENGINE;
public class LobbyPlayerScript : NetworkComponent
{
    public int Character;
    public int Team;
    public bool IsReady;
    public string Message;
    public bool startFlag = true;

    public Dropdown CharSelect;
    public Dropdown TeamSelect;
    public InputField nameSelect;
    public Toggle ReadyToggle;
    public Image MyLobbyBackground;
    public Text CharName;
    public override void HandleMessage(string flag, string value)
    {
        switch (flag)
        {
            case "TEAM":
                Team = int.Parse(value.ToString());
                if (IsServer)
                {
                    SendUpdate("TEAM", value);
                }
                if (IsClient)
                {
                    switch (Team)
                    {
                        case 0://red
                            MyLobbyBackground.color = new Color32(255, 0, 0, 128);
                            break;
                        case 1://blue
                            MyLobbyBackground.color = new Color32(0, 0, 255, 128);
                            break;
                        case 2://green
                            MyLobbyBackground.color = new Color32(0, 255, 0, 128);
                            break;
                        case 3://pink
                            MyLobbyBackground.color = new Color32(255, 102, 178, 128);
                            break;
                    }
                }
                break;
            case "CHAR":
                Character = int.Parse(value);
                if (IsServer)
                {
                    SendUpdate("CHAR", value);
                }
                break;
            case "READY":
                IsReady = bool.Parse(value);
                if (IsClient)
                {
                    ReadyToggle.isOn = IsReady;
                    //disables the toggle on ready
                    //if (IsReady)
                    //{
                    //    ReadyToggle.interactable = false;
                    //}
                }
                if (IsServer)
                {
                    SendUpdate("READY", value);


                }
                break;
            case "NAME":
                if (IsServer)
                {
                    Message = value.ToString();
                    CharName.text = Message;
                    SendUpdate("NAME", value);
                }
                if (IsClient)
                {
                    Message = value.ToString();
                    CharName.text = Message;
                }
                break;
                



        }
    }

    public override void NetworkedStart()
    {
       
    }

    public override IEnumerator SlowUpdate()
    {
        if (!IsLocalPlayer)
        {
            ReadyToggle.interactable = false;
            TeamSelect.gameObject.SetActive(false);
            CharSelect.gameObject.SetActive(false);
            nameSelect.gameObject.SetActive(false);
        }

        

        switch (Owner)
        {
            case 0:
                this.transform.position = new Vector3(-4, 4, 5);
                break;
            case 1:
                this.transform.position = new Vector3(4, 4, 5);
                break;
            case 2:
                this.transform.position = new Vector3(-4, -4, 5);
                break;
            case 3:
                this.transform.position = new Vector3(4, -4, 5);
                break;
        }

        while (MyCore.IsConnected)
        {

            bool startFlag = true;
            if (IsLocalPlayer)
            {
                //input here
                //any local player only notifications
                if (CharName == null)
                {
                    ReadyToggle.interactable = false;
                }
                else
                {
                    ReadyToggle.interactable = true;
                }
            }
            if (IsClient)
            {
                //
            }
            if (IsServer)
            {
                /*if (startFlag)
                {
                    foreach (Canvas ca in GameObject.FindObjectsOfType<Canvas>())
                    {
                        ca.gameObject.SetActive(false);
                        if (IsReady)
                        {
                            GameObject temp = MyCore.NetCreateObject(Character, Owner, this.transform.position = new Vector3(0, .5f, 0), Quaternion.identity);

                        }
                    }
                }*/
                if (IsDirty)
                {
                    SendUpdate("READY", IsReady.ToString());
                    SendUpdate("CHAR", Character.ToString());
                    SendUpdate("TEAM", Team.ToString());
                    SendUpdate("NAME", CharName.ToString());
                    IsDirty = false;

                }
            }
            startFlag = true;
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

    public void SetTeam(int t)
    {
        if(IsLocalPlayer && MyId.IsInit)
        {
            SendCommand("TEAM", t.ToString());
        }
    }

    public void SetCharacter(int c)
    {
        if (IsLocalPlayer && MyId.IsInit)
        {
            SendCommand("CHAR", c.ToString());
        }
    }

    public void SetReady(bool r)
    {
        if (IsLocalPlayer && MyId.IsInit)
        {
            SendCommand("READY", r.ToString());
        }
    }

    public void OnPressEnter(string n)
    {
        if (MyId.IsInit && IsLocalPlayer)
        {
            SendCommand("NAME", n.ToString());
        }
    }
}
