package train_service.train_service.dtos;

import train_service.train_service.models.Train;

import java.util.List;

public class TrainDetailsDTO {
    private Train train;
    private DriverDTO driver;
    private List<TravelerDTO> passengers;
}

