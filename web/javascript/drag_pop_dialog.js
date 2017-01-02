/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/* global winHeight, winWidth */

function setDragPopDialog(dialog_id) {
    findDimensions();
    var oDiv2 = document.getElementById(dialog_id);
    oDiv2.style.bottom = (winHeight - 400) / 2 + "px";
    oDiv2.style.right = (winWidth - 500) / 2 + "px";
    var zhezhao = document.getElementById("zhezhao");
    var h2 = document.getElementById("dialog_title");
    var mouseStart = {};
    var divStart = {};
    //var rightStart = {};
    //var bottomStart = {};
    h2.onmousedown = function (ev) {
        var oEvent = ev || event;
        mouseStart.x = oEvent.clientX;
        mouseStart.y = oEvent.clientY;
        divStart.x = oDiv2.offsetLeft;
        divStart.y = oDiv2.offsetTop;

        if (h2.setCapture) {
            h2.onmousemove = doDrag3;
            h2.onmouseup = stopDrag3;
            h2.setCapture();
        }
        else {
            document.addEventListener("mousemove", doDrag3, true);
            document.addEventListener("mouseup", stopDrag3, true);
        }

        zhezhao.style.display = 'block';
        h2.style.cursor = "move";
    };
    function doDrag3(ev) {
        var oEvent = ev || event;
        var l = oEvent.clientX - mouseStart.x + divStart.x;
        var t = oEvent.clientY - mouseStart.y + divStart.y;
        if (l < 0) {
            l = 0;
        }
        else if (l > document.documentElement.clientWidth - oDiv2.offsetWidth) {
            l = document.documentElement.clientWidth - oDiv2.offsetWidth;
        }
        if (t < 0) {
            t = 0;
        }
        else if (t > document.documentElement.clientHeight - oDiv2.offsetHeight) {
            t = document.documentElement.clientHeight - oDiv2.offsetHeight;
        }
        oDiv2.style.left = l + "px";
        oDiv2.style.top = t + "px";
        h2.style.cursor = "move";
    }
    function stopDrag3() {
        if (h2.releaseCapture) {
            h2.onmousemove = null;
            h2.onmouseup = null;
            h2.releaseCapture();
        }
        else {
            document.removeEventListener("mousemove", doDrag3, true);
            document.removeEventListener("mouseup", stopDrag3, true);
        }
        zhezhao.style.display = 'none';
    }
}

function displayDialog(dialog_id, dialog_content) {
    setDragPopDialog(dialog_id);
    document.getElementById(dialog_id).style.display = "block";
    document.getElementById(dialog_content).style.display = "block";
    //$('#' + dialog_id).css('display', 'block');
    //$('#' + dialog_content).css('display', 'block');
}
function setDialogTitle(title) {
    document.getElementById("dialog_title").innerHTML = "<span>" + title +"</span>";
}

function hideDragPopDialog(dialog_id, tag_name) {
    document.getElementById(dialog_id).style.display = "none";
    var contentList = document.getElementById("dialog_content").getElementsByTagName(tag_name);
    for (var i = 0; i < contentList.length; i++) {
        contentList[i].style.display = "none";
        contentList[i].reset();
    }
}