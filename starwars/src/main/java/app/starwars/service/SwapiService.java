package app.starwars.service;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SwapiService {

  private static final String SWAPI_BASE_URL = "https://swapi.dev/api/planets/?search=";

  public int getFilmAppearance(String planetName) {
    RestTemplate restTemplate = new RestTemplate();
    String url = SWAPI_BASE_URL + planetName;

    try {
      SwapiResponse response = restTemplate.getForObject(url, SwapiResponse.class);
      if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
        for (SwapiPlanet planet : response.getResults()) {
          if (planet.getName().equalsIgnoreCase(planetName)) {
            List<String> films = planet.getFilms();
            return (films != null) ? films.size() : 0;
          }
        }
      }
    } catch (Exception e) {
      System.out.println("Error fetching data from SWAPI: " + e.getMessage());
    }
    return 0;
  }

  @Getter
  @Setter
  public static class SwapiResponse {

    private List<SwapiPlanet> results;
  }

  @Getter
  @Setter
  public static class SwapiPlanet {

    private String name;
    private List<String> films;
  }
}
