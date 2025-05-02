package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/ads")
public class AdsController {

    @GetMapping
    public ResponseEntity<Ads> findAllAds() {
        return ResponseEntity.ok(new Ads());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ad> createAd(@RequestBody Ad ad, @RequestParam MultipartFile image) {
        return ResponseEntity.status(201).body(ad);
    }

    @GetMapping("{id}")
    public ResponseEntity<ExtendedAd> findAdById(@PathVariable Integer id) {
        return ResponseEntity.ok(new ExtendedAd());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdById(@PathVariable Integer id) {
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Ad> updateAdById(@PathVariable Integer id, @RequestBody CreateOrUpdateAd ad) {
        return ResponseEntity.ok(new Ad());
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> findAllAdsByAuthorizedUser() {
        return ResponseEntity.ok(new Ads());
    }

    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImageOfAdById(@PathVariable Integer id, @RequestParam MultipartFile image) {
        return ResponseEntity.ok(new byte[0]);
    }
}
