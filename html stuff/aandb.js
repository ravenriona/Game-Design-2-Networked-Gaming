class AandB{
    constructor(a,b){
        this.a = a;
        this.b = b;


    }
    add() {
        return document.getElementById("number").innerHTML=this.a + this.b;
    }
    sub(){
        return document.getElementById("number").innerHTML=this.a - this.b;
    }
    mul(){
        return document.getElementById("number").innerHTML=this.a * this.b;
    }
    div(){
        return document.getElementById("number").innerHTML=this.a / this.b;
    }

}