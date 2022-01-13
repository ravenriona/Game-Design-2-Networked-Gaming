class Vehicle{ 
    constructor(){
        this.imagebmw = "bmw.png";
        this.imageford= "ford.png";
        this.imagemerc= "m.png";
        this.logo = new Image();
        this.lowSpeed = 20;
        this.topSpeed = 100;
        this.defaultSpeed = 50;
        this.speed = 0;
    }
}

class Car extends Vehicle{
    constructor(name){
        super();
        this.X=50;
        this.Y=100;
        this.name = name;
        if(this.name == "ford"){
            this.logo.src = "ford.png";
            this.X=50;
            this.Y=100;
        } else if(this.name== "bmw"){
            this.logo.src = "bmw.png";
            this.X=50;
            this.Y=200;
        } else if(this.name == "mercades"){
            this.logo.src = "m.png";
            this.X=50;
            this.Y=300;
        }
        this.speed = this.RandomSpeed();
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
    RandomSpeed(){
        var speed = Math.round(Math.random()*100)+25;
        if(speed > 100){
            speed = 100;
        }
        return speed;
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
        console.log("hello2");
        //Cars
        // Circle(x,y,radius,startangle,endangle,color,style,countercw)
        this.Circle(this.X,this.Y,30,0,2*Math.PI,this.RandomColor(),'fill');
        ctx.drawImage(this.logo, this.X, this.Y, 30,30);

        this.Rectangle(600, 0, 60, 700, this.RandomColor()); 



    }
    update(){
        console.log("hello");
        this.render();
        this.X+=this.speed;
    }
}