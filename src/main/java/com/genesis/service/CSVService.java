package com.genesis.service;

import com.genesis.dto.MedDTO;
import java.io.IOException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface CSVService {

//  /**
//   * Get All meds
//   *
//   * @return
//   */
//  List<MedDTO> getMeds();
//
//  /**
//   * Get a med by Id
//   *
//   * @param medId
//   * @return
//   */
//  MedDTO getMed(Long medId);
//
//  /**
//   * Add new set of meds
//   *
//   * @param meds
//   */
//  void createMeds(List<MedDTO> meds);
//
//  /**
//   * Delete a med by Id
//   *
//   * @param medId
//   */
//  void deleteMedById(Long medId);
//
//  /**
//   * Update a med by Id
//   *
//   * @param med
//   * @param medId
//   */
//  @Transactional
//  void updateMed(MedDTO med, Long medId);
//
//  /**
//   * Add a patch to a med
//   *
//   * @param medDTO
//   * @param medId
//   */
//  @Transactional
//  void updatePartialMed(MedDTO medDTO, Long medId);

  /**
   * Upload csv file
   * @param file
   */
  void upload(MultipartFile file);
}
