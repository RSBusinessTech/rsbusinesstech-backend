package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeoMetadataDTO {

    private String primaryKeyword;
    private List<String> secondaryKeywords;

    private Integer seoScore;

    private String canonicalUrl;

    private String schemaType;

    private String intentCluster;

    private List<String> contentCluster;

    // ADD THIS
    private InternalLinkingDTO internalLinking;

    private List<SeoLinkPageDTO> relatedSearchPages;

    private List<SeoInternalPageDTO> internalPages;
}