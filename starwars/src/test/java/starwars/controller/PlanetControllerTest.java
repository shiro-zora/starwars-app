package starwars.controller;

import java.util.Optional;
import app.starwars.controller.PlanetController;
import app.starwars.dto.PlanetDto;
import app.starwars.model.Planet;
import app.starwars.service.PlanetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = PlanetController.class)
@AutoConfigureMockMvc
public class PlanetControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PlanetService planetService;

  @Test
  public void testGetPlanetById_InvalidIdFormat() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/planets/invalid-id"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testGetPlanetById() throws Exception {
    PlanetDto mockPlanetDto = new PlanetDto(
        new Planet(1L, "Tatooine", "Arid", "Desert", 1));
    Mockito.when(planetService.getPlanetById(1L)).thenReturn(Optional.of(mockPlanetDto));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/planets/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tatooine"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.climate").value("Arid"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.terrain").value("Desert"));
  }

  @Test
  public void testGetPlanetById_NotFound() throws Exception {
    Mockito.when(planetService.getPlanetById(99L)).thenReturn(Optional.empty());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/planets/99"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
