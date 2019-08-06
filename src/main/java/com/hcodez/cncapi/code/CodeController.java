package com.hcodez.cncapi.code;

import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import com.hcodez.codeengine.parser.CodeParser;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {

    @RequestMapping(method = RequestMethod.GET)
    public List<Code> getCodeList() {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST, consumes = "application/text", produces = "application/json")
    public List<Code> getCode(@RequestBody String input) {
        return new CodeParser()
                .addCodeTypes(CodeType.all())
                .parseString(input);
    }
}
