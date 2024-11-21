package app.starwars.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import app.starwars.model.Planet;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlanetDto {

  private Long id;
  private String name;
  private String climate;
  private String terrain;
  private int appearance;

  public PlanetDto(Planet planet) {
    this.id = planet.getId();
    this.name = planet.getName();
    this.climate = planet.getClimate();
    this.terrain = planet.getTerrain();
    this.appearance = planet.getAppearance();
  }

  public Planet toEntity() {
    Planet planet = new Planet();
    planet.setId(id);
    planet.setName(name);
    planet.setClimate(climate);
    planet.setTerrain(terrain);
    planet.setAppearance(appearance);
    return planet;
  }
}

