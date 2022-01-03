using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using NETWORK_ENGINE;
public class LobbyPlayerScript : NetworkComponent
{

    public bool IsReady;

    public Toggle ReadyToggle;
    public Image MyLobbyBackground;
    public Text WinnerID;
    public int[,] BingoArray;
    public Text[] TextArray = new Text[25];
    public Text[,] DisplayArray = new Text[5, 5];
    public int currentNumber = -1;
    [SerializeField]
    //public GameObject GameManager;
    GameManagerScript managerScript;

    static bool GameOver = false;

    public override void HandleMessage(string flag, string value)
    {
        switch (flag)
        {
           
           
            case "READY":
                IsReady = bool.Parse(value);
                if (IsClient)
                {
                    ReadyToggle.isOn = IsReady;
                    if (IsReady)
                    {
                        ReadyToggle.interactable = false;
                    }
                }
                if (IsServer)
                {
                    SendUpdate("READY", value);


                }
                break;
            case "PICK":
                if (IsClient)
                {
                    Debug.Log("Reveived " + value);
                    currentNumber = int.Parse(value);
                    updateDisplayArray(BingoArray, DisplayArray);
                }
                if (IsServer)
                {
                    SendUpdate("PICK", value);
                }
                break;
            case "ARRAYSEND":
                if (IsClient)
                {
                    BingoArray = Parse2DArrayToIntArray(value);
                    setDisplayArray(BingoArray, DisplayArray);

                }
                if (IsServer)
                {
                    SendUpdate("ARRAYSEND", value);
                }
                break;
            case "WIN":
                if (IsClient)
                {
                    WinnerID.text = "Winner is card # " + value; 
                }
                break;
                



        }
    }

    public override void NetworkedStart()
    {
        if (IsServer)
        {

        }
    }

    public override IEnumerator SlowUpdate()
    {
        if (IsServer)
        {
            
            BingoArray = MakeBingoCard(); 
            SendUpdate("ARRAYSEND", IntArray2DToString(BingoArray));
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

        do
        {


            managerScript = GameObject.FindObjectOfType<GameManagerScript>();

            yield return new WaitForSeconds(1);
        } while (managerScript == null);

        if (!IsLocalPlayer)
        {
            ReadyToggle.interactable = false;
        }

        

        

        while (MyCore.IsConnected)
        {


            if (IsLocalPlayer)
            {

                
                //input here
                //any local player only notifications
                
            }
            if (IsClient)
            {

                updateDisplayArray(BingoArray, DisplayArray);   

            }
            if (IsServer)
            {
                if(managerScript.GameStarted && !GameOver)
                {
                    Debug.Log("here");
                    currentNumber = PickNumber();
                        updateDisplayArray(BingoArray, DisplayArray);
                        SendUpdate("PICK", currentNumber.ToString());
                        updateDisplayArray(BingoArray, DisplayArray);
                        if (didWin(BingoArray))
                        {
                        GameOver = true;

                            SendUpdate("WIN", NetId.ToString());
                        }
                    
                }

                if (IsDirty)
                {
                    SendUpdate("READY", IsReady.ToString());
                    SendUpdate("PICK", currentNumber.ToString());
                    SendUpdate("ARRAYSEND", IntArray2DToString(BingoArray));
                    IsDirty = false;

                }
            }

            yield return new WaitForSeconds(.1f);
        }
    }

    // Start is called before the first frame update
    void Start()
    {
        BingoArray = new int[5, 5];
        
        DisplayArray = OneDimtoTwoDim(TextArray, DisplayArray);
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

    public int[,] MakeBingoCard()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (j == 0)
                {
                    BingoArray[i, j] = Random.Range(1, 16);
                }
                else if (j == 1)
                {
                    BingoArray[i, j] = Random.Range(16, 31);
                }
                else if (j == 2)
                {
                    BingoArray[i, j] = Random.Range(31, 46);
                }
                else if (j == 3)
                {
                    BingoArray[i, j] = Random.Range(46, 61);
                }
                else if (j == 4)
                {
                    BingoArray[i, j] = Random.Range(61, 76);
                }
            }
        }

        return BingoArray;
    }

    public int PickNumber()
    {
        int randNumb = Random.Range(0, 76);

        return randNumb;
       
    }
    
    public void setDisplayArray(int [,] bingoArray, Text[,] displayArray)
    {
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                DisplayArray[i, j].text = BingoArray[i, j].ToString();
            }
        }
    }
    public void updateDisplayArray(int[,] bingoArray, Text[,] displayArray)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
              

                    managerScript = GameObject.FindObjectOfType<GameManagerScript>();

                Debug.Log(managerScript.GameStarted);
                Debug.Log(currentNumber);
                if (currentNumber == BingoArray[i, j] && managerScript.GameStarted)
                {
                    if (IsClient)
                    {
                        Debug.Log("IN CLIENT");
                    }
                    BingoArray[i, j] = 0;
                    DisplayArray[i, j].color = Color.red;
                }
            }
        }
    }

    public static int[,] Parse2DArrayToIntArray(string v)
    {
        Debug.Log(v);
        int count = 0;
        string[] temp = v.Split(',');
        int[,] ret = new int[5, 5];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                ret[i, j] = int.Parse(temp[count]);
                count++;
            }
        }
        return ret;

    }
   
    public string IntArray2DToString(int[,] sendArray)
    {
        string StringArray = "";
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                StringArray = StringArray + sendArray[i, j].ToString() + ",";
            }
        }
        return StringArray;
    }

    public Text[,] OneDimtoTwoDim(Text[] OneDim, Text[,] TwoDim) 
    {
        int count = 0;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                TwoDim[i, j] = OneDim[count];
                count++;
            }
        }
        return TwoDim;
    }

    public bool didWin(int[,] bingoArray)
    {
        bool wonFlag = true;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if(BingoArray[i,j] != 0)
                {
                    wonFlag = false;

                }
            }
            if (wonFlag == true)
            {
                return wonFlag;
            }
            wonFlag = true;
        }
        
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (BingoArray[j, i] != 0)
                {
                    wonFlag = false;
                }
            }
            if (wonFlag == true)
            {
                return wonFlag;
            }
            wonFlag = true;
        }
        for (int i = 0; i < 5; i++)
        {
            if (BingoArray[i, i] != 0)
            {
                wonFlag = false;
            }
        }
        if (wonFlag == true)
        {
            return wonFlag;
        }
        wonFlag = true;
        for (int i = 0; i < 5; i++)
        {
            if (BingoArray[i, 4-i] != 0)
            {
                wonFlag = false;
            }
        }
        if (wonFlag == true)
        {
            return wonFlag;
        }
        return wonFlag;
    }

 

   /* public static int[,] Parse2DArrayToTextArray(string v)
    {
        int count = 0;
        string[] temp = v.Split(',');
        Text[,] ret = new int[5, 5];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                ret[i, j] = .Parse(temp[count]);
            }
        }
        return ret;

    }
    public string TextArray2DToString(Text[,] sendArray)
    {
        string StringArray = "";
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                StringArray = StringArray + "," + sendArray[i, j].ToString();
            }
        }
        return StringArray;
    }*/

}
