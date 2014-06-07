/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var wsUri = "ws://localhost:8080/kdomic_aplikacija_3_web/adresaEndpointAddress";

var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt){
    alert("Problem na websocketu");
};

websocket.onmessage = function(evt){
    updateGrowl();
    alert("Desila se promjena podataka");
};
