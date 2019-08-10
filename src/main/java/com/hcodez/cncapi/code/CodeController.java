package com.hcodez.cncapi.code;

import com.google.gson.Gson;
import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import com.hcodez.codeengine.parser.CodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("codeController")
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Code> getCodeList() {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/parse",
            method = RequestMethod.POST,
            consumes = "text/plain;charset=UTF-8",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> parseInputString(@RequestBody String input) {
        final List<Code> codeList = new CodeParser()
                .addCodeTypes(CodeType.all())
                .parseString(input);

        final List<CodeEntity> codeEntityList = new ArrayList<>();
        for (Code code : codeList) {
            codeEntityList.add(CodeEntity.fromLibraryCode(code));
        }

        return new ResponseEntity<List<CodeEntity>>(codeEntityList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeEntity> getByIdentifierAndOwner(@RequestParam String identifier, @RequestParam String owner) {
        return codeService.findByIdentifierAndOwner(identifier, owner);
    }
}
