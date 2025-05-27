package train_service.train_service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import train_service.train_service.dtos.TravelerDTO;

import java.util.List;
import java.util.Map;

@FeignClient(name = "traveler-service", url = "http://localhost:8081")
public interface TravelerFeignClient {

    @GetMapping("/api/traveler/train/{trainId}")
    List<TravelerDTO> getTravelerByTrainId(@PathVariable String trainId);
}
