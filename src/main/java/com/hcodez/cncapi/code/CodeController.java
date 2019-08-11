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


    @GetMapping
    public ResponseEntity<List<Code>> getCodes() {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> getCodeById(@PathVariable Integer id) {
        return new ResponseEntity<>(codeService.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> createCode(@RequestBody CodeEntity codeEntity) {
        List<CodeEntity> codeEntityList = new ArrayList<>();
        codeEntityList.add(codeService.saveNew(codeEntity));

        return new ResponseEntity<>(codeEntityList, HttpStatus.OK);
    }

    @PostMapping(value = "/parse",
            consumes = "text/plain;charset=UTF-8",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> parseCodesFromString(@RequestBody String input) {
        final List<Code> codeList = new CodeParser()
                .addCodeTypes(CodeType.all())
                .parseString(input);

        final List<CodeEntity> codeEntityList = new ArrayList<>();
        for (Code code : codeList) {
            codeEntityList.add(CodeEntity.fromLibraryCode(code));
        }

        return new ResponseEntity<>(codeEntityList, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> updateCodeById(@PathVariable Integer id,
                                                           @RequestBody CodeEntity codeEntity) {
        return new ResponseEntity<>(new ArrayList<CodeEntity>(), HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCodeById(@PathVariable Integer id) {
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }
}
