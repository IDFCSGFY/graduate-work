package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ApiResponse;
import ru.skypro.homework.dto.Pet;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pet")
public class PetController {

    @PutMapping
    public ResponseEntity<Pet> updatePet(@RequestBody Pet pet) {
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        return ResponseEntity.ok(pet);
    }

    @GetMapping("/findByStatus")
    public ResponseEntity<List<Pet>> findPetsByStatuses(@RequestParam String status) {
        return ResponseEntity.ok(new ArrayList<>(List.of(new Pet())));
    }

    @GetMapping("/findByTags")
    public ResponseEntity<List<Pet>> findPetsByTags(@RequestParam String[] tags) {
        return ResponseEntity.ok(new ArrayList<>(List.of(new Pet())));
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> findPetById(@PathVariable Long petId) {
        return ResponseEntity.ok(new Pet());
    }

    @PostMapping("/{petId}")
    public ResponseEntity<Pet> findPetById(@PathVariable Long petId, @RequestParam String petName, @RequestParam String petStatus) {
        return ResponseEntity.ok(new Pet());
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePetById(@PathVariable Long petId) {
        return ResponseEntity.ok("Pet deleted");
    }

    @PostMapping(value = "/{petId}/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> uploadPetImage(@PathVariable Long petId, @RequestParam MultipartFile petImage) {
        return ResponseEntity.ok(new ApiResponse());
    }
}
