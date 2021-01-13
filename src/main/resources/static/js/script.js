const fractalDiv = document.getElementById("fractal-img");
const step = 5;
const allPos = {x:0,y:0,z:3}

updateImage()

function move(axe, increment){
    increment *= (Math.log(2) * allPos.z) / step;
    allPos[axe] += increment;


    if(allPos.x >= 1 && allPos.x < 2){
    window.alert("Merci à l'equipe 2 !!!!");
    }else{
     updateImage();
    }



}

document.onkeydown = checkKey;

function checkKey(e) {

    e = e || window.event;

    if (e.keyCode == '38') {
        move('y', -1)
    }
    else if (e.keyCode == '40') {
        move('y', 1)
    }
    else if (e.keyCode == '37') {
        move('x', -1)
    }
    else if (e.keyCode == '39') {
        move('x', 1)
    }

}
window.addEventListener("wheel", event => zoomScroll(event.deltaY));
function zoomScroll(z) {
    if(z > 0){
        move('z', 1);
    }else{
        move('z', -1);
    }
}

function updateImage() {
    let width = window.innerWidth, height = window.innerHeight;
    let type = document.getElementById("slct").value;
    fractalDiv.style["background-image"] = "url('/images/"+type+"/"+width+"/"+height+"/"+allPos.x+"/"+allPos.y+"/"+allPos.z+"')";
}

