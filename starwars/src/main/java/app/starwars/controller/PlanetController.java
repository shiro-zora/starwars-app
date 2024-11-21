package app.starwars.controller;

import java.util.List;
import app.starwars.dto.PlanetDto;
import app.starwars.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/planets")
public class PlanetController {

  private final PlanetService planetService;

  @Autowired
  public PlanetController(PlanetService planetService) {
    this.planetService = planetService;
  }

  @GetMapping
  public ResponseEntity<List<PlanetDto>> getAllPlanets() {
    List<PlanetDto> planets = planetService.getAllPlanets();
    return ResponseEntity.ok(planets);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PlanetDto> getPlanetById(@PathVariable Long id) {
    return planetService.getPlanetById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/search")
  public ResponseEntity<List<PlanetDto>> getPlanetsByName(@RequestParam String name) {
    List<PlanetDto> planets = planetService.getPlanetByName(name);
    if (planets.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(planets);
  }

  @PostMapping
  public ResponseEntity<PlanetDto> addPlanet(@RequestBody PlanetDto PlanetDto) {
    PlanetDto createdPlanet = planetService.addPlanet(PlanetDto);
    return new ResponseEntity<>(createdPlanet, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
    planetService.deletePlanet(id);
    return ResponseEntity.noContent().build();
  }
}
