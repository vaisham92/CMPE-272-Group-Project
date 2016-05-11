function pack(chars) {
	var s = "";
	for (var i = 0, l = chars.length; i < l; i++)
		s += String.fromCharCode(chars[i]);
	return s;
}