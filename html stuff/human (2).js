class Skeleton
{
    constructor(){
        //Body 
        this.bodyx=50;
        this.bodyy=100;

        //Legs
        this.rightlegx=150;
        this.rightlegy=200;

        this.leftlegx=50;
        this.leftlegy=200;

        //Arms 
        //
        this.rightarmx=200;
        this.rightarmy=125;
       
        //Left arm position is at x=25,y=125;
        //Left arm position x=25+ Size in x direction is 25
        this.leftarmx=25;
        this.leftarmy=125;

        //Face
        this.facex=125;
        this.facey=50;
        //2 Eyes
        this.righteyex=145;
        this.righteyey=25;

        this.lefteyex=105;
        this.lefteyey=25;

        // Mouth
        this.mouthx=125;
        this.mouthy=60;
        this.smilestate=false;
    }
}

class Body extends Skeleton
{
    constructor()
    {
        super();
        this.speed = 50;
    }
    Circle(x,y,radius,startangle,endangle,color,style,countercw){
        ctx.beginPath();
            ctx.arc(x,y,radius,startangle,endangle,countercw);
            if(style=='fill'){
                ctx.fillStyle=color;
                ctx.fill();
            }
            else if(style=='stroke'){
                ctx.strokeStyle=color;
                ctx.stroke();
            }

    }

    Rectangle(x,y,sizex,sizey,color){
        ctx.fillStyle= color;
        ctx.fillRect(x,y,sizex,sizey);
    }
    RandomColor(opacity){
        var randomcolor;
        if(opacity=='yes'){
            var r= Math.round(Math.random()*255);
            var g= Math.round(Math.random()*255);
            var b= Math.round(Math.random()*255);
            var a= Math.random();
            return randomcolor = 'rgba('+r+','+g+','+b+','+a+')';
        }
        else{
            var r= Math.round(Math.random()*255);
            var g= Math.round(Math.random()*255);
            var b= Math.round(Math.random()*255);
            return randomcolor = 'rgb('+r+','+g+','+b+')';
        }
    }
    render(){
        //Body
        this.Rectangle(this.bodyx,this.bodyy,150,100,this.RandomColor());

        //Legs
        this.Rectangle(this.leftlegx,this.leftlegy,50,100,this.RandomColor());
        this.Rectangle(this.rightlegx,this.rightlegy,50,100,this.RandomColor());

        //Arms
        this.Rectangle(this.leftarmx,this.leftarmy,25,100,this.RandomColor());
        this.Rectangle(this.rightarmx,this.rightarmy,25,100,this.RandomColor());
    
        //Face
        // Circle(x,y,radius,startangle,endangle,color,style,countercw)
        this.Circle(this.facex,this.facey,50,0,2*Math.PI,this.RandomColor(),'stroke');

        //Eyes
        this.Circle(this.lefteyex,this.lefteyey,10,0,2*Math.PI,this.RandomColor(),'fill');
        this.Circle(this.righteyex,this.righteyey,10,0,2*Math.PI,this.RandomColor(),'fill');

        //Mouth
        if(this.smilestate==true){
            this.Circle(this.mouthx,this.mouthy,25,0,Math.PI,this.RandomColor(),'fill');
        }else{
            this.Circle(this.mouthx,this.mouthy,25,0,Math.PI,this.RandomColor(),'fill','countercw');
        }
        


    }
    update(){
        this.render();
        if(left){   
        this.bodyx-=this.speed;
        this.leftlegx-=this.speed;
        this.rightlegx-=this.speed;
        this.rightarmx-=this.speed;
        this.leftarmx-=this.speed;

        this.facex-=this.speed;
        this.lefteyex-=this.speed;
        this.righteyex-=this.speed;
        this.mouthx-=this.speed;
        left=false;
        }

        if(right){   
        this.bodyx+=this.speed;
        this.leftlegx+=this.speed;
        this.rightlegx+=this.speed;
        this.rightarmx+=this.speed;
        this.leftarmx+=this.speed;

        this.facex+=this.speed;
        this.lefteyex+=this.speed;
        this.righteyex+=this.speed;
        this.mouthx+=this.speed;
        right=false;
        }
        if(down){   
            this.bodyy+=this.speed;
            this.leftlegy+=this.speed;
            this.rightlegy+=this.speed;
            this.rightarmy+=this.speed;
            this.leftarmy+=this.speed;
    
            this.facey+=this.speed;
            this.lefteyey+=this.speed;
            this.righteyey+=this.speed;
            this.mouthy+=this.speed;
            down=false;
            }

            if(up){   
                this.bodyy-=this.speed;
                this.leftlegy-=this.speed;
                this.rightlegy-=this.speed;
                this.rightarmy-=this.speed;
                this.leftarmy-=this.speed;
        
                this.facey-=this.speed;
                this.lefteyey-=this.speed;
                this.righteyey-=this.speed;
                this.mouthy-=this.speed;
                up=false;
                }
                if(smile==true){
                    this.smilestate = true;
                }
                if(smile==false){
                    this.smilestate = false;
                }
    }
}