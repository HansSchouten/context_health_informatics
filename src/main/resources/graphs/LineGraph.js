// Set the dimensions of the canvas / graph
var margin = {top: 30, right: 20, bottom: 30, left: 50},
width =  800 - margin.left - margin.right,
height = 600 - margin.top - margin.bottom;

// Set the ranges
//var x = d3.time.scale().range([0, width]);
var x = d3.scale.linear().range([0, width]);
var y = d3.scale.linear().range([height, 0]);

// Parse the date / time
var parseDate = d3.time.format.iso.parse;

// Define the axes
var xAxis = d3.svg.axis().scale(x)
.orient("bottom").ticks(5);

var yAxis = d3.svg.axis().scale(y)
.orient("left").ticks(5);

// Define the line
var valueline = d3.svg.line()
.x(function(d) { return x(d.x); })
.y(function(d) { return y(d.y); });

function drawLineGraph(column1, column2) {
	
  var rownumberX = column1.value;
  var rownumberY = column2.value;
  var inputData = [];
  var xData = data.map(function(value,index) { return value[rownumberX]; });
  var yData = data.map(function(value,index) { return value[rownumberX]; });
  
  for (var i = 0; i < data.length; i++) {
	  inputData.push({x: xData[i], y: yData[i]});
  }
  
  console.log(inputData[0].x);
  console.log(inputData[0].y);
  
  var svg = d3.select("body")
  .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
  
  
  // Scale the range of the data
  x.domain(d3.extent(inputData, function(d) { return d.x; }));
  y.domain([0, d3.max(inputData, function(d) { return d.y; })]);

 // Add the valueline path.
  svg.append("path")
      .attr("class", "line")
      .attr("d", valueline(inputData));

  // Add the X Axis
  svg.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + height + ")")
      .call(xAxis);

  // Add the Y Axis
  svg.append("g")
      .attr("class", "y axis")
      .call(yAxis);
}

function remove() {
  // Removes the SVG from the canvas
  d3.select("svg").remove();
}
