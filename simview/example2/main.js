import stomp from "stompjs"
import Matter from "matter-js";

const myCanvas = document.getElementById("canvas");
const ctx = myCanvas.getContext("2d");

ctx.fillStyle = "blue";
ctx.strokeStyle = "blue";

const R = 5;

const SCALE = 1;

var mouse_x;
var mouse_y;

myCanvas.addEventListener("mousemove",(e)=>{
    mouse_x = e.clientX;
    mouse_y = e.clientY;
})


fetch("http://localhost:8080/balls").then(data => data.text())
.then(id=>{

var socket = new SockJS("http://localhost:8080/simapi")
const client = stomp.over(socket);

client.debug = null;

/**
 * @type {Array<{x:number,y:number}>}
 */
var positions = [];

client.connect({},()=>{
    console.log("STOMP Connected!");
    
    //client.subscribe("/broker/test",(message)=>{console.log(message)});
    client.subscribe("/broker/ballpositions",
    /**
    * @param {{body:string}} message
    */
    (message)=>{
        positions = [];
        for(let i of message.body.split(",")){
            let parts = i.split("x");
            positions.push({
                x : +parts[0],
                y : +parts[1]
            })
        }
    });

    //client.send("/api/test");
    client.send("/api/getballs");
})


function animation() {
    ctx.clearRect(0,0,1000,1000);
    for(let i=0;i<positions.length;i++){
        if(i === 0 ){
            ctx.fillStyle = "red";
            ctx.strokeStyle = "red";
        }else {
            ctx.fillStyle = "blue";
            ctx.strokeStyle = "blue";
        }
        ctx.beginPath();
        ctx.arc(positions[i].x*SCALE,positions[i].y*SCALE,R,0,2*Math.PI);
        ctx.stroke();
    }
    client.send("/api/getballs");

    client.send("/api/mousepos",{},mouse_x+"-"+mouse_y+"-"+id);
}


setInterval(() => {
    if(client.connected){
        animation();
    }
}, 10);


})



function canvas2() {
    const myCanvas = document.getElementById("canvas2");
    const ctx = myCanvas.getContext("2d");

    const SCALE = 0.1

    var Engine = Matter.Engine,
        Runner = Matter.Runner,
        Bodies = Matter.Bodies,
        Composite = Matter.Composite


    var engine = Engine.create();

    engine.positionIterations = 6;
    engine.velocityIterations = 4;

    var ground = Bodies.rectangle(500/SCALE,1000/SCALE,1000/SCALE,100/SCALE, { isStatic: true });
    var wall1 = Bodies.rectangle(0,500/SCALE,100/SCALE,1000/SCALE, { isStatic: true });
    var wall2 = Bodies.rectangle(1000/SCALE,500/SCALE,100/SCALE,1000/SCALE, { isStatic: true });

    Composite.add(engine.world,ground,wall1,wall2);

    const count = 2000;
    /**
     * @type {Array<Matter.Body>}
     */
    var bodies = [];
    for(let i=0;i<count;i++){
        let body = Bodies.circle(500/SCALE+(Math.random()*(1000/SCALE) % (R/SCALE)),(500/SCALE)-i*(R/SCALE),R/SCALE);
        bodies.push(body);
        Composite.add(engine.world,body);
    }

    let runner = Runner.create();

    function animation() {
        ctx.clearRect(0,0,1000,1000);
        for(let i=0;i<bodies.length;i++){
            if(i === 0 ){
                ctx.fillStyle = "red";
                ctx.strokeStyle = "red";
            }else {
                ctx.fillStyle = "blue";
                ctx.strokeStyle = "blue";
            }
            ctx.beginPath();
            ctx.arc(bodies[i].position.x*SCALE,bodies[i].position.y*SCALE,R,0,2*Math.PI);
            ctx.stroke();
        }
        Matter.Body.setVelocity(bodies[0],Matter.Vector.create((mouse_x/SCALE-bodies[0].position.x),(mouse_y/SCALE-bodies[0].position.y)));
        Runner.tick(runner,engine,1.0/600.0)
    }

    setInterval(() => {
        animation();
    }, 50);

    }
canvas2();