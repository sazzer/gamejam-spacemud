/** The WebSocket URL to connect to */
const WS_URL = process.env.REACT_APP_WS_URL;

const ws = new WebSocket(WS_URL);
ws.onopen = function(evt) {
    console.log(evt);
};

ws.onmessage = function(data) {
    console.log(data);
};

ws.onclose = function(evt) {
    console.log(evt);
};

ws.onerror = function(evt) {
    console.log(evt);
};

export default ws;
