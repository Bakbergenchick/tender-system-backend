package com.diplomaproject.tendersystembackend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenderDTO {
    private String tenderName;
    private String description;
    private Long categoryID;

}
