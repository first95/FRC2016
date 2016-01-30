// If GRIP continues being stupid, we might need this.

var MjpegProxy = require('mjpeg-proxy').MjpegProxy;
var express = require('express');
var app = express();

app.get('/mjpg/video.mjpg', new MjpegProxy('http://roborio-95-frc.local:1180').proxyRequest);
app.listen(8080);
