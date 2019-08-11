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
    public ResponseEntity<List<CodeEntity>> getCodes() {
        return new ResponseEntity<>(codeService.findCodes(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> getCodeById(@PathVariable Integer id) {
        return new ResponseEntity<>(codeService.findCodeById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> createCode(@RequestBody CodeEntity codeEntity) {
        List<CodeEntity> codeEntityList = codeService.createCode(codeEntity);

        if (codeEntityList.size() == 0)
            return new ResponseEntity<>(codeEntityList, HttpStatus.NOT_MODIFIED);
        return new ResponseEntity<>(codeEntityList, HttpStatus.OK);
    }

    @PostMapping(value = "/parse",
            consumes = "text/plain;charset=UTF-8",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> parseCodesFromString(@RequestBody String input) {
        return new ResponseEntity<>(codeService.parseCodeFromString(input), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CodeEntity>> updateCodeById(@PathVariable Integer id,
                                                           @RequestBody CodeEntity codeEntity) {
        return new ResponseEntity<>(codeService.updateCodeById(id, codeEntity), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCodeById(@PathVariable Integer id) {
        boolean success = codeService.deleteCodeById(id);

        return success ? new ResponseEntity(HttpStatus.OK) :
                new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
    }
}
