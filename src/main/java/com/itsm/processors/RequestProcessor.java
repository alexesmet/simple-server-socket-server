package com.itsm.processors;

import com.itsm.parse.Request;
import com.itsm.parse.Response;

public interface RequestProcessor {
    public boolean canProcess(Request req);
    public Response process(Request req);
}
