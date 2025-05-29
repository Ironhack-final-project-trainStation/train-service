package train_service.train_service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import train_service.train_service.exceptions.TrainNotFoundException;
import train_service.train_service.models.Train;
import train_service.train_service.repositories.TrainRepository;
import train_service.train_service.service.TrainService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ServiceTestUnitarios {
    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private TrainService trainService;

    @Test
    void testFindTrainById_ReturnsTrain() {
        Train train = new Train("train1", 200, "Sevilla", 1L);
        Mockito.when(trainRepository.findById("train1")).thenReturn(Optional.of(train));

        Train result = trainService.findTrainById("train1");

        assertNotNull(result);
        assertEquals("train1", result.getId());
        assertEquals("Sevilla", result.getDestination());
    }

    @Test
    void testFindTrainById_NotFound() {
        Mockito.when(trainRepository.findById("train1")).thenReturn(Optional.empty());

        assertThrows(TrainNotFoundException.class, () -> {
            trainService.findTrainById("train1");
        });
    }

    @Test
    void testSaveTrain() {
        Train newTrain = new Train(null, 200, "Sevilla", 1L);
        Train savedTrain = new Train(null, 200, "Sevilla", 1L);

        Mockito.when(trainRepository.save(newTrain)).thenReturn(savedTrain);

        Train result = trainService.saveTrain(newTrain);

        assertNotNull(result);
        assertEquals(200, result.getMaxCapacity());
        assertEquals("Sevilla", result.getDestination());
    }

    @Test
    void testDeleteTrain() {
        String idToDelete = "train1";
        Train testTrain = new Train(idToDelete, 200, "Sevilla", 1L);

        Mockito.when(trainRepository.findById(idToDelete)).thenReturn(Optional.of(testTrain));
        Mockito.doNothing().when(trainRepository).delete(testTrain);

        assertDoesNotThrow(() -> trainService.deleteTrain(idToDelete));
    }

    @Test
    void testDeleteTrain_NotFound() {
        Mockito.when(trainRepository.findById("train333")).thenReturn(Optional.empty());
        assertThrows(TrainNotFoundException.class, () -> trainService.deleteTrain("train333"));
    }
}

