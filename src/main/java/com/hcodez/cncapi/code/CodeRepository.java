package com.hcodez.cncapi.code;

import com.hcodez.codeengine.model.CodeType;
import org.springframework.data.repository.CrudRepository;

public interface CodeRepository extends CrudRepository<CodeEntity, Integer> {
    CodeEntity findCodeEntityByIdentifierAndOwnerAndPasscodeAndCodeType(String identifier,
                                                                        String owner,
                                                                        String passcode,
                                                                        CodeType codeType);
}
