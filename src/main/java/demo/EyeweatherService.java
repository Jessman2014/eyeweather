package demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EyeweatherService {
	@Autowired
	private EyeweatherRepository eyeweatherRepository;
	
	public static final String GOOGLE_HOST = "maps.googleapis.com/maps/api/geocode/json";
	public static final String WEATHER_HOST = "forecast.weather.gov/MapClick.php";
	
	public void createLatlon(String userId, Integer latitude, Integer longitude) throws URISyntaxException, ClientProtocolException, IOException {
		
		URI googleUri = new URIBuilder()
		.setScheme("http")
		.setHost(GOOGLE_HOST)
		.setParameter("latlng", latitude.toString() + "," + longitude.toString())
		.setParameter("sensor", "false")
		.build();
		//?latlng=43.81,-91.23&sensor=false
		
		URI weatherUri = new URIBuilder()
		.setScheme("http")
		.setHost(WEATHER_HOST)
		.setParameter("lat", latitude.toString())
		.setParameter("lon", longitude.toString())
		.setParameter("FcstType", "json")
		.build();
			//?lat=43.81&lon=-92.23&FcstType=json
		
		HttpGet httpget = new HttpGet(googleUri);		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(1000)
		        .setConnectTimeout(1000)
		        .build();
		
		httpget.setConfig(requestConfig);		
		CloseableHttpResponse response1 = httpclient.execute(httpget);		
		
		HttpEntity result = response1.getEntity();
		InputStream stream = result.getContent();			
		ObjectMapper mapper = new ObjectMapper();
		Address addr = mapper.readValue(stream, new TypeReference<Address>(){});		
		stream.close();
		httpclient.close();
		
		httpget = new HttpGet(weatherUri);		
		httpclient = HttpClients.createDefault();
		requestConfig = RequestConfig.custom()
		        .setSocketTimeout(1000)
		        .setConnectTimeout(1000)
		        .build();
		
		httpget.setConfig(requestConfig);		
		response1 = httpclient.execute(httpget);		
		
		result = response1.getEntity();
		stream = result.getContent();			
		mapper = new ObjectMapper();
		Weather weather = mapper.readValue(stream, new TypeReference<Weather>(){});		
		stream.close();
		httpclient.close();
		
		/*Collections.sort(repos, new Comparator<Repo>() {
			@Override
			public int compare(Repo r1, Repo r2) {
				return r2.getWatchersCount() - r1.getWatchersCount();
			}
			
		});*/
		
		Latlon newLatlon = new Latlon.Builder().userId(userId).latitude(latitude)
				.longitude(longitude).address(addr).weather(weather).build();
		eyeweatherRepository.addLatlon(newLatlon);
	}
	
	public List<Latlon> getLatlons (String userId) {
		return eyeweatherRepository.getLatlons(userId);
	}
}
