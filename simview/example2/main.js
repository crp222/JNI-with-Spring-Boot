import stomp from "stompjs"

const myCanvas = document.getElementById("canvas");
const ctx = myCanvas.getContext("2d");

ctx.fillStyle = "blue";
ctx.strokeStyle = "blue";

const R = 10;

var mouse_x;
var mouse_y;

myCanvas.addEventListener("mousemove",(e)=>{
    mouse_x = e.clientX;
    mouse_y = e.clientY;
})

fetch("http://localhost:8080/balls");

var socket = new SockJS("http://localhost:8080/simapi")
const client = stomp.over(socket);

/**
 * @type {Array<{x:number,y:number}>}
 */
var positions = [];

client.connect({},()=>{
    console.log("STOMP Connected!");
    
    //client.subscribe("/broker/test",(message)=>{console.log(message)});

    client.subscribe("/broker/ballpositions",(message)=>{
        positions = JSON.parse(message.body);
    });

    //client.send("/api/test");
    client.send("/api/getballs");
})


function animation() {
    if(positions.length === 0){
        window.requestAnimationFrame(animation);
        return;
    }
    console.log(positions)
    
    ctx.clearRect(0,0,1000,1000);
    for(let i=0;i<positions.length;i++){
        ctx.beginPath();
        ctx.arc(positions[i].x,positions[i].y,R,0,2*Math.PI);
        ctx.stroke();
    }
    client.send("/api/getballs");

    client.send("/api/mousepos",{},mouse_x+"-"+mouse_y);
}

setInterval(() => {
    animation();
}, 10);