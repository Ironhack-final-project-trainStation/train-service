package train_service.train_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
