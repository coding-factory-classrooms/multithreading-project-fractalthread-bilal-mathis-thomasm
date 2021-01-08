const fractalDiv = document.getElementById("fractal-img");
const step = 5;
const allPos = {x:0,y:0,z:3}

function move(axe, increment){
    increment *= (Math.log(2) * allPos.z) / step;
    allPos[axe] += increment;
    updatePos();
}

function updatePos() {
    fractalDiv.style["background-image"] = "url('/images/"+allPos.x+"/"+allPos.y+"/"+allPos.z+"')";
}