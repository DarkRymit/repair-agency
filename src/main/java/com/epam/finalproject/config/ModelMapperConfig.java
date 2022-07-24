package com.epam.finalproject.config;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.repository.RepairCategoryLocalPartRepository;
import com.epam.finalproject.repository.RepairWorkLocalPartRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Supplier;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(ModelMapperParameters parameters) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(SignUpRequest.class, User.class).addMappings(mapper -> mapper.skip(User::setPassword));
        modelMapper.typeMap(Receipt.class, ReceiptDTO.class)
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter(parameters.getTimeZoneSupplier()))
                        .map(Receipt::getCreationDate, ReceiptDTO::setCreationDate))
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter(parameters.getTimeZoneSupplier()))
                        .map(Receipt::getLastModifiedDate, ReceiptDTO::setLastModifiedDate));
        modelMapper.typeMap(RepairWork.class, RepairWorkDTO.class)
                .setPostConverter(parameters.getRepairWorkRepairWorkDTOPostConverter());
        modelMapper.typeMap(RepairCategory.class, RepairCategoryDTO.class)
                .setPostConverter(parameters.getRepairCategoryRepairCategoryDTOPostConverter());
        return modelMapper;
    }

    @Bean
    public ModelMapperParameters modelMapperParameters(RepairWorkLocalPartRepository repairWorkLocalPartRepository, RepairCategoryLocalPartRepository repairCategoryLocalPartRepository){
        return ModelMapperParameters.builder()
                .localeSupplier(currentLocale())
                .timeZoneSupplier(currentTimeZone())
                .repairWorkRepairWorkDTOPostConverter(repairWorkRepairWorkDTOPostConverter(repairWorkLocalPartRepository,currentLocale()))
                .repairCategoryRepairCategoryDTOPostConverter(repairCategoryRepairCategoryDTOPostConverter(repairCategoryLocalPartRepository,currentLocale()))
                .build();
    }


    private Converter<Instant, ZonedDateTime> instantZonedDateTimeConverter(Supplier<TimeZone> timeZoneSupplier) {
        return mappingContext -> mappingContext.getSource().atZone(timeZoneSupplier.get().toZoneId());
    }

    private Converter<RepairWork, RepairWorkDTO> repairWorkRepairWorkDTOPostConverter(RepairWorkLocalPartRepository repository,Supplier<Locale> localeSupplier) {
        return mappingContext -> {
            RepairWork repairWork = mappingContext.getSource();
            RepairWorkDTO result = mappingContext.getDestination();
            Locale locale = localeSupplier.get();
            RepairWorkLocalPart localPart = repository.findByWork_IdAndLanguage_Lang(repairWork.getId(), locale.getLanguage())
                    .orElseThrow();
            result.setName(localPart.getName());
            return result;
        };
    }

    private Converter<RepairCategory, RepairCategoryDTO> repairCategoryRepairCategoryDTOPostConverter(RepairCategoryLocalPartRepository repository,Supplier<Locale> localeSupplier) {
        return mappingContext -> {
            RepairCategory repairCategory = mappingContext.getSource();
            RepairCategoryDTO result = mappingContext.getDestination();
            Locale locale = localeSupplier.get();
            RepairCategoryLocalPart localPart = repository.findByCategory_IdAndLanguage_Lang(repairCategory.getId(), locale.getLanguage())
                    .orElseThrow();
            result.setName(localPart.getName());
            return result;
        };
    }


    private Supplier<Locale> currentLocale() {
        return LocaleContextHolder::getLocale;
    }


    private Supplier<TimeZone> currentTimeZone() {
        return LocaleContextHolder::getTimeZone;
    }

}
