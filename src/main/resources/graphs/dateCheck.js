/** This function checks whether an input is a date */
function checkDate(check) {
	if ( /\d{4}-\d{2}-\d{2}/.test(check) )
		return true;
	return checkTimeStamp(check);
}

/** This function checks whether an input is of the form of a timestamp */
function checkTimeStamp(check) {
	if ( /\d{4}-\d{2}-\d{2}T\d{2}:\d{2}/.test(check) )
		return true;
	else return false
}