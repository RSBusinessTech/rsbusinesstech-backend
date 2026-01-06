package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.service;

import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.agent.dto.AgentDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.dto.CustomerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.service.CustomerService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.LeaseInfoDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PMSDashboardSummaryDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PropertyStatusChartDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.email.model.RentalPaymentReminderRequest;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.owner.service.OwnerService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.service.PropertyService;
import com.rsbusinesstech.rsbusinesstech_backend.utils.DateFormatterUtil;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    PropertyService propertyService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OwnerService ownerService;

    @Autowired
    JsonFileUtil jsonFileUtil;

    @Autowired
    DateFormatterUtil dateFormatterUtil;

    public PMSDashboardSummaryDTO getPMSDashboardSummary(String agentId){
        PMSDashboardSummaryDTO pmsDashboardSummaryDTO = new PMSDashboardSummaryDTO();
        PropertyStatusChartDTO propertyStatusChartDTO = new PropertyStatusChartDTO();

        Map<String,Long> customersInfoMap = customerService.getCustomersInfo(agentId);
        Map<String,Long> ownersInfoMap = ownerService.getOwnersInfo(agentId);
        Map<String,Object> rentalPropertiesInfo = propertyService.getPropertiesInfoByType("rent", agentId);
        Map<String,Object> salesPropertiesInfo = propertyService.getPropertiesInfoByType("buy", agentId);

        List<PropertyDTO> allRentalProperties = (List<PropertyDTO>) rentalPropertiesInfo.getOrDefault("totalProperties", Collections.emptyList());
        List<PropertyDTO> rentedOutProperties = (List<PropertyDTO>) rentalPropertiesInfo.getOrDefault("occupiedProperties", Collections.emptyList());
        List<PropertyDTO> toBeRentedProperties = (List<PropertyDTO>) rentalPropertiesInfo.getOrDefault("vacantProperties", Collections.emptyList());

        List<PropertyDTO> allSalesProperties = (List<PropertyDTO>) salesPropertiesInfo.getOrDefault("totalProperties", Collections.emptyList());
        List<PropertyDTO> soldOutProperties = (List<PropertyDTO>) salesPropertiesInfo.getOrDefault("occupiedProperties", Collections.emptyList());
        List<PropertyDTO> toBeSoldProperties = (List<PropertyDTO>) salesPropertiesInfo.getOrDefault("vacantProperties", Collections.emptyList());

        List<LeaseInfoDTO> contractsExpiringThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesRentedThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesSoldThisMonth = new ArrayList<>();

        //Property Info
        pmsDashboardSummaryDTO.setTotalProperties(propertyService.getAllPropertiesCount(agentId));

        pmsDashboardSummaryDTO.setTotalRentalProperties(allRentalProperties.size());
        pmsDashboardSummaryDTO.setTotalRentedOutProperties(rentedOutProperties.size()); //rented out properties
        pmsDashboardSummaryDTO.setTotalToBeRentedProperties(toBeRentedProperties.size()); //to be rented properties

        pmsDashboardSummaryDTO.setTotalSaleProperties(allSalesProperties.size());
        pmsDashboardSummaryDTO.setTotalSoldOutProperties(soldOutProperties.size());  //sold out properties
        pmsDashboardSummaryDTO.setTotalToBeSoldProperties(toBeSoldProperties.size());  //to be sold properties

        pmsDashboardSummaryDTO.setTotalCommercialProperties(propertyService.getPropertiesCountByType("commercial", agentId));
        pmsDashboardSummaryDTO.setTotalMm2hProperties(pmsDashboardSummaryDTO.getTotalSaleProperties());
        pmsDashboardSummaryDTO.setTotalNewProjects(propertyService.getPropertiesCountByType("newprojects", agentId));

        //Property Customer Info
        pmsDashboardSummaryDTO.setTotalTenants(customersInfoMap.getOrDefault("totalTenants",0L));
        pmsDashboardSummaryDTO.setTotalBuyers(customersInfoMap.getOrDefault("totalBuyers", 0L));
        pmsDashboardSummaryDTO.setTotalOwners(ownersInfoMap.getOrDefault("totalOwners",0L));

        //Lists
        pmsDashboardSummaryDTO.setPendingRentalsThisMonth(getPendingRentalsThisMonth(allRentalProperties,agentId));
        pmsDashboardSummaryDTO.setContractsExpiringThisMonth(contractsExpiringThisMonth);
        pmsDashboardSummaryDTO.setPropertiesRentedThisMonth(propertiesRentedThisMonth);
        pmsDashboardSummaryDTO.setPropertiesSoldThisMonth(propertiesSoldThisMonth);

        propertyStatusChartDTO.setOccupied(12);
        propertyStatusChartDTO.setVacant(20);
        propertyStatusChartDTO.setUnderMaintenance(2);
        propertyStatusChartDTO.setReserved(5);
        pmsDashboardSummaryDTO.setPropertyStatusChart(propertyStatusChartDTO);

        return pmsDashboardSummaryDTO;
    }

    public List<LeaseInfoDTO> getPendingRentalsThisMonth(List<PropertyDTO> allRentalProperties, String agentId){
        List<LeaseInfoDTO> pendingRentalsThisMonthList = new ArrayList<>();

        List<CustomerDTO> customers = Optional.ofNullable(jsonFileUtil.readCustomers()).orElse(Collections.emptyList());
        List<CustomerDTO> agentCustomers = customers
                                                    .stream()
                                                    .filter(customer -> StringUtils.hasText(customer.getAgentId()) && StringUtils.hasText(agentId)
                                                                        && customer.getAgentId().equalsIgnoreCase(agentId))
                                                    .collect(Collectors.toList());
        List<AgentDTO> agents = Optional.ofNullable(jsonFileUtil.readAgents()).orElse(Collections.emptyList());

        for(CustomerDTO customer: agentCustomers){
            if(customer != null && "Rental".equalsIgnoreCase(customer.getPropertyType())
                    && "No".equalsIgnoreCase(customer.getIsRentalPaid())
                    && dateFormatterUtil.isBeforeOrToday(customer.getRentalStartDate())){
                LeaseInfoDTO leaseInfoDTO = new LeaseInfoDTO();

                Optional<PropertyDTO> rentalPropertyOptional =  allRentalProperties
                        .stream()
                        .filter(property -> Objects.equals(customer.getPropertyId(), property.getId()) &&
                                Objects.equals(customer.getAgentId(), property.getAgentId()))
                        .findFirst();

                Optional<AgentDTO> agentOptional = agents
                        .stream().filter(agent -> agent != null && Objects.equals(agent.getId(), customer.getAgentId()))
                        .findFirst();

                //Tenant Info
                leaseInfoDTO.setTenantId(customer.getId());
                leaseInfoDTO.setTenantName(customer.getFullName());
                leaseInfoDTO.setTenantWhatsappNumber(customer.getWhatsappNumber());

                //Property Info
                rentalPropertyOptional.ifPresent(property -> {
                    leaseInfoDTO.setPropertyId(property.getId());
                    leaseInfoDTO.setPropertyName(property.getName());
                });
//                agentOptional.ifPresent(agent -> {
//                    rentalPaymentReminderRequest.setAgentName(agent.getFullName());
//                    rentalPaymentReminderRequest.setAgentEmail(agent.getEmail());
//                    rentalPaymentReminderRequest.setAgentMobileNo(agent.getMobileNumber());
//                });

                //Rental Info
                leaseInfoDTO.setRentalAmount(customer.getRentalAmount());
                leaseInfoDTO.setRentalStartDate(dateFormatterUtil.getFormattedDate(customer.getRentalStartDate()));

                pendingRentalsThisMonthList.add(leaseInfoDTO);
            }
        }
        return pendingRentalsThisMonthList;
    }
}
