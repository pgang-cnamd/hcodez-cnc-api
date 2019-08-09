package com.hcodez.cncapi.code;

import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import com.hcodez.codeengine.parser.CodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/parse",
            method = RequestMethod.POST,
            consumes = "application/text",
            produces = "application/json")
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

    @RequestMapping(method = RequestMethod.GET,
            consumes = "application/json")
    public List<CodeEntity> getByIdentifierAndOwner(@RequestParam String identifier, @RequestParam String owner) {
        return codeRepository.findByIdentifierAndOwner(identifier, owner);
    }
}
