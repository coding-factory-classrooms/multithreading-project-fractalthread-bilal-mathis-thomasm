const fractalDiv = document.getElementById("fractal-img");
const step = 5;
const allPos = {x:0,y:0,z:3}

updateImage()

function move(axe, increment){
    increment *= (Math.log(2) * allPos.z) / step;
    allPos[axe] += increment;
    updateImage();
}

function updateImage() {
    let width = window.innerWidth, height = window.innerHeight;
    let type = document.getElementById("slct").value;
    fractalDiv.style["background-image"] = "url('/images/"+type+"/"+width+"/"+height+"/"+allPos.x+"/"+allPos.y+"/"+allPos.z+"')";
}