package org.crayzer.design.design_mode_pattern.behavioural.visitor.refactor.v3;

import java.util.HashMap;
import java.util.Map;

public class ExtractorFactory {
    private static final Map<ResourceFileType, Extractor> extractors = new HashMap<>();
    static {
        extractors.put(ResourceFileType.PDF, new PdfExtractor());
        extractors.put(ResourceFileType.PPT, new PPTExtractor());
        extractors.put(ResourceFileType.WORD, new WordExtractor());
    }

    public static Extractor getExtractor(ResourceFileType type) {
        return extractors.get(type);
    }
}
