package com.hcodez.cncapi.code;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CodeRepository extends CrudRepository<CodeEntity, Integer> {
    List<CodeEntity> findByIdentifierAndOwner(String identifier, String owner);
}
