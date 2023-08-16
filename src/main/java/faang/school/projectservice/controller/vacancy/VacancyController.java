package faang.school.projectservice.controller.vacancy;

import faang.school.projectservice.dto.vacancy.VacancyDto;
import faang.school.projectservice.dto.vacancy.VacancyDtoReqUpdate;
import faang.school.projectservice.service.vacancy.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vacancy")
@Validated
@RequiredArgsConstructor
@Tag(name = "Вакансии", description = "Взаимодействие с вакансиями.")
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping()
    @Operation(summary = "Создать новую вакансию.",
            description = "Позволяет создать вакансию. Вакансия принадлежит проекту. " +
                    "Вакансию может создать участник с определенной ролью.")
    public VacancyDto createVacancy(@Valid @RequestBody VacancyDto vacancyDto) {
        return vacancyService.createVacancy(vacancyDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить существующую вакансию.",
            description = "Вакансию может обновить менеджер или владелец. " +
                    "Закрыть вакансию возможно когда набрано 5 и более участников.")
    public VacancyDto updateVacancy(@NotNull @PathVariable("id") Long vacancyId,
                                    @Valid @RequestBody VacancyDtoReqUpdate vacancyDto) {
        return vacancyService.updateVacancy(vacancyId, vacancyDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить существующую вакансию.",
            description = "Удалить вакансию по ID. При удалении вакансии будут также удалены все кандидаты, " +
                    "если они не были приняты в команду")
    public void deleteVacancy(@NotNull @PathVariable("id") Long vacancyId) {
        vacancyService.deleteVacancy(vacancyId);
    }
}