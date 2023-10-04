package faang.school.projectservice.service;

import faang.school.projectservice.client.PaymentServiceClient;
import faang.school.projectservice.client.UserServiceClient;
import faang.school.projectservice.dto.client.Currency;
import faang.school.projectservice.dto.donation.DonationDto;
import faang.school.projectservice.service.exception.DataValidationException;
import faang.school.projectservice.service.exception.enumException.EntityStatusException;
import faang.school.projectservice.mapper.DonationMapperImpl;
import faang.school.projectservice.model.Campaign;
import faang.school.projectservice.model.CampaignStatus;
import faang.school.projectservice.model.Project;
import faang.school.projectservice.repository.CampaignRepository;
import faang.school.projectservice.repository.DonationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @InjectMocks
    private DonationService donationService;

    @Spy
    private DonationMapperImpl donationMapper;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private PaymentServiceClient paymentServiceClient;
    @Mock
    private DonationRepository donationRepository;
    @Mock
    private CampaignRepository campaignRepository;
    private Campaign campaign;
    private DonationDto donationDto;
    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId(2L);

        campaign = new Campaign();
        campaign.setId(1L);
        campaign.setProject(project);
        campaign.setStatus(CampaignStatus.ACTIVE);
        campaign.setTitle("Hello ");
        campaign.setDescription("world!");

        donationDto = new DonationDto();
        donationDto.setId(2L);
        donationDto.setCurrency(Currency.USD);
        donationDto.setAmount(BigDecimal.valueOf(100));
        donationDto.setCampaignId(campaign.getId());
        donationDto.setUserId(1L);
    }

    @Test
    public void send_Successful() {
        Mockito.when(campaignRepository.findById(donationDto.getCampaignId()))
                .thenReturn(Optional.of(campaign));

        donationService.send(donationDto);

        Mockito.verify(donationRepository, Mockito.times(1))
                .save(donationMapper.toEntity(donationDto));
    }

    @Test
    public void send_throwException_checkCampaignNotFound() {
        Mockito.when(campaignRepository.findById(donationDto.getCampaignId()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(DataValidationException.class,
                () -> donationService.send(donationDto));
    }

    @Test
    public void send_throwException_checkStatus() {
        Campaign campaign1 = new Campaign();
        campaign1.setId(3L);
        campaign1.setProject(project);
        campaign1.setStatus(CampaignStatus.CANCELED);

        DonationDto donationDto1 = new DonationDto();
        donationDto1.setId(2L);
        donationDto1.setCurrency(Currency.USD);
        donationDto1.setAmount(BigDecimal.valueOf(100));
        donationDto1.setCampaignId(campaign1.getId());

        Mockito.when(campaignRepository.findById(donationDto1.getCampaignId()))
                .thenReturn(Optional.of(campaign1));

        Assertions.assertThrows(EntityStatusException.class,
                () -> donationService.send(donationDto1));
    }
}