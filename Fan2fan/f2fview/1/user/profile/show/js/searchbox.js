function drop_mouseover(pos){
    try{window.clearTimeout(timer);}catch(e){}
}
function drop_mouseout(pos){
    var posSel=document.getElementById(pos+"Sel").style.display;
    if(posSel=="block"){
    timer = setTimeout("drop_hide('"+pos+"')", 1000);
    }
}
function drop_hide(pos){
    document.getElementById(pos+"Sel").style.display="none";
    }
function search_show(pos,searchType,href){
    document.getElementById(pos+"SearchType").value=searchType;
    document.getElementById(pos+"Sel").style.display="none";
    document.getElementById(pos+"Slected").innerHTML=href.innerHTML;
    document.getElementById(pos+'q').focus();
try{window.clearTimeout(timer);}catch(e){}
return false;
}
