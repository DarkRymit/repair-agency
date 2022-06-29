package com.epam.finalproject.config;

import com.epam.finalproject.payload.request.SignUpRequest;
import com.epam.finalproject.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(SignUpRequest.class, User.class).addMappings(mapper -> mapper.skip(User::setPassword));
        return new ModelMapper();
    }
}
