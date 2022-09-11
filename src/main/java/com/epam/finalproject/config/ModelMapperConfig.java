package com.epam.finalproject.config;

import com.epam.finalproject.currency.context.CurrencyUnitContextHolder;
import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.dto.RepairCategoryDTO;
import com.epam.finalproject.dto.RepairWorkDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.model.entity.*;
import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.repository.RepairCategoryRepository;
import com.epam.finalproject.repository.RepairWorkRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Supplier;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(ModelMapperParameters parameters) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(SignUpRequest.class, User.class).addMappings(mapper -> mapper.skip(User::setPassword));
        modelMapper.typeMap(RepairWork.class, RepairWorkDTO.class)
                .addMappings(mapper -> mapper.using(
                                localNameWorkConverter(parameters.getRepairWorkRepository(), parameters.getLocaleSupplier()))
                        .map(RepairWork::getId, RepairWorkDTO::setName))
                .addMappings(mapper -> mapper.using(lowerBorderWorkConverter(parameters.getRepairWorkRepository(),
                                parameters.getCurrencyUnitSupplier()))
                        .map(RepairWork::getId, RepairWorkDTO::setLowerBorder))
                .addMappings(mapper -> mapper.using(upperBorderBorderWorkConverter(parameters.getRepairWorkRepository(),
                                parameters.getCurrencyUnitSupplier()))
                        .map(RepairWork::getId, RepairWorkDTO::setUpperBorder));

        modelMapper.typeMap(RepairCategory.class, RepairCategoryDTO.class)
                .addMappings(mapper -> mapper.using(localNameCategoryConverter(parameters.getRepairCategoryRepository(),
                                parameters.getLocaleSupplier()))
                        .map(RepairCategory::getId, RepairCategoryDTO::setName));
        modelMapper.typeMap(Receipt.class, ReceiptDTO.class)
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter(parameters.getTimeZoneSupplier()))
                        .map(Receipt::getCreationDate, ReceiptDTO::setCreationDate))
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter(parameters.getTimeZoneSupplier()))
                        .map(Receipt::getLastModifiedDate, ReceiptDTO::setLastModifiedDate));
        modelMapper.typeMap(User.class, UserDTO.class)
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter(parameters.getTimeZoneSupplier()))
                        .map(User::getCreationDate, UserDTO::setCreationDate))
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter(parameters.getTimeZoneSupplier()))
                        .map(User::getLastModifiedDate, UserDTO::setLastModifiedDate));
        return modelMapper;
    }

    @Bean
    public ModelMapperParameters modelMapperParameters(RepairWorkRepository repairWorkLocalPartRepository,
            RepairCategoryRepository repairCategoryLocalPartRepository) {
        return new ModelMapperParameters(currentLocale(), currentTimeZone(), currentCurrencyUnit(),
                repairWorkLocalPartRepository, repairCategoryLocalPartRepository);
    }


    private Converter<Instant, ZonedDateTime> instantZonedDateTimeConverter(Supplier<TimeZone> timeZoneSupplier) {
        return mappingContext -> mappingContext.getSource().atZone(timeZoneSupplier.get().toZoneId());
    }

    private Converter<Long, String> localNameCategoryConverter(RepairCategoryRepository repository,
            Supplier<Locale> localeSupplier) {
        return mappingContext -> Optional.ofNullable(mappingContext.getSource())
                .map(s -> repository.findFirstLocalPartByCategory_IdAndLanguage_Lang(s,
                                localeSupplier.get().getLanguage())
                        .orElseThrow()
                        .getName())
                .orElse(null);
    }

    private Converter<Long, String> localNameWorkConverter(RepairWorkRepository repository,
            Supplier<Locale> localeSupplier) {
        return mappingContext -> Optional.ofNullable(mappingContext.getSource())
                .map(s -> repository.findLocalByWork_IdAndLanguage_Lang(s, localeSupplier.get().getLanguage())
                        .orElseThrow(()-> new NoSuchElementException(String.format("Not found for %s",localeSupplier.get().getLanguage())))
                        .getName())
                .orElse(null);
    }

    private Converter<Long, BigDecimal> lowerBorderWorkConverter(RepairWorkRepository repository,
            Supplier<CurrencyUnit> currencyUnitSupplier) {
        return mappingContext -> Optional.ofNullable(mappingContext.getSource())
                .map(s -> repository.findPriceByWork_IdAndCurrency_Code(s, currencyUnitSupplier.get().getCurrencyCode())
                        .orElseThrow()
                        .getLowerBorder())
                .orElse(null);
    }

    private Converter<Long, BigDecimal> upperBorderBorderWorkConverter(RepairWorkRepository repository,
            Supplier<CurrencyUnit> currencyUnitSupplier) {
        return mappingContext -> Optional.ofNullable(mappingContext.getSource())
                .map(s -> repository.findPriceByWork_IdAndCurrency_Code(s, currencyUnitSupplier.get().getCurrencyCode())
                        .orElseThrow()
                        .getUpperBorder())
                .orElse(null);
    }

    private Supplier<Locale> currentLocale() {
        return LocaleContextHolder::getLocale;
    }

    private Supplier<CurrencyUnit> currentCurrencyUnit() {
        return CurrencyUnitContextHolder::getCurrencyUnit;
    }

    private Supplier<TimeZone> currentTimeZone() {
        return LocaleContextHolder::getTimeZone;
    }

}
