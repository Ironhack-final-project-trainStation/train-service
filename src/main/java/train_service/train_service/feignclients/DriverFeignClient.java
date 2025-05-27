package train_service.train_service.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "driver-service", url = "http://localhost:8082")
public interface DriverFeignClient {

    @GetMapping("api/driver/train/{trainId}")
    Map<String, Object> getDriverByTrainId(@PathVariable String trainId);
}
