/**
 * 
 */




$( function () {
	var userName = getParameterByName("username");
	$('h2').text(userName);
	
	getAll();
	//$('#lat').first().text("43.77");39.9, 43.81
	//$('#lon').first().text("-91.22");-75.2, -92.233
	$('#submit').click(function(){
		addLatlon();
	});
	$('#delete').click(function(){
		$('.head').each(function() {
			var lid = $(".lid", this).text();
			deleteLatlon(lid);
		});
	});
	$('#get').click(function(){
		getAll();
	});
	
	function getParameterByName(name) {
	    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
	    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
	        results = regex.exec(location.search);
	    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
	}
	
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
						$('.hit').remove();
						$.each(latlons, function(i,val) {
							$('div.container').append('<div class="row hit"><p class="head"><span>' + val.address + 
									'</span><span class="lid">' + val.id + '</span><span  id="glyph'+ i + '" class="glyphicon glyphicon-trash" aria-hidden="true"></span></p><p class="body">Coordinates:(' + val.latitude + ', ' + val.longitude + 
									')<br>Time: ' + val.time + '<br>Wind: ' + val.winds + ', Temperature: ' +
									val.temp + ', Humdidity: ' + val.relh + '<br>Current: ' + val.weather + 
									'<br>Forecast: ' + val.forecast + '</p>');
							
						});
						var glyphs = $('.glyphicon');
						for (i=0; i<glyphs.length; i += 1) {
							var glyphId = '#glyph' + i;
							var id = $(glyphId).prev().text()
							$(glyphId).click( function(){
								deleteLatlon(id);
							});
						}
						
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
				success: function() {
					getAll();
				},
				error: function(jqXHR, textStatus, errorThrown) {
					  console.log(textStatus, errorThrown);
					  getAll();
				}
			})
		}
	}
	
	function addLatlon (){
		var userName = $('.user').text();
		var latitude = $('#lat').val();
		var longitude = $('#lon').val();
		if (userName && latitude && longitude) {
			$.ajax({
				url: "/latlon/users/" + userName + "/locations",
				method: "POST",
				dataType: "json",
				data: {"lat": latitude, "lon": longitude},
				success: function() {
					getAll();
				},
				error: function(jqXHR, textStatus, errorThrown) {
					  console.log(textStatus, errorThrown);
					  getAll();
				}
			})
		}
	}

	
});
