package com.example.tech_titans_app.ui.api;

import java.util.HashMap;
import java.util.Map;

public class PatchReqBody {
    private final Map<String, String> updateParams;

    public PatchReqBody(String param, String value) {
        updateParams = new HashMap<>();
        updateParams.put(param, value);
    }

    public Map<String, String> getUpdateParams() {
        return updateParams;
    }
}
