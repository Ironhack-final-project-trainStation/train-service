package train_service.train_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import train_service.train_service.models.Train;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, String> {
    Optional<Train> findByDestination (String destination);
}
