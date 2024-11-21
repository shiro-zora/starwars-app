package app.starwars.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import app.starwars.dto.PlanetDto;
import app.starwars.model.Planet;
import app.starwars.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

  private final PlanetRepository planetRepository;
  private final SwapiService swapiService;

  @Autowired
  public PlanetService(PlanetRepository planetRepository, SwapiService swapiService) {
    this.planetRepository = planetRepository;
    this.swapiService = swapiService;
  }

  public List<PlanetDto> getAllPlanets() {
    return planetRepository.findAll()
        .stream()
        .map(PlanetDto::new)
        .collect(Collectors.toList());
  }

  public Optional<PlanetDto> getPlanetById(Long id) {
    return planetRepository.findById(id).map(PlanetDto::new);
  }

  public List<PlanetDto> getPlanetByName(String name) {
    return planetRepository.findByName(name)
        .stream()
        .map(PlanetDto::new)
        .collect(Collectors.toList());
  }

  public PlanetDto addPlanet(PlanetDto planetDto) {
    Planet planet = planetDto.toEntity();
    int filmAppearance = swapiService.getFilmAppearance(planetDto.getName());
    planet.setAppearance(filmAppearance);
    Planet savedPlanet = planetRepository.save(planet);
    return new PlanetDto(savedPlanet);
  }

  public void deletePlanet(Long id) {
    Planet planet = planetRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException(
            "Planet with id " + id + " not found")
        );

    planetRepository.delete(planet);
  }
}
