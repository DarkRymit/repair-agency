package com.epam.finalproject.config;

import com.epam.finalproject.dto.ReceiptDTO;
import com.epam.finalproject.model.entity.Receipt;
import com.epam.finalproject.model.entity.User;
import com.epam.finalproject.payload.request.SignUpRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(SignUpRequest.class, User.class).addMappings(mapper -> mapper.skip(User::setPassword));
        modelMapper.typeMap(Receipt.class, ReceiptDTO.class)
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter())
                        .map(Receipt::getCreationDate, ReceiptDTO::setCreationDate))
                .addMappings(mapper -> mapper.using(instantZonedDateTimeConverter())
                        .map(Receipt::getLastModifiedDate, ReceiptDTO::setLastModifiedDate));
        return modelMapper;
    }

    @Bean
    public Converter<Instant, ZonedDateTime> instantZonedDateTimeConverter() {
        return mappingContext -> mappingContext.getSource().atZone(ZoneId.systemDefault());
    }
}
