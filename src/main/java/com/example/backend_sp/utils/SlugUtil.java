package com.example.backend_sp.utils;

import java.text.Normalizer;

public class SlugUtil {
    public static String createSlug(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("[^\\p{ASCII}]", "").trim().replaceAll("\\s+", "-");
        return slug.toLowerCase();
    }
}
