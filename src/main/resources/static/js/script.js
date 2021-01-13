const fractalDiv = document.getElementById("fractal-img");
const step = 5;
const allPos = {x:0,y:0,z:3}
let isEasterOn = false
let audio = new Audio('https://timotei.co/music/circus.mp3');
updateImage()

function move(axe, increment){
    if(document.getElementById("slct").value == "julia" && axe == 'z'){
        increment = -(increment)
    }
    increment *= (Math.log(2) * allPos.z) / step;
    allPos[axe] += increment;

    if(isEasterOn){
        audio.pause();
        audio.currentTime = 0;
        updateImage();
    }



    if(allPos.x >= 1 && allPos.x < 2){
        fractalDiv.style["background-image"] = "url('https://www.zupimages.net/up/21/02/6w8o.jpg')";
        audio.play();
        isEasterOn = true;
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

