package train_service.train_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import train_service.train_service.models.Train;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainDetailsDTO {
    private Train train;
    private DriverDTO driver;
    private List<TravelerDTO> passengers;
}

