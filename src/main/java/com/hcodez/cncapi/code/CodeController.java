package com.hcodez.cncapi.code;

import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import com.hcodez.codeengine.parser.CodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeRepository codeRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Code> getCodeList() {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/parse", method = RequestMethod.POST, consumes = "application/text", produces = "application/json")
    public List<String> parseInputString(@RequestBody String input) {
        final List<Code> codeList = new CodeParser()
                .addCodeTypes(CodeType.all())
                .parseString(input);

        final List<String> jsonStringList = new ArrayList<>();
        for (Code code : codeList) {
            jsonStringList.add(code.toJson());
        }

        return jsonStringList;
    }
}
