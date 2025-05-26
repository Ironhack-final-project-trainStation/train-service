package train_service.train_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import train_service.train_service.exceptions.TrainNotFoundException;
import train_service.train_service.models.Train;
import train_service.train_service.service.TrainService;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    @Autowired
    TrainService trainService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTrainById(@PathVariable String id) {
        try {
            Train foundTrain = trainService.findTrainById(id);
            return new ResponseEntity<>(foundTrain, HttpStatus.OK);
        } catch (TrainNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>("Train deleted succesfully", HttpStatus.OK);
        } catch (TrainNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
