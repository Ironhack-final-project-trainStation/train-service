package train_service.train_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Train {

    @Id
    private String id;

    @NotNull(message = "Capacity is required")
    @Min(value = 100, message = "Capacity must be greater than 100")
    private int maxCapacity;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Driver id is required")
    private Long driverId;
}
