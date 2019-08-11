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

    public List<CodeEntity> updateCodeById(@Nonnull Integer id) {
        return new ArrayList<>();
    }

    public boolean deleteCodeById(@Nonnull Integer id) {
        return false;
    }
}
