package com.example.pethospitalbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InspectionCaseDTO {
    
    Long inspection_item_id;
    String inspection_result_text;
    List<String> inspection_graphs;
    
}
