package com.kb.saving.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SavingTopRequestDTO {
    private String investType;
    private int finCategoryId;
}
