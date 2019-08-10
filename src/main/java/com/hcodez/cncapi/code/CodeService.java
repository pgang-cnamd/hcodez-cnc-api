package com.hcodez.cncapi.code;

import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import com.hcodez.codeengine.parser.CodeParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import org.joda.time.Instant;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    List<CodeEntity> findByTextForm(@Nonnull String textForm) {
        List<CodeEntity> list = new ArrayList<>();

        Code dummyCode = new CodeParser().addCodeTypes(CodeType.all()).parseSingle(textForm);
        if (dummyCode == null) return list;

        CodeEntity result = codeRepository
                .findCodeEntityByIdentifierAndOwnerAndPasscodeAndCodeType(dummyCode.getIdentifier(),
                        dummyCode.getOwner(),
                        dummyCode.getPasscode(),
                        dummyCode.getCodeType());
        if (result != null) list.add(result);

        return list;
    }

    CodeEntity saveNew(@Nonnull CodeEntity codeEntity) {
        codeEntity.setCreateTime(Instant.now());
        codeEntity.setUpdateTime(Instant.now());
        return codeRepository.save(codeEntity);
    }

    public List<CodeEntity> findById(@Nonnull Integer id) {
        Optional<CodeEntity> result = codeRepository.findById(id);
        List<CodeEntity> list = new ArrayList<>();

        result.ifPresent(list::add);
        return list;
    }
}
