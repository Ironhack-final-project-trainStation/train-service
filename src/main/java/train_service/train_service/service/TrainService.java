package train_service.train_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train_service.train_service.exceptions.TrainNotFoundException;
import train_service.train_service.models.Train;
import train_service.train_service.repositories.TrainRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TrainService {
    @Autowired
    TrainRepository trainRepository;

    public List<Train> findAllTrains() throws TrainNotFoundException {
        List<Train> trains = trainRepository.findAll();
        if (trains.isEmpty()) {
            throw new TrainNotFoundException("No trains found in the system");
        }
        return trains;
    }

    public Map<String, Object> findTrainById(String id) throws TrainNotFoundException {
        Optional<Train> foundTrain = trainRepository.findById(id);

        if(foundTrain.isPresent()) {
            return foundTrain.get();
        } else {
            throw new TrainNotFoundException("Train not found");
        }
    }

    public Train findByDestination(String destination) throws TrainNotFoundException {
        return trainRepository.findByDestination(destination)
                .orElseThrow(() -> new TrainNotFoundException("No trains found for destination " + destination));

    }



    public Train saveTrain(Train train) {
        return trainRepository.save(train);
    }

    public Train updateTrain (String id, Train updated) {
        Train train = findTrainById(id);
        train.setDestination(updated.getDestination());
        train.setMaxCapacity(updated.getMaxCapacity());
        train.setDriverId(updated.getDriverId());
        return trainRepository.save(train);
    }

    public void deleteTrain(String id){
        Train train = findTrainById(id);
        trainRepository.delete(train);
    }
}
