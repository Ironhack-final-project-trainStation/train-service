package train_service.train_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import train_service.train_service.dtos.TrainDetailsDTO;
import train_service.train_service.dtos.TravelerDTO;
import train_service.train_service.exceptions.TrainNotFoundException;
import train_service.train_service.feignclients.DriverFeignClient;
import train_service.train_service.feignclients.TravelerFeignClient;
import train_service.train_service.models.Train;
import train_service.train_service.service.TrainService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    @Autowired
    TrainService trainService;

    @Autowired
    DriverFeignClient driverFeignClient;

    @Autowired
    TravelerFeignClient travelerFeignClient;

    @GetMapping
    public ResponseEntity<?> getAllTrains() {
        try {
            List<Train> trains = trainService.findAllTrains();
            return new ResponseEntity<>(trains, HttpStatus.OK);
        } catch (TrainNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<?> getTrainInfo(@PathVariable String id) {
        try {
            TrainDetailsDTO details = trainService.findTrainInfoById(id);
            return ResponseEntity.ok(details);
        } catch (TrainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainById(@PathVariable String id) {
        try {
            Train foundTrain = trainService.findTrainById(id);
            return new ResponseEntity<>(foundTrain, HttpStatus.OK);
        } catch (TrainNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/destination/{destination}")
    public ResponseEntity<?> getTrainByDestination(@PathVariable String destination) {
        try {
            Train train = trainService.findByDestination(destination);
            return ResponseEntity.ok(train);
        } catch (TrainNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Train> createTrain (@RequestBody Train train) {
        Train newTrain = trainService.saveTrain(train);
        return new ResponseEntity<>(newTrain, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrain(@PathVariable String id, @RequestBody Train train) {
        try {
            Train updatedTrain = trainService.updateTrain(id, train);
            return new ResponseEntity<>(updatedTrain, HttpStatus.OK);
        } catch (TrainNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrain (@PathVariable String id) {
        try {
            trainService.deleteTrain(id);
            return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);
        } catch (TrainNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
