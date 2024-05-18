package comspringboot.challengewl.services;

import com.google.gson.JsonObject;
import comspringboot.challengewl.exceptions.GoogleGeocodingException;
import comspringboot.challengewl.models.Location;
import comspringboot.challengewl.services.clients.GoogleGeocodingClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleGeocodingService {

    private final GoogleGeocodingClient googleGeocodingClient;

    @Value("${google.api.key}")
    private String apiKey;

    public Location getCoordinatesByZipCode(String zipCode) {
        try {
            JsonObject response = googleGeocodingClient.getCoordinatesByZipCode(zipCode, apiKey);


            if (!"OK".equals(response.get("status").getAsString())) {
                throw new GoogleGeocodingException("Erro na resposta da API: " + response.get("status").getAsString());
            }

            if (!response.getAsJsonArray("status").equals("OK")) {
                throw new GoogleGeocodingException("Google Geocoding error");
            }

            JsonObject resposeGoogleLoc = response.getAsJsonArray("results")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("geometry")
                    .getAsJsonObject("location");

            Location location = new Location();
            location.setLat(resposeGoogleLoc.get("lat").getAsDouble());
            location.setLng(resposeGoogleLoc.get("lng").getAsDouble());

            return location;

        } catch (Exception e) {
            throw new GoogleGeocodingException("Erro ao chamar a API do Google Geocoding");
        }
    }
}