/**
 * 
 */
var userName = $('.user').text();

$.ajax({
	url: "/latlon/users/" + userName + "/locations",
	method: "GET",
	dataType: "json",
	success: function(results) {
		$('div.container').add('<div class="row"><p id="head"></p><p id="body"></p>');
	}
})
