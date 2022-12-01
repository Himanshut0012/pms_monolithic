package com.josh.pms.dto.search.api;

import com.lowagie.text.Anchor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PagingHeaders {
    PAGE_SIZE("Page-Size"),
    PAGE_NUMBER("Page-Number"),
    PAGE_OFFSET("Page-Offset"),
    PAGE_TOTAL("Page-Total"),
    COUNT("Count");


    private final String name;
}
