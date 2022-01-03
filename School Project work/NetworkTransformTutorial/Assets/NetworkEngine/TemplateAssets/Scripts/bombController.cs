using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using NETWORK_ENGINE;

public class bombController : NetworkComponent
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void OnTriggerEnter(Collider c)
    {

        /*if(c.gameObject.tag == "Floor")
        /{
            if (IsServer)
            {
                MyCore.NetDestroyObject(MyId.NetId);
            }
            if (IsClient)
            {
                //create explosion here, cosmetic only
            }
        
    } */
    }

    public override IEnumerator SlowUpdate()
    {
        yield return new WaitForSeconds(.1f);
    }

    public override void HandleMessage(string flag, string value)
    {

    }

    public override void NetworkedStart()
    {

    }
}
