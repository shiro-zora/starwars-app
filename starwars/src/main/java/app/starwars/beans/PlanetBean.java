package app.starwars.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import app.starwars.dto.PlanetDto;
import app.starwars.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("planetBean")
@Scope("session")
public class PlanetBean implements Serializable {

  private static final long serialVersionUID = 1L;

  @Autowired
  private PlanetService planetService;

  @Getter
  @Setter
  private List<PlanetDto> planets;

  @Getter
  @Setter
  private List<PlanetDto> filteredPlanets;

  @Getter
  @Setter
  private String searchId;

  @Getter
  @Setter
  private String searchName;

  @Getter
  @Setter
  private PlanetDto selectedPlanet;

  @Getter
  @Setter
  private PlanetDto newPlanet;

  @PostConstruct
  public void init() {
    loadAllPlanets();
    filteredPlanets = new ArrayList<>(planets);
    newPlanet = new PlanetDto();
  }

  public void loadAllPlanets() {
    planets = planetService.getAllPlanets();
    filteredPlanets = new ArrayList<>(planets);
  }

  public void createPlanet() {
    PlanetDto addedPlanet = planetService.addPlanet(newPlanet);
    planets.add(addedPlanet);
    filteredPlanets.add(addedPlanet);
    newPlanet = new PlanetDto();
  }

  public void deletePlanet() {
    if (selectedPlanet != null) {
      planetService.deletePlanet(selectedPlanet.getId());
      planets.remove(selectedPlanet);
      filteredPlanets.remove(selectedPlanet);
      selectedPlanet = null;
    }
  }

  public void searchPlanets() {
    filteredPlanets = planets.stream()
        .filter(planet -> (searchId == null || searchId.isEmpty() || String.valueOf(planet.getId())
            .contains(searchId)) &&
            (searchName == null || searchName.isEmpty() || planet.getName().toLowerCase()
                .contains(searchName.toLowerCase())))
        .collect(Collectors.toList());
  }

  public void clearSearch() {
    searchId = null;
    searchName = null;
    filteredPlanets = new ArrayList<>(planets);
  }
}
