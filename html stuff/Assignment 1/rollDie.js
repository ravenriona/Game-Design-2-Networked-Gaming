class dice{
    constructor(){
        //dice dimensions
        this.die1 = 0;
        this.die2 = 0;
        this.winVar = 0;
        this.playVar= 0;
    }
    setdie1(value){
        this.die1 = value;
    }
    setdie2(value){
        this.die2 = value;
    }
    RollDice(result1, result2){ 
        this.playVar++;
        if (result1==result2){
            this.winVar = this.winVar + 1;
            console.log(this.winVar);
            console.log(result1);
            console.log(result2);
        }
             console.log("a");
             this.setdie1(result1);
             switch(result1){
             case 1: document.getElementById("dice1").src = "side1.png";
             break;
             case 2: document.getElementById("dice1").src = "side2.png";
             break;
             case 3: document.getElementById("dice1").src = "side3.png";
             break;
             case 4: document.getElementById("dice1").src = "side4.png";
             break;
             case 5: document.getElementById("dice1").src = "side5.png";
             break;
             case 6: document.getElementById("dice1").src = "side6.png";
             break;
            }
        
     

             this.setdie2(result2);
             switch(result2){
             case 1: document.getElementById("dice2").src = "side1.png";
             break;
             case 2: document.getElementById("dice2").src = "side2.png";
             break;
             case 3: document.getElementById("dice2").src = "side3.png";
             break;
             case 4: document.getElementById("dice2").src = "side4.png";
             break;
             case 5: document.getElementById("dice2").src = "side5.png";
             break;
             case 6: document.getElementById("dice2").src = "side6.png";
             break;
            }   
    return;
    }
    RandomNumber(){
        var result= Math.floor(Math.random()*6+1);
        return result;
    }




}