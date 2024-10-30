package com.kb.gold.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class GoldParam {
    private int page;
    private int limit;
    private int offset;
}
