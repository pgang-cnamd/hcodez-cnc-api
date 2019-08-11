package com.hcodez.cncapi.code;

import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;


    public List<CodeEntity> findCodes() { // FIXME: 2019-08-11 method must actually return all of a user's codes
        return (List<CodeEntity>) codeRepository.findAll();
    }

    public List<CodeEntity> findCodeById(@Nonnull Integer id) {
        final Optional<CodeEntity> result = codeRepository.findById(id);
        final List<CodeEntity> list = new ArrayList<>();

        result.ifPresent(list::add);
        return list;
    }

    public CodeEntity createCode(@Nonnull CodeEntity codeEntity) {
        codeEntity.setCreateTime(Instant.now());
        codeEntity.setUpdateTime(Instant.now());

        final CodeEntity newCodeEntity = codeRepository.save(codeEntity);
        try {
            newCodeEntity.setUrl(new URL("http://localhost:8080/code/" +
                    newCodeEntity.getId()));
        } catch (MalformedURLException e) {
            return null;
        }
        return newCodeEntity;
    }

    public List<CodeEntity> updateCodeById(@Nonnull Integer id, @Nonnull CodeEntity codeEntity) {
        final List<CodeEntity> list = new ArrayList<>();

        if (!codeRepository.existsById(id)) return list;

        CodeEntity toBeUpdated = codeRepository.findById(id).get();

        if (!toBeUpdated.getId().equals(codeEntity.getId())) return list;

        toBeUpdated.setIdentifier(codeEntity.getIdentifier());
        toBeUpdated.setPasscode(codeEntity.getPasscode());
        toBeUpdated.setCodeType(codeEntity.getCodeType());
        toBeUpdated.setUpdateTime(Instant.now());
        toBeUpdated.setContentId(codeEntity.getContentId());
        toBeUpdated.setName(codeEntity.getName());

        CodeEntity updatedCodeEntity = codeRepository.save(toBeUpdated);
        list.add(updatedCodeEntity);

        return list;
    }

    public boolean deleteCodeById(@Nonnull Integer id) {
        if (!codeRepository.existsById(id)) return false;
        codeRepository.deleteById(id);
        return !codeRepository.existsById(id);
    }
}
