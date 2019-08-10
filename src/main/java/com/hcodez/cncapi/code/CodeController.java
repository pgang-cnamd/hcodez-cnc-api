package com.hcodez.cncapi.code;

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

        return new ResponseEntity<>(codeEntityList, HttpStatus.OK);
    }

    @RequestMapping(value = "/textForm/{textForm}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeEntity> getByTextForm(@PathVariable String textForm) {
        return codeService.findByTextForm(textForm);
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> createNewCode(@RequestBody CodeEntity codeEntity) {

        List<CodeEntity> codeEntityList = new ArrayList<>();
        codeEntityList.add(codeService.saveNew(codeEntity));

        return new ResponseEntity<>(codeEntityList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(codeService.findById(id), HttpStatus.OK);
    }
}
