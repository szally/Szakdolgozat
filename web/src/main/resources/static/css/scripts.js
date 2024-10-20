function changeFont(element){
    element.style.fontSize = "30px";
    for(var i=0; i < element.children.length; i++){
        changeFont(element.children[i]);
    }
}
changeFont(document.getElementsByTagName("body")[0]);

function resetFont(element){
    element.style.fontSize = "16px";
    for(var i=0; i < element.children.length; i++){
        changeFont(element.children[i]);
    }
}
changeFont(document.getElementsByTagName("body")[0]);