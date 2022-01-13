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
    }
}

class Body extends Skeleton
{
    constructor()
    {
        super();
        this.speed = 50;
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

    }
    update(){
        this.render();
        this.bodyx+=this.speed;
        this.leftlegx+=this.speed;
        this.rightlegx+=this.speed;
    }
}