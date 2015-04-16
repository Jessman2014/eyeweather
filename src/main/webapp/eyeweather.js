/**
 * 
 */

/*function addLatlon (){
	var userName = $('.user').text();
	var latitude = $('#lat').text();
	var longitude = $('#long').text();
	if (userName && latitude && longitude) {
		$.ajax({
			url: "/latlon/users/" + userName + "/locations",
			method: "POST",
			dataType: "json",
			data: {"lat": latitude, "long": longitude},
			success: getAll(),
			error: function(jqXHR, textStatus, errorThrown) {
				  console.log(textStatus, errorThrown);
			}
		})
	}
}
*/

$( function () {
	getAll();
	//$('.btn')[0].click(addLatlon);
	
	function getAll (){
		var userName = $('.user').text();
		if (userName) {
			$.ajax({
				url: "/latlon/users/" + userName + "/locations",
				method: "GET",
				dataType: "json",
				success: function(results) {
					if (results.status === "OK"){
						var latlons = results.latlons;
						
						$.each(latlons, function(i,val) {
							$('div.container').append('<div class="row"><p id="head"><span>' + val.address + 
									'</span><span id="lid">' + val.id + '</span></p><p id="body">Coordinates:(' + val.latitude + ', ' + val.longitude + 
									')<br>Time: ' + val.time + '<br>Wind: ' + val.winds + ', Temperature: ' +
									val.temp + ', Humdidity: ' + val.relh + '<br>Current: ' + val.weather + 
									'<br>Forecast: ' + val.forecast + '</p>');
							$('#lid')[0].click(function(){
									deleteLatlon(val.id);});
						});
						
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					  console.log(textStatus, errorThrown);
				}
			})
		}
	}

	function deleteLatlon (lid){
		var userName = $('.user').text();
		if (userName && lid) {
			$.ajax({
				url: "/latlon/users/" + userName + "/locations/" + lid,
				method: "DELETE",
				dataType: "json",
				success: getAll(),
				error: function(jqXHR, textStatus, errorThrown) {
					  console.log(textStatus, errorThrown);
				}
			})
		}
	}

	
});
