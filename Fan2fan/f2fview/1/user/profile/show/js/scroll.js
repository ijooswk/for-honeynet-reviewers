// custom scroll application developed for three.oh 3/2001.
// i'd say copyright, but i know it wouldn't stop you... so have at it...
// thanks to brandon (www.jesterworld.net) and grant (no site cuz he's too pimp for it) for the help w/ the visuals.
// > youngpup > www.youngpup.net
// optimized for the latest version of {3.0} by bob

ThreeOhScroll.mo5 = navigator.userAgent.indexOf("Gecko") != -1
ThreeOhScroll.ie5 = navigator.appName == "Microsoft Internet Explorer" && document.getElementById

ThreeOhScroll.aniLen = 250

function ThreeOhScroll(id)
{
	if (ThreeOhScroll.mo5 || ThreeOhScroll.ie5) {
		this.id = id
		this.getMembers(document.getElementById(id))

		this.clipH		= parseInt(this.container.style.height)
		this.PTags		= this.content.getElementsByTagName("P")
		var lastP		= this.PTags[this.PTags.length-1];
		var lastPTop	= lastP.offsetTop - (ThreeOhScroll.mo5 ? 108 : 0);
		this.docH		= lastPTop + Math.max(lastP.offsetHeight, this.clipH)
		this.scrollH	= this.docH - this.clipH
		this.markersMax	= parseInt(this.markers.style.height) - 7
		this.thumbMax	= parseInt(this.thumbContainer.style.height) - this.thumbImg.height
		this.arrowMax	= parseInt(this.arrowContainer.style.height) - this.arrowImg.height

		this.gRef = "ThreeOhScroll_"+id
		eval(this.gRef+"=this")
		this.thumb.obj	= this
		this.thumb.onmousedown = this.startDrag
		this.thumb.onmouseover = Function(this.gRef + ".toggleThumb(1)")
		this.thumb.onmouseout  = Function(this.gRef + ".toggleThumb(0)")
		this.thumb.onmousup	   = Function(this.gRef + ".toggleThumb(0)")
		this.thumb.ondragstart = function() { return false }
		this.initMarkers()
	}
}

