function export_svg() {
	var svg = document.getElementsByTagName("svg")[0];
	var svg_xml = (new XMLSerializer).serializeToString(svg);
	return svg_xml;
}