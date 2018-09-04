package com.itsm.processors;

import com.itsm.parse.JsonRequest;
import com.itsm.parse.JsonResponse;

public interface RequestProcessor {
    boolean canProcess(JsonRequest req);
    JsonResponse process(JsonRequest req);
}
