package faang.school.projectservice.controller.vacancy;

import faang.school.projectservice.dto.Vacancy.CreateVacancyDto;
import faang.school.projectservice.dto.Vacancy.ExtendedVacancyDto;
import faang.school.projectservice.dto.Vacancy.UpdateVacancyDto;
import faang.school.projectservice.service.VacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/vacancy")
public class VacancyController {
         private final VacancyService vacancyService;

        @PostMapping("/create")
        public ResponseEntity<ExtendedVacancyDto> create(@Valid @RequestBody CreateVacancyDto vacancyDto) {
            ExtendedVacancyDto newVacancy = vacancyService.create(vacancyDto);
            return ResponseEntity.ok(newVacancy);
        }

        @PutMapping("/update")
        public ResponseEntity<ExtendedVacancyDto> update(@Valid @RequestBody UpdateVacancyDto vacancyDto) {
            ExtendedVacancyDto updated = vacancyService.update(vacancyDto);
            return ResponseEntity.ok(updated);
        }
}
