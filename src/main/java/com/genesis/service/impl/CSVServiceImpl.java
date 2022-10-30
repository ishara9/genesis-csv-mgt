package com.genesis.service.impl;

import com.genesis.dto.MedDTO;
import com.genesis.exception.ServerRequestException;
import com.genesis.model.Record;
import com.genesis.repository.CSVRepository;
import com.genesis.service.CSVService;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class CSVServiceImpl implements CSVService {

  private final CSVRepository CSVRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<MedDTO> getMeds() {
    return CSVRepository.findAll().stream().map(record -> modelMapper.map(record, MedDTO.class)).collect(
        Collectors.toList());
  }

  @Override
  public MedDTO getMed(Long medId) {
    log.debug("Get med by Id: " + medId);
    return CSVRepository.findAll().stream().filter(record -> record.getId().equals(medId))
        .map(record -> modelMapper.map(record, MedDTO.class))
        .findFirst()
        .orElseThrow(() -> {
          log.error("error when getting med {}", medId);
          return new ServerRequestException("med not found with medId:" + medId);
        });
  }

  @Override
  public void createMeds(List<MedDTO> medDTOS) {
    List<Record> records = modelMapper.map(medDTOS, new TypeToken<List<Record>>() {
    }.getType());
    CSVRepository.saveAll(records);
  }

  @Override
  public void deleteMedById(Long medId) {
    CSVRepository.deleteById(medId);
  }

  @Override
  @Transactional
  public void updateMed(MedDTO medDTO, Long medId) {
    CSVRepository.findById(medId)
        .ifPresent(value -> value.setName(medDTO.getName()).setEmail(medDTO.getEmail())
            .setSecurityKey(medDTO.getSecurityKey()));
  }

  @Override
  @Transactional
  public void updatePartialMed(MedDTO medDTO, Long medId) {
    CSVRepository.findById(medId)
        .ifPresent(value -> {
          Optional.ofNullable(medDTO.getName()).map(value::setName);
          Optional.ofNullable(medDTO.getEmail()).map(value::setEmail);
          Optional.ofNullable(medDTO.getSecurityKey()).map(value::setSecurityKey);
        });
  }

  @Override
  public void upload(MultipartFile file) {
    Reader reader = new FileReader(file);
    Iterable<CSVRecord> records= CSVFormat.DEFAULT.parse((Reader) file);
//    final CSVFormat csvFormat = CSVFormat.Builder.create()
//        .setHeader(HEADERS)
//        .setAllowMissingColumnNames(true)
//        .build();
    final Iterable<CSVRecord> records = csvFormat.parse(reader);
    if (CS.hasCSVFormat(file)) {
      try {
        fileService.save(file);

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }
}
