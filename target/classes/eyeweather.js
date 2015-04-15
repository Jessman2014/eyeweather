/**
 * 
 */
var userName = $('.user').text();

$.ajax({
	url: "/latlon/users/" + userName + "/locations",
	method: "GET",
	dataType: "json",
	success: function(results) {
		if (results.status === "OK"){
			var latlons = results.latlons;
			$('div.container').add('<div class="row"><p id="head"></p><p id="body"></p>');
		}
	}
})
