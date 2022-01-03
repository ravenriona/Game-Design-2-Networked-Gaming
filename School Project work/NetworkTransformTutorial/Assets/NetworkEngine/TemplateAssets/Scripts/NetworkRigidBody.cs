using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using NETWORK_ENGINE;

public class NetworkRigidBody : NetworkComponent
{
    public Vector3 LastPosition;
    public Vector3 LastRotation;
    public Vector3 LastVelocity;
    public Vector3 LastAngular;

    public Vector3 OffsetVelocity;

    public float MinThreshold = .05f;
    public float MaxThreshold = .25f;

    public Rigidbody MyRig;
    public bool UseOffsetVelocity = true;

    public static Vector3 VectorFromString(string value)
    {
        char[] temp = { '(', ')' };
        string[] args = value.Trim(temp).Split(',');
        return new Vector3(float.Parse(args[0].Trim()), float.Parse(args[1].Trim()), float.Parse(args[2].Trim()));
    }
    public override void HandleMessage(string flag, string value)
    {
        if(flag == "POS" && IsClient)
        {
            LastPosition = VectorFromString(value);
            float d = (MyRig.position - LastPosition).magnitude;
            if(d > MaxThreshold || !UseOffsetVelocity || LastVelocity.magnitude < .1)
            {
                OffsetVelocity = Vector3.zero;
                MyRig.position = LastPosition;
            }
            else if(LastVelocity.magnitude > .1)
            {
                OffsetVelocity = (LastPosition - MyRig.position);
            }
        }
        if(flag == "VEL" && IsClient)
        {
            LastVelocity = VectorFromString(value);
        }
        if(flag == "ROT" && IsClient)
        {
            LastRotation = VectorFromString(value);
            MyRig.rotation = Quaternion.Euler(LastRotation);
        }
        if(flag == "ANG" && IsClient)
        {
            LastAngular = VectorFromString(value);
        }
    }


    public override void NetworkedStart()
    {

    }

    public override IEnumerator SlowUpdate()
    {
        if (IsClient)
        {
            MyRig.useGravity = false;
        }
            while (true)
            {
                if (IsServer)
                {
                    if ((LastPosition - MyRig.position).magnitude > MinThreshold)
                    {
                        SendUpdate("POS", MyRig.position.ToString("F3"), false);
                        LastPosition = MyRig.position;
                    }
                    if ((LastVelocity - MyRig.velocity).magnitude > MinThreshold)
                    {
                        SendUpdate("VEL", MyRig.velocity.ToString("F3"), false);
                        LastVelocity = MyRig.velocity;
                    }
                    if ((LastRotation - MyRig.rotation.eulerAngles).magnitude > MinThreshold)
                    {
                        SendUpdate("ROT", MyRig.rotation.eulerAngles.ToString("F3"), false);
                        LastRotation = MyRig.rotation.eulerAngles;
                    }
                    if ((LastAngular - MyRig.angularVelocity).magnitude > MinThreshold)
                    {
                        SendUpdate("ANG", MyRig.angularVelocity.ToString("F3"), false);
                        LastAngular = MyRig.angularVelocity;
                    }
                    if (IsDirty)
                    {
                        SendUpdate("POS", MyRig.position.ToString("F3"), false);
                        SendUpdate("VEL", MyRig.velocity.ToString("F3"), false);
                        SendUpdate("ROT", MyRig.rotation.eulerAngles.ToString("F3"), false);
                        SendUpdate("ANG", MyRig.angularVelocity.ToString("F3"), false);
                        IsDirty = false;
                    }
                }
                yield return new WaitForSeconds(.05f);
            }
       
    }

    // Start is called before the first frame update
    void Start()
    {
        MyRig = GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update()
    {
        if (IsClient)
        {
            if (LastVelocity.magnitude < .05f)
            {
                OffsetVelocity = Vector3.zero;
            }
            if (UseOffsetVelocity)
            {
                MyRig.velocity = LastVelocity + OffsetVelocity; 
            }
            else
            {
                MyRig.velocity = LastVelocity; 

            }
            MyRig.angularVelocity = LastAngular;
        }
        
    }
}
