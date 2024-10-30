package com.kb.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kb.exchange.service.CustomDoubleDeserializer;
import lombok.Data;

@Data
public class ExchangeApiResponse {

    @JsonProperty("cur_unit")
    private String curUnit;     // "cur_unit"

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @JsonProperty("ttb")
    private Double ttb;         // "ttb"

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @JsonProperty("tts")
    private Double tts;         // "tts"

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @JsonProperty("deal_bas_r")
    private Double dealBasR;    // "deal_bas_r"

}
