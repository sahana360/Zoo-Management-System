package edu.mum.mpp.controller;


import edu.mum.mpp.exceptions.BadRequestException;
import edu.mum.mpp.exceptions.ConflictException;
import edu.mum.mpp.model.*;
import edu.mum.mpp.service.AnimalService;
import edu.mum.mpp.util.CustomResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/zoo")
public class AnimalController {

    @Autowired
    AnimalService animalService;


    @RequestMapping(value = "/manageAnimal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> manageAnimal(@RequestBody @Valid Animal animal) throws Exception {


        if (animal.getName() == null || animal.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal Name cannot be empty");

        if (animal.getTag() == null || animal.getTag().isEmpty())
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Tag cannot be empty");

        if (animal.getBlockId() < 0)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Block cannot be empty");

        //animal.setCellId(AnimalDataUtil.lastId + 1);//TODO REMOVE LATER JUST FOR TESTING, lastId SHOULD BE PRIVATE

        if (animal.getCellId() < 0)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Cell cannot be empty");

        if (animalService.checkAnimal(animal.getCellId())) {
            throw new ConflictException(CustomResponseCode.INVALID_REQUEST, "Another Animal already exist assigned to this Cell");
        }

        long id = animalService.manageAnimal(animal);

        Response resp = new Response();


        HttpStatus httpCode = (id > 0L) ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
        resp.setDescription((id > 0L) ? "Operation successful" : "Operation failed");

        return new ResponseEntity<>(resp, httpCode);

    }


    @RequestMapping(value = "/animal/paging", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_VIEW_ALL_USERS')")
    public Page<AnimalReport> getAnimals(
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") Long pageSize) throws Exception {
        return animalService.getAnimals(pageNum, pageSize);
    }


    @RequestMapping(value = "/animal/report", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    //@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_VIEW_ALL_USERS')")
    public Page<PaymentReport> getreports(
            @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
            @RequestParam(value = "pageSize", defaultValue = "20") Long pageSize) throws Exception {
        return animalService.report(pageNum, pageSize);
    }




    @RequestMapping(value = "/animal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody @Validated Animal animal) {

        // ObjectMapper objectMapper = new ObjectMapper();

        if (animal.getName() == null || animal.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal Name cannot be empty");

        if (animal.getTag() == null || animal.getTag().isEmpty())
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Tag cannot be empty");

        if (animal.getBlockId() < 0)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Block cannot be empty");

        //animal.setCellId(AnimalDataUtil.lastId + 1);//TODO REMOVE LATER JUST FOR TESTING, lastId SHOULD BE PRIVATE

        if (animal.getCellId() < 0)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Cell cannot be empty");

        if (animalService.checkAnimal(animal.getCellId())) {
            throw new ConflictException(CustomResponseCode.INVALID_REQUEST, "Another Animal already exist assigned to this Cell");
        }

        //    User user = TokenService.getCurrentUserFromSecurityContext();
        //   role.setCreatedBy(user.getId());

        Response resp = new Response();
        animalService.create(animal);
        HttpStatus httpCode = (animal.getId() > 0) ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
        resp.setDescription((animal.getId() > 0) ? "Operation successful" : "Operation failed");

        return new ResponseEntity<>(resp, httpCode);
    }

    @RequestMapping(value = "/animal", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> edit(@RequestBody @Validated Animal animal) {

        if (animal.getId() < 1)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal Id cannot be empty");

        if (animal.getName() == null || animal.getName().isEmpty())
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal Name cannot be empty");

        if (animal.getTag() == null || animal.getTag().isEmpty())
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Tag cannot be empty");

        if (animal.getBlockId() < 0)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Block cannot be empty");

        if (animal.getCellId() < 0)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Cell cannot be empty");

        AnimalReport animalTemp = animalService.getSingleAnimal(animal.getId());
        if (animalTemp == null) {
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, " Animal record does not exist");
        }


        Response resp = new Response();
        animalService.editAnimal(animal);
        HttpStatus httpCode = (animal.getId() > 0) ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
        resp.setDescription((animal.getId() > 0) ? "Edit Operation successful" : "Edit Operation failed");

        return new ResponseEntity<>(resp, httpCode);
    }


    @RequestMapping(value = "/animal", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)

    public List<Animal> getAninals() {
        return animalService.getAnimals();
    }


    @RequestMapping(value = "/animal/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public AnimalReport getAnimalById(@PathVariable long id) {

        if (id < 1)
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal Id cannot be empty");

        AnimalReport animal = animalService.getSingleAnimal(id);
        if (animal == null) {
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal does not exist");
        }

        return animal;
    }

    @RequestMapping(value = "/animal/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public AnimalReport searchAnimal(@RequestParam("name") String name) {

        if (name == null || name.isEmpty())
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal Name cannot be empty");

        AnimalReport animal = animalService.searchAnimal(name);
        if (animal == null) {
            throw new BadRequestException(CustomResponseCode.INVALID_REQUEST, "Animal does not exist");
        }

        return animal;
    }

}
