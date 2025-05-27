package train_service.train_service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import train_service.train_service.dtos.DriverDTO;
import train_service.train_service.dtos.TrainDetailsDTO;
import train_service.train_service.dtos.TravelerDTO;
import train_service.train_service.feignclients.DriverFeignClient;
import train_service.train_service.feignclients.TravelerFeignClient;
import train_service.train_service.models.Train;
import train_service.train_service.repositories.TrainRepository;
import train_service.train_service.service.TrainService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@WebMvcTest(TrainController.class)
public class TrainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainService trainService;

    @MockBean
    private TrainRepository trainRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DriverFeignClient driverFeignClient;

    @MockBean
    private TravelerFeignClient travelerFeignClient;

    @Test
    void testFindAllTrains() throws Exception {
        List<Train> mockTrainsList = List.of(new Train("train1", 200, "Sevilla", 1L), new Train("train2", 200, "Madrid", 2L));

        Mockito.when(trainService.findAllTrains()).thenReturn(mockTrainsList);

        mockMvc.perform(get("/api/train"))
                .andExpect(status().isOk());
    }

    //falta trainByDestination y TrainById

    @Test
    void testFindTrainById_ReturnsAlsoDetails() throws Exception {
        Train train = new Train("train1", 200, "Sevilla", 1L);
        DriverDTO driverDTO = new DriverDTO(1L, "Juan");
        List<TravelerDTO> travelers = List.of(new TravelerDTO("Ana"), new TravelerDTO("Luis"));
        TrainDetailsDTO detailsDTO = new TrainDetailsDTO(train, driverDTO, travelers);

        Mockito.when(trainService.findTrainInfoById("train1")).thenReturn(detailsDTO);

        mockMvc.perform(get("/api/train/train1/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.train.destination").value("Sevilla"))
                .andExpect(jsonPath("$.driver.name").value("Juan"))
                .andExpect(jsonPath("$.passengers[0].name").value("Ana"))
                .andExpect(jsonPath("$.passengers[1].name").value("Luis"));
    }

    @Test
    void testCreateTrain() throws Exception {
        Train train = new Train("train1", 200, "Sevilla", 1L);

        Mockito.when(trainService.saveTrain(Mockito.any())).thenReturn(train);

        mockMvc.perform(post("/api/train")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(train)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.destination").value("Sevilla"))
                .andExpect(jsonPath("$.id").value("train1"));

    }

    @Test
    void testUpdateTrain() throws Exception {
        Train updated = new Train("train1", 200, "Sevilla", 1L);

        Mockito.when(trainService.updateTrain(Mockito.eq("train1"), Mockito.any())).thenReturn(updated);

        mockMvc.perform(put("/api/train/train1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("train1"));

    }

    @Test
    void testDeleteTrain() throws Exception {
        Mockito.doNothing().when(trainService).deleteTrain("train1");
        mockMvc.perform(delete("/api/train/train1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Train deleted successfully"));
    }
}
