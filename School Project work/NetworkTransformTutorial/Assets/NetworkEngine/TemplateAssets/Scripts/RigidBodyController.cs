using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using NETWORK_ENGINE;
[RequireComponent(typeof(NetworkRigidBody))]

public class RigidBodyController : NetworkComponent
{
    public Rigidbody MyRig;
    public float speed = 3;
    public Vector3 LastMove;
    //public Vector3 LastRotation;
    public Animator myAnimator;
    public int HP = 10;

    public bool CanBomb = true;
    public float ReloadTimer = 1;
    public Vector3 MousePointer;

    public static Vector3 VectorFromString(string value)
    {
        char[] temp = { '(', ')' };
        string[] args = value.Trim(temp).Split(',');
        return new Vector3(float.Parse(args[0].Trim()), float.Parse(args[1].Trim()), float.Parse(args[2].Trim()));
    }

    public IEnumerator Reload()
    {
        yield return new WaitForSeconds(ReloadTimer);
        CanBomb = true;
    }
    public override void HandleMessage(string flag, string value)
    {
        if(flag == "MOVE" && IsServer)
        { 
            string[] args = value.Split(',');
            //float v = int.Parse(value);
            float v = float.Parse(args[1]);
            float h = float.Parse(args[0]);
            LastMove = new Vector3(h,0,v)*speed;
        }
        if(flag == "BOMB" && IsServer && CanBomb)
        {
            CanBomb = false;
            GameObject myBullet = MyCore.NetCreateObject(2, Owner, this.transform.position + (this.transform.forward * 1.5f), Quaternion.identity);
            myBullet.GetComponent<Rigidbody>().velocity = this.transform.forward * 5;

            StartCoroutine(Reload());

        }
        if (flag == "HP")
        {
            HP = int.Parse(value);
        }
        if (flag == "MOUSE")
        {
            if (IsServer)
            {
                MyRig.transform.forward = VectorFromString(value);
                SendUpdate("MOUSE", value);
            }
            if (IsClient)
            {
                //MyRig.transform.forward = VectorFromString(value);
            }
        }
        /*if(flag == "ROTATE" && IsServer)
        {
            float h = int.Parse(value);

            LastRotation = new Vector3(0, h, 0) * 2 ;
        }*/
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
        if (IsServer)
        {
            MyRig.position += new Vector3(0, 5, 0);
            CanBomb = true;
        }
        if (IsLocalPlayer)
        {
            AxisEventCallers.current.OnDirectionChanged += OnMove;
            AxisEventCallers.InputEvents["Jump"].OnAxisKeyDown += OnJump;
        }
        /*while (IsConnected)
        {
            if (IsLocalPlayer)
            {
                float h = Input.GetAxis("Horizontal");
                float v = Input.GetAxis("Vertical");
                if ((LastMove - new Vector3(h, 0, v)).magnitude > .1f)
                {
                    LastMove = new Vector3(h, 0, v);
                    SendCommand("MOVE", h + "," + v);
                }
                if(Input.GetAxis("Jump")>0 && CanBomb)
                {
                    SendCommand("BOMB", "1");
                    CanBomb = false;
                    StartCoroutine(Reload());
                }*/
                //if((LastRotation - new Vector3(0,h,0)).magnitude > .1f){
                    //LastRotation = new Vector3(0,h,0);
                   // SendCommand("ROTATE", h.ToString());
                //}
               // if((LastMove -v) > .1f)
                //{
                    //LastMove = v;
                    //SendCommand("MOVE", v.ToString());
                //}

            //}
            if (IsServer)
            {

            }
            yield return new WaitForSeconds(.05f);
        //}

    }

    // Start is called before the first frame update
    void Start()
    {
        MyRig = GetComponent<Rigidbody>();
        myAnimator = this.GetComponent<Animator>();
    }

    // Update is called once per frame
    void Update()
    {
        if (IsServer)
        {

                MyRig.velocity = LastMove + new Vector3 (0,MyRig.velocity.y, 0);


            //MyRig.angularVelocity = LastRotation;
        }
        if (IsLocalPlayer)
        {
            Plane plane = new Plane(Vector3.up, 0);

            float distance;
            Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
            if (plane.Raycast(ray, out distance))
            {
                MousePointer = ray.GetPoint(distance);
                SendCommand("MOUSE", MousePointer.ToString());
            }
        }
        if (IsClient)
        { 
            if (MyRig.velocity.magnitude > .1f)
            {
                if(myAnimator == null)
                {
                    this.transform.forward = MyRig.velocity.normalized;
                }
                else
                {
                    if (Mathf.Abs(MyRig.velocity.x) > Mathf.Abs(MyRig.velocity.z))
                    {
                        if (MyRig.velocity.x > 0)
                        {
                            myAnimator.SetInteger("dir", 0);
                            myAnimator.SetInteger("idle", 0);
                            myAnimator.SetInteger("running", 1);
                        }
                        else if (MyRig.velocity.x == 0)
                        {
                            myAnimator.SetInteger("idle", 1);
                            myAnimator.SetInteger("running", 0);
                        }
                        else
                        {
                            myAnimator.SetInteger("dir", 2);
                            myAnimator.SetInteger("idle", 0);
                            myAnimator.SetInteger("running", 1);

                        }
                    }
                    else
                    {
                        if (MyRig.velocity.z > 0)
                        {
                            myAnimator.SetInteger("dir", 1);
                            myAnimator.SetInteger("idle", 0);
                            myAnimator.SetInteger("running", 1);
                        }
                        else if (MyRig.velocity.z == 0)
                        {
                            myAnimator.SetInteger("idle", 1);
                            myAnimator.SetInteger("running", 0);
                        }
                        else
                        {
                            myAnimator.SetInteger("dir", 3);
                            myAnimator.SetInteger("idle", 0);
                            myAnimator.SetInteger("running", 1);

                        }
                    }
                }
            }
        }
    }

    private void OnDestroy()
    {
        AxisEventCallers.current.OnDirectionChanged -= OnMove;
        AxisEventCallers.InputEvents["Jump"].OnAxisKeyDown += OnJump;
    }

    private void OnTriggerEnter(Collider other)
    {
        if (other.gameObject.tag == "BOMB")
        {
            if (IsClient)
            {

            }
            if (IsServer)
            {
                MyCore.NetDestroyObject(other.gameObject.GetComponent<NetworkID>().NetId);
                HP--;
                if (HP < 1)
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

    public void OnJump()
    {
        if (CanBomb)
        {
            myAnimator.SetInteger("attack", 1);
            SendCommand("BOMB", "1");
            CanBomb = false;
            StartCoroutine(Reload());
        }
    }
    public void OnMove()
    {
        float h = Input.GetAxis("Horizontal");
        float v = Input.GetAxis("Vertical");
        SendCommand("MOVE", h + "," + v);
    }
}