ThreeOhScroll.prototype.initMarkers = function() {
	var shtml = "", sTitle, iTop
	for (var i = 0; i < this.PTags.length; i++) {
		sTitle	= this.PTags[i].getAttribute("description")
		pTop	= this.PTags[i].offsetTop - (ThreeOhScroll.mo5 ? 108 : 0)
		iTop	= Math.round(pTop * this.markersMax / this.scrollH)
		if (sTitle && sTitle != "" && sTitle != null) {
			shtml  += "<div id='" + this.id + "_marker_" + i + "' "
			shtml  += "style='position:absolute; left:2px; top:" + (iTop + 2) + "px; "
			shtml  += "width:5px; height:3px; clip:rect(0 5 3 0); background-color:#CCCCCC; z-index:3;'></div>"
			shtml  += "<div style='position:absolute; left:0px; top:" + iTop + "px; "
			shtml  += "cursor:pointer; cursor:hand; width:9px; height:7px; clip:rect(0 9 7 0); z-index:4;' " 
			shtml  += "onmousedown='" + this.gRef + ".scrollTo(" + pTop + ")' "
			shtml  += "onmouseover='" + this.gRef + ".toggleMarker(this, " + i + ", 1)' "
			shtml  += "onmouseout='" + this.gRef + ".toggleMarker(this, " + i + ", 0)' "
			shtml  += "description='" + sTitle.replace(/'/g, "|pos|") + "'>"
			shtml  += "<img src='imgs/ui/1.gif' width='9' height='7'></div>"
		}
	}
	this.markers.innerHTML += shtml
}
// this is so that we only loop through the descendant tree looking for elements one time - it loads faster!
ThreeOhScroll.prototype.getMembers = function(a) {
	this.container=document.getElementById('ypContainer');
	this.content=document.getElementById('ypContent');
	this.markers=document.getElementById('ypMarkers');
	this.thumb=document.getElementById('ypThumb');
	this.arrow=document.getElementById('ypArrow');
	this.thumbImg=document.getElementById('ypThumbImg');
	this.arrowImg=document.getElementById('ypArrowImg');
	this.thumbContainer=document.getElementById('ypThumbContainer');
	this.arrowContainer=document.getElementById('ypArrowContainer');
	this.description=document.getElementById('ypDescription');
	this.descArrow=document.getElementById('ypDescArrow');
}

ThreeOhScroll.prototype.startDrag = function(e) {
	if (!e) e = window.event
	var ey = e.pageY ? e.pageY : e.y
	this.dragLastY = ey
	this.dragStartOffset = ey - parseInt(this.style.top)
	ThreeOhScroll.current = this.obj
	document.onmousemove = this.obj.doDrag
	document.onmouseup = this.obj.stopDrag
	if (this.obj.aniTimer) window.clearInterval(this.obj.aniTimer)
	return false;
}

ThreeOhScroll.prototype.doDrag = function(e) {
	if (!e) e = window.event
	var obj = ThreeOhScroll.current
	var ey = (e.pageY ? e.pageY : e.y)
	var dy = ey - obj.thumb.dragLastY
	var ny = parseInt(obj.thumb.style.top) + dy
	if (ny >= obj.thumbMax) obj.thumb.dragLastY = obj.thumbMax + obj.thumb.dragStartOffset
	else if (ny < 0) obj.thumb.dragLastY = obj.thumb.dragStartOffset
	else obj.thumb.dragLastY = ey
	ny = Math.min(Math.max(ny, 0), obj.thumbMax)
	obj.jumpTo(ny * obj.scrollH / obj.thumbMax)
	return false;
}

ThreeOhScroll.prototype.stopDrag = function() {
	this.onmousemove = null
	this.onmouseup   = null
	ThreeOhScroll.current.toggleThumb(0)
}

ThreeOhScroll.prototype.scrollTo = function(ny) {
	this.endArrow = Math.round(ny * this.markersMax / this.scrollH)
	this.startTime = (new Date()).getTime()
	this.startPos = parseInt(this.content.style.top) * -1
	this.endPos = ny
	this.dist = this.endPos - this.startPos
	this.accel = this.dist / ThreeOhScroll.aniLen / ThreeOhScroll.aniLen
	if (this.aniTimer) this.aniTimer = window.clearInterval(this.aniTimer)
	this.aniTimer = window.setInterval(this.gRef + ".scroll()", 10)
}

ThreeOhScroll.prototype.scroll = function() {
	var now = (new Date()).getTime()
	var elapsed = now - this.startTime
	if (elapsed > ThreeOhScroll.aniLen) this.endScroll()
	else {
		var t = ThreeOhScroll.aniLen - elapsed
		var ny = this.endPos - t * t * this.accel
		this.jumpTo(ny)
	}
}

ThreeOhScroll.prototype.endScroll = function() {
	this.jumpTo(this.endPos)
	this.arrow.style.top = this.endArrow
	this.aniTimer = window.clearInterval(this.aniTimer)
}

ThreeOhScroll.prototype.jumpTo = function(ny) {
	this.thumb.style.top	= Math.round(ny * this.thumbMax / this.scrollH)
	this.arrow.style.top	= Math.round(ny * this.arrowMax / this.scrollH)
	this.content.style.top	= -ny
}

ThreeOhScroll.prototype.toggleMarker = function(oTrigger, markerNum, bOn) {
	if (bOn) {
		document.getElementById(this.id + "_marker_" + markerNum).style.backgroundColor = "#444444"
		if (this.curMarker) this.toggleMarker(this.curMarker, 0)
		this.curMarker = markerNum
		this.descArrow.style.top = parseInt(oTrigger.style.top) + 2 + "px"
		this.description.style.left = "-400px"
		this.description.style.top = "-400px"
		this.description.innerHTML = oTrigger.getAttribute("description").replace(/\|pos\|/g, "'")
		var w = this.description.offsetWidth
		this.description.style.left = 259 - w + "px"
		this.description.style.top = parseInt(oTrigger.style.top) - 1 + "px"
		this.description.style.visibility = "visible"
		this.descArrow.style.visibility = "visible"
		this.container.style.left = "0px"
	} else {
		document.getElementById(this.id + "_marker_" + markerNum).style.backgroundColor = "#cccccc"
		this.curMarker = 0
		this.description.style.visibility = "hidden"
		this.descArrow.style.visibility = "hidden"
	}
}

ThreeOhScroll.prototype.toggleThumb = function(bOn) {
	//this.arrow.style.backgroundColor = this.thumb.style.backgroundColor = bOn ? "#7CDAFE" : "#5EBBE7"
}

function ypGetChildNodes(objParent) {
	return (objParent.childNodes ? objParent.childNodes : objParent.children)
}
