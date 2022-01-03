using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using NETWORK_ENGINE;
using UnityEngine.AI;

[RequireComponent(typeof(NavMeshAgent))]
public class NavMeshController : NetworkComponent
{
    public int HP = 3;
    public Vector3 Goal;
    NavMeshAgent MyAgent;

    public override void HandleMessage(string flag, string value)
    {
        if (flag == "HP") 
        {
            HP = int.Parse(value);
        }
        
    }

    public override void NetworkedStart()
    {

    }

    public override IEnumerator SlowUpdate()
    {
        if (IsClient)
        {

        }
        if (IsServer)
        {
            foreach (RigidBodyController lp in FindObjectsOfType<RigidBodyController>()){
                if ((this.transform.position - lp.transform.position).magnitude < .7f)
                {
                    Goal = lp.transform.position;
                }
            }
            MyAgent.SetDestination(Goal);
        }
        while (IsServer)
        {
            foreach (RigidBodyController lp in FindObjectsOfType<RigidBodyController>())
            {
                if ((this.transform.position - lp.transform.position).magnitude < 3)
                {
                    Goal = lp.transform.position;
                    MyAgent.SetDestination(Goal);
                }
                if (MyAgent.remainingDistance < .5f && !((this.transform.position - lp.transform.position).magnitude < 3))
            {
                    Goal = new Vector3(Goal.x * -1, Goal.y, Goal.z);

                    MyAgent.SetDestination(Goal);

                }
            }
            yield return new WaitForSeconds(.1f);
        }
    }

    // Start is called before the first frame update
    void Start()
    {
        MyAgent = this.GetComponent<NavMeshAgent>();    
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void OnTriggerEnter(Collider other)
    {
        if(other.gameObject.tag == "BOMB")
        {
            if (IsClient)
            {

            }
            if (IsServer)
            {
                MyCore.NetDestroyObject(other.gameObject.GetComponent<NetworkID>().NetId);
                HP--;
                if(HP < 1)
                {
                    MyCore.NetDestroyObject(MyId.NetId);
                }
                else
                {
                    SendUpdate("HP", HP.ToString());
                }
            }
            if (IsLocalPlayer)
            {

            }
        }
    }
}
