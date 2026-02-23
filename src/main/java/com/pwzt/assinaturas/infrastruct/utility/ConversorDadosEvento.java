package com.pwzt.assinaturas.infrastruct.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwzt.assinaturas.infrastruct.dto.DadosEventoDTO;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConversorDadosEvento implements AttributeConverter<DadosEventoDTO, String> {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public String convertToDatabaseColumn(DadosEventoDTO dadosEventoDto){
        try{
            return objectMapper.writeValueAsString(dadosEventoDto);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public DadosEventoDTO convertToEntityAttribute(String bdJson){
        try {
            return objectMapper.readValue(bdJson, DadosEventoDTO.class);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

}
