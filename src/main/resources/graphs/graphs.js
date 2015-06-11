var data;
var comboBoxes = [];
var selected = false;

/** This function adds a comboBox to the list of comboBoxes */
function addCombobox() {
	var text = document.getElementById("text");
	var combobox = document.createElement("select");
	combobox.setAttribute("id", "combobox");
	text.appendChild(combobox);

	var keys = [];
	for(var k in data[0]) keys.push(k);

	for (var i = 0; i < keys.length; i++) {
		var option = document.createElement("option");
		option.value=i;
		option.innerHTML=keys[i];
		combobox.appendChild(option);
	}

	comboBoxes.push(combobox);
}

/** This function deletes a combobox from the list */
function deleteCombobox() {
	if (comboBoxes.length != 0) {
		var comboBox = comboBoxes.pop();
		alert(comboBox);
		comboBox.parentNode.removeChild(comboBox);
	}
}

/** This function shows the required number of comboboxes */
function showComboboxes(numberOfComboBoxes) {
	var change = numberOfComboBoxes - comboBoxes.length;
	if (change < 0) {
		while (change != 0) {
			deleteCombobox();
			change++;
		}
	} else if ( change > 0) {
		while (change != 0) {
			addCombobox();
			change--;
		}
	}
}

/** This function changes the appearance of the view depending on the selected graph. */
function selectGraphs() {
	if (selected != false) {
		var graphSelect = document.getElementById("graphSelect");
		var numberToShow = 0;
		switch(graphSelect.selectedIndex){
			case 0:
				numberToShow = 2;
				break;
			case 1:
				numberToShow = 4;
			default:
				break;
		}
		showComboboxes(numberToShow);
		addRunButton();
	}
}

/** This function adds the runbutton, if a configuration is valid. */
function addRunButton() {
	removeRunButton();
	var text = document.getElementById("text");
	var runButton = document.createElement("BUTTON"); 
	runButton.setAttribute("id", "runButton");
	runButton.setAttribute("onClick", "drawGraph()");
	runButton.appendChild(document.createTextNode("Draw Graph"));
	text.appendChild(runButton);
}

/** This function removes the runbutton, for when the configuration gets invalid. */
function removeRunButton () {
	var runButton = document.getElementById("runButton");
	if (runButton != null) {
		runButton.parentNode.removeChild(runButton);
	}
}

/** This function draws the graph */
function drawGraph() {
	var graphSelect = document.getElementById("graphSelect");
	alert(graphSelect.selectedIndex);
	switch(graphSelect.selectedIndex){
		case 0:
			drawLineGraph(comboBoxes[0], comboBoxes[1]);
			break;
		case 1:
			numberToShow = 4;
	}
}

/** This functino reads a file, that you have chosen. */
function readSingleFile(e) {
  var file = e.target.files[0];
  if (!file) {
    return;
  }
  var reader = new FileReader();
  reader.onload = function(e) {
    var contents = e.target.result;
    displayContents(contents);
	selected = 1;
	data = $.csv.toObjects(contents);
	
	// Delete all the comboboxes that are there now.
	for(var i = 0; i < comboBoxes.length; i++) {
		deleteCombobox();
	}
	selectGraphs();
	
  };
  reader.readAsText(file);
}

/** This function displays the content of this file */
function displayContents(contents) {
  var element = document.getElementById('file-content');
  element.innerHTML = contents;
}
	
	