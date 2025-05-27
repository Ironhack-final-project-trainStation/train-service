package train_service.train_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import train_service.train_service.dtos.DriverDTO;
import train_service.train_service.dtos.TrainDetailsDTO;
import train_service.train_service.dtos.TravelerDTO;
import train_service.train_service.exceptions.TrainNotFoundException;
import train_service.train_service.feignclients.DriverFeignClient;
import train_service.train_service.feignclients.TravelerFeignClient;
import train_service.train_service.models.Train;
import train_service.train_service.repositories.TrainRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TrainService {
    @Autowired
    TrainRepository trainRepository;

    @Autowired
    DriverFeignClient driverFeignClient;

    @Autowired
    TravelerFeignClient travelerFeignClient;

    public List<Train> findAllTrains() throws TrainNotFoundException {
        List<Train> trains = trainRepository.findAll();
        if (trains.isEmpty()) {
            throw new TrainNotFoundException("No trains found in the system");
        }
        return trains;
    }
    public Train findTrainById(String id) throws TrainNotFoundException {
        return trainRepository.findById(id)
                .orElseThrow(() -> new TrainNotFoundException("Train not found"));
    }

    public TrainDetailsDTO findTrainInfoById(String id) throws TrainNotFoundException {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new TrainNotFoundException("Train not found"));

        DriverDTO driver = driverFeignClient.getDriverByTrainId(id);
        List<TravelerDTO> travelers = travelerFeignClient.getTravelerByTrainId(id);

        return new TrainDetailsDTO(train, driver, travelers);
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
