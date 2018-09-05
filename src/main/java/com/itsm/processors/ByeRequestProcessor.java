package com.itsm.processors;

import com.itsm.parse.JsonRequest;
import com.itsm.parse.JsonResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class ByeRequestProcessor implements RequestProcessor{

    @Override
    public boolean canProcess(JsonRequest req) {
        String[] greetings = {"bye","see you later"};
        //only lower case! Better to move this to .properties...
        for (String greet : greetings) {
            if (req.getMessage().toLowerCase().contains(greet)) return true;
        }
        return false;
    }

    @Override
    public JsonResponse process(JsonRequest req) {
        return new JsonResponse("Good bye, " + req.getName() + ".");
    }
}
