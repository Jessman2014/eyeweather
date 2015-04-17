package latlon;

import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EyeweatherService {
	@Autowired
	private EyeweatherRepository eyeweatherRepository;
	
	public static final String ADDRESS_HOST = "maps.googleapis.com/maps/api/geocode/json";
	public static final String WEATHER_HOST = "forecast.weather.gov/MapClick.php";
	
	public void createLatlon(String userId, String latitude, String longitude) {
		Latlon newLatlon = new Latlon();
		newLatlon.setUserId(userId);
		newLatlon.setId(UUID.randomUUID().toString());
		newLatlon.setLatitude(latitude);
		newLatlon.setLongitude(longitude);
		newLatlon.setDatetime(new Date());
		
		readWeather(newLatlon);
		readAddress(newLatlon);
		
		eyeweatherRepository.addLatlon(newLatlon);
	}
	
	private void readWeather (Latlon latlon) {
		try {
		URI uri = new URIBuilder()
		.setScheme("http")
		.setHost(WEATHER_HOST)
		.setParameter("lat", latlon.getLatitude())
		.setParameter("lon", latlon.getLongitude())
		.setParameter("FcstType", "json")
		.build();
		
		HttpGet httpget = new HttpGet(uri);		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(5000)
		        .setConnectTimeout(5000)
		        .build();
		
		httpget.setConfig(requestConfig);		
		CloseableHttpResponse response = httpclient.execute(httpget);		
		
		HttpEntity result = response.getEntity();
		InputStream stream = result.getContent();			
		ObjectMapper mapper = new ObjectMapper();
		
			JsonNode root = mapper.readTree(stream);
			JsonNode data = root.get("data");
			JsonNode curObs = root.get("currentobservation");
			latlon.setForecast(data.get("text").get(0).asText());
			latlon.setRelh(curObs.get("Relh").asInt());
			latlon.setTemp(curObs.get("Temp").asInt());
			latlon.setWinds(curObs.get("Winds").asInt());
			latlon.setWeather(curObs.get("Weather").asText());
			stream.close();
			httpclient.close();
		} catch (Exception e) {
			//e.printStackTrace();
		} 
	}
	
	private void readAddress (Latlon latlon) {
		try {
			URI uri = new URIBuilder()
			.setScheme("http")
			.setHost(ADDRESS_HOST)
			.setParameter("latlng", latlon.getLatitude() + "," + latlon.getLongitude())
			.setParameter("sensor", "false")
			.build();
			
			HttpGet httpget = new HttpGet(uri);		
			CloseableHttpClient httpclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom()
			        .setSocketTimeout(5000)
			        .setConnectTimeout(5000)
			        .build();
			
			httpget.setConfig(requestConfig);		
			CloseableHttpResponse response = httpclient.execute(httpget);		
			
			HttpEntity result = response.getEntity();
			InputStream stream = result.getContent();			
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(stream);
			JsonNode results = root.get("results");
			JsonNode frmt = results.get(0);
			JsonNode frmtAddr = frmt.get("formatted_address");
			String formattedAddress = frmtAddr.asText();
			latlon.setAddress(formattedAddress);
			stream.close();
			httpclient.close();
		} catch (Exception e) {
			//e.printStackTrace();
		} 
	}
	
	public List<Latlon> getLatlons (String userId) {
		return eyeweatherRepository.getLatlons(userId);
	}

	public boolean deleteLatlon(String userId, String latlonId) {
		return eyeweatherRepository.delete(userId, latlonId);
	}
}
