package com.kb.gold.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoldResponseDto {

    @JsonProperty("OutBlock_1")
    private List<GoldDataDto> outBlock1;
}
