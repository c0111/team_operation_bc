const express = require('express');
const app = new express();
var exec = require('child_process').exec;
var popup = require('popups');

app.get('/', function(request, response){
    response.sendfile('index.html');
});

app.get('/Running', function(request, response){
	response.sendfile('running.html');
	var compileit = 'javac team_operation_bc';
	exec(compileit, function(error, stdout, stderr){
	console.log('program ran');		
});
	var child = require('child_process').spawn(
  'java', ['-jar', 'done.jar', 'argument to pass in']
);

const port = 8080
app.listen(port, () => console.log(`Example app listening on port ${port}!`))
