package app.starwars.repository;

import java.util.List;
import app.starwars.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

  List<Planet> findByName(String name);
}