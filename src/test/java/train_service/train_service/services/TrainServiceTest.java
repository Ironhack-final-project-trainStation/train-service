package train_service.train_service.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import train_service.train_service.models.Train;
import train_service.train_service.service.TrainService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TrainServiceTest {

    @Autowired
    TrainService trainService;

    @Test
    @DisplayName("The train we receive is correct")
    public void getTrainById() {
        Train foundTrain = trainService.findTrainById("A5002");
        assertNotNull(foundTrain);
    }
}
