package com.hcodez.cncapi.code;

import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    public CodeEntity createCode(@Nonnull CodeEntity codeEntity) {
        codeEntity.setCreateTime(Instant.now());
        codeEntity.setUpdateTime(Instant.now());
        return codeRepository.save(codeEntity);
    }

    public List<CodeEntity> findCodeById(@Nonnull Integer id) {
        Optional<CodeEntity> result = codeRepository.findById(id);
        List<CodeEntity> list = new ArrayList<>();

        result.ifPresent(list::add);
        return list;
    }
}
