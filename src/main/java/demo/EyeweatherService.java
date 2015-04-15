package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
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
	
	public static final String GOOGLE_HOST = "maps.googleapis.com/maps/api/geocode/json";
	public static final String WEATHER_HOST = "forecast.weather.gov/MapClick.php";
	
	public void createLatlon(String userId, Double latitude, Double longitude) throws URISyntaxException, ClientProtocolException, IOException {
		Latlon newLatlon = new Latlon();
		newLatlon.setUserId(userId);
		newLatlon.setId(latitude.toString());
		newLatlon.setLatitude(latitude);
		newLatlon.setLongitude(longitude);
		
		URI googleUri = new URIBuilder()
		.setScheme("http")
		.setHost(GOOGLE_HOST)
		.setParameter("latlng", latitude.toString() + "," + longitude.toString())
		.setParameter("sensor", "false")
		.build();
		
		URI weatherUri = new URIBuilder()
		.setScheme("http")
		.setHost(WEATHER_HOST)
		.setParameter("lat", latitude.toString())
		.setParameter("lon", longitude.toString())
		.setParameter("FcstType", "json")
		.build();
		
		HttpGet httpget1 = new HttpGet(googleUri);		
		CloseableHttpClient httpclient1 = HttpClients.createDefault();
		RequestConfig requestConfig1 = RequestConfig.custom()
		        .setSocketTimeout(1000)
		        .setConnectTimeout(1000)
		        .build();
		
		httpget1.setConfig(requestConfig1);		
		CloseableHttpResponse response1 = httpclient1.execute(httpget1);		
		
		HttpEntity result1 = response1.getEntity();
		InputStream stream1 = result1.getContent();			
		ObjectMapper mapper1 = new ObjectMapper();
		JsonNode root1 = mapper1.readTree(stream1);
		JsonNode results = root1.get("results");
		JsonNode frmt = results.get(0);
		JsonNode frmtAddr = frmt.get("formatted_address");
		String formattedAddress = frmtAddr.asText();
		newLatlon.setAddress(formattedAddress);
		stream1.close();
		httpclient1.close();
		
		HttpGet httpget2 = new HttpGet(weatherUri);		
		CloseableHttpClient httpclient2 = HttpClients.createDefault();
		RequestConfig requestConfig2 = RequestConfig.custom()
		        .setSocketTimeout(1000)
		        .setConnectTimeout(1000)
		        .build();
		
		httpget2.setConfig(requestConfig2);		
		CloseableHttpResponse response2 = httpclient2.execute(httpget2);		
		
		HttpEntity result2 = response2.getEntity();
		InputStream stream2 = result2.getContent();			
		ObjectMapper mapper2 = new ObjectMapper();
		JsonNode root2 = mapper2.readTree(stream2);
		root2 = mapper2.readTree(stream2);
		JsonNode data = root2.get("data");
		JsonNode curObs = root2.get("currentobservation");
		newLatlon.setForecast(data.get("text").get(0).asText());
		newLatlon.setRelh(curObs.get("Relh").asInt());
		newLatlon.setTemp(curObs.get("Temp").asInt());
		newLatlon.setWinds(curObs.get("Winds").asInt());
		newLatlon.setWeather(curObs.get("Weather").asText());
		stream2.close();
		httpclient2.close();
		
		/*Collections.sort(repos, new Comparator<Repo>() {
			@Override
			public int compare(Repo r1, Repo r2) {
				return r2.getWatchersCount() - r1.getWatchersCount();
			}
			
		});*/
		
		eyeweatherRepository.addLatlon(newLatlon);
	}
	
	public List<Latlon> getLatlons (String userId) {
		return eyeweatherRepository.getLatlons(userId);
	}

	public void deleteLatlon(String userId, String latlonId) {
		eyeweatherRepository.delete(userId, latlonId);
	}
}
