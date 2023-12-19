

function createMandelbrot(canvas,api) {
    const myCanvas = document.getElementById(canvas);
    const ctx = myCanvas.getContext("2d");

    let x = -0.5;
    let y = 0;
    let zoom = 0.002;
    let max_iter = 1000;
    let limit = 40;

    let time = 0;
    let time_interval = setInterval(()=>{time++},1);

    fetch(`http://localhost:8080/${api}?x_s=${x}&y_s=${y}&zoom_s=${zoom}&maxiter_s=${max_iter}`)
    .then(pre => pre.json())
    .then(data => {
      clearInterval(time_interval);
      document.getElementById("loading"+api).textContent = "Loading Time : "+time;
      let k = 0;
      for(let i=0;i<1000;i++){
        for(let j=0;j<1000;j++){
          if(data[k] > limit){
            ctx.fillStyle = `rgb(${data[k]},0,0)`;
          }else {
            ctx.fillStyle = "white";
          }
          ctx.fillRect(i,j,1,1);
          k++;
        }
      }
    })
}

createMandelbrot("canvasJava","mandelbrotJava")
createMandelbrot("canvasCpp","mandelbrot")