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

        //face
        this.facex=125;
        this.facey=50;

        //eyes
        this.righteyex=150; 
        this.righteyey=25;
        this.lefteyex=100;
        this.lefteyey=25;

        //mouth
        this.mouthx=125;
        this.mouthy=75;

        //grass
        this.grassx=0;
        this.grassy=300;
    }
}

class Body extends Skeleton
{
    constructor()
    {
        super();
        this.speed = 50;
    }
    circle(x,y,radius,startAngle,endAngle,color,style,countercw){
        ctx.beginPath();
        ctx.arc(x,y,radius, startAngle,endAngle,countercw);
        if(style == 'fill'){
            ctx.fillStyle = color;
            ctx.fill();
        }
        else if(style == 'stroke'){
            ctx.strokeStyle=color;
            ctx.stroke();
        }
    }

    Rectangle(x,y,sizex,sizey,color){
        ctx.fillStyle = color;
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

        //face
        this.circle(this.facex, this.facey, 50,0,2*Math.PI,this.RandomColor(),'stroke');

        //eyes 
        this.circle(this.righteyex,this.righteyey, 10, 0, 2*Math.PI, this.RandomColor(), 'fill');
        this.circle(this.lefteyex,this.lefteyey, 10, 0, 2*Math.PI, this.RandomColor(), 'fill');

        //mouth
        this.circle(this.mouthx, this.mouthy, 20, 0, Math.PI, this.RandomColor(), 'fill');

        //grass
        this.Rectangle(this.grassx, this.grassy, 700,100,'green');

    }
    update(){
        this.render();
        this.bodyx+=this.speed;
        this.leftlegx+=this.speed;
        this.rightlegx+=this.speed;
        this.rightarmx+=this.speed;
        this.leftarmx+=this.speed;
        this.righteyex+=this.speed;
        this.lefteyex+=this.speed;
        this.facex+=this.speed;
        this.mouthx+=this.speed;
    }
}