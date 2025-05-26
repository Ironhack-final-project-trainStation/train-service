package train_service.train_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import train_service.train_service.models.Train;
import train_service.train_service.repositories.TrainRepository;

@SpringBootTest
public class TrainTest {
    @Autowired
    TrainRepository trainRepository;

    @Test
    public void addTrain () {
        Train testTrain = new Train();
        testTrain.setId("AV8855");
        testTrain.setDestination("Valencia");
        testTrain.setMaxCapacity(385);
        trainRepository.save(testTrain);
        System.out.println("New train info : "+ testTrain);
        //trainRepository.delete(testTrain);

    }

}
