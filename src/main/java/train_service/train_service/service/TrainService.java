package train_service.train_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train_service.train_service.exceptions.TrainNotFoundException;
import train_service.train_service.models.Train;
import train_service.train_service.repositories.TrainRepository;

import java.util.Optional;

@Service
public class TrainService {
    @Autowired
    TrainRepository trainRepository;

    public Train findTrainById(String id) throws TrainNotFoundException {
        Optional<Train> foundTrain = trainRepository.findById(id);

        if(foundTrain.isPresent()) {
            return foundTrain.get();
        } else {
            throw new TrainNotFoundException("Train not found");
        }
    }
}
