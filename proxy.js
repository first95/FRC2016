// If GRIP continues being stupid, we might need this.

var MjpegProxy = require('mjpeg-proxy').MjpegProxy;
var express = require('express');
var app = express();

app.get('/mjpg/video.mjpg', new MjpegProxy('http://10.0.95.22/mjpg/video.mjpg').proxyRequest);
app.listen(8080);