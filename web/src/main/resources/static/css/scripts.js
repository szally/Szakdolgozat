function changeFont(element, newFontSize){
    element.style.fontSize = newFontSize;
    for(var i=0; i < element.children.length; i++){
        changeFont(element.children[i],newFontSize);
    }
    sessionStorage.setItem("fontSize", newFontSize);

}

function getSavedItem(){
    return sessionStorage.getItem("fontSize");
}

function collapsible(){
let coll = document.getElementsByClassName("collapsible");

for (let i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    let content = this.nextElementSibling;
    if (content.style.maxHeight) {
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    }
  });
}
}