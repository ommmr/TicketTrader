var ws;

function connect() {
    var seller = document.getElementById("seller").value;
	var buyer = document.getElementById("buyer").value;
    var ticket = document.getElementById("ticket").value;
    
    var host = document.location.host;
    var pathname = document.location.pathname;
    
    ws = new WebSocket("ws://" +"cs309-pp-1.misc.iastate.edu:8080"+"/websocket" + "/" + seller + "/" + buyer + "/" + ticket);

    ws.onmessage = function(event) {
    var log = document.getElementById("log");
        console.log(event.data);
        log.innerHTML += event.data + "\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;
    
    ws.send(content);
}