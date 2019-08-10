package com.hcodez.cncapi.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.List;

@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    public List<CodeEntity> findByIdentifierAndOwner(@Nonnull String identifier,
                                                     @Nonnull String owner) {
        return codeRepository.findByIdentifierAndOwner(identifier, owner);
    }
}
