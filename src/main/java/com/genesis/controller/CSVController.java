package com.genesis.controller;

import com.genesis.dto.MedDTO;
import com.genesis.service.CSVService;
import com.genesis.service.impl.CSVServiceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(path = "api/v1/csv")
@RestController
@Slf4j
@AllArgsConstructor
public class CSVController {

  private final CSVService CSVService;
  private final CSVServiceImpl medsService;

  @PostMapping()
  public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file) {
    CSVService.upload(file);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }


//
//  @GetMapping
//  ResponseEntity<List<MedDTO>> getMeds() {
//    return new ResponseEntity<>(CSVService.getMeds(), HttpStatus.OK);
//  }
//
//  @GetMapping(path = "{medId}")
//  ResponseEntity<MedDTO> getMed(@PathVariable Long medId) {
//    return new ResponseEntity<>(CSVService.getMed(medId), HttpStatus.OK);
//  }
//
//
//
//  @PutMapping(path = "{medId}")
//  ResponseEntity<Void> updateMed(@RequestBody MedDTO MedDTO, @PathVariable Long medId) {
//    CSVService.updateMed(MedDTO, medId);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
//
//  @PatchMapping(path = "{medId}")
//  ResponseEntity<Void> updatePatchMed(@RequestBody MedDTO partialMedDTO,
//      @PathVariable Long medId) {
//    CSVService.updatePartialMed(partialMedDTO, medId);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
//
//  @DeleteMapping(path = "{medId}")
//  ResponseEntity<Void> deleteMed(@PathVariable Long medId) {
//    CSVService.deleteMedById(medId);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
}
