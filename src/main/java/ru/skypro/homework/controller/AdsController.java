package ru.skypro.homework.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.impl.AdService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {

    private final AdService service;

    public AdsController(AdService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Ads> findAllAds() {
        return ResponseEntity.ok(service.findAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Ad> createAd(Authentication authentication, @RequestBody Ad ad, @RequestParam MultipartFile image) {
        return ResponseEntity.status(201).body(service.createAd(authentication, ad, image));
    }

    @GetMapping("{id}")
    public ResponseEntity<ExtendedAd> findAdById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findExtendedAdById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdById(Authentication authentication, @PathVariable Integer id) {
        service.deleteAdById(authentication,id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Ad> updateAdById(Authentication authentication, @PathVariable Integer id, @RequestBody CreateOrUpdateAd ad) {
        return ResponseEntity.ok(service.updateAdById(authentication, id, ad));
    }

    @GetMapping("/me")
    public ResponseEntity<Ads> findAllAdsByAuthorizedUser(Authentication authentication) {
        return ResponseEntity.ok(service.findAllAdsByUser(authentication));
    }

    @PatchMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImageOfAdById(Authentication authentication, @PathVariable Integer id, @RequestParam MultipartFile image) {
        return ResponseEntity.ok(service.updateAdImage(authentication, id, image));
    }
}
