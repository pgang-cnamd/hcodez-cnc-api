package com.hcodez.cncapi.code;

import com.hcodez.cncapi.user.UserService;
import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import com.hcodez.codeengine.parser.CodeParser;
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

    private static final String PROTOCOL = "http";
    private static final String HOSTNAME = "localhost";
    private static final String PORT     = ":8080";

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private UserService userService;


    public List<CodeEntity> findCodes() { // FIXME: 2019-08-11 method must actually return all of a user's codes
        return (List<CodeEntity>) codeRepository.findAll();
    }

    public List<CodeEntity> findCodeById(@Nonnull Integer id) {
        final Optional<CodeEntity> result = codeRepository.findById(id);
        final List<CodeEntity> list = new ArrayList<>();

        result.ifPresent(list::add);
        return list;
    }

    public List<CodeEntity> createCode(@Nonnull CodeEntity codeEntity, String jwt) {
        final List<CodeEntity> list = new ArrayList<>();

        String owner = userService.getOwner(jwt).orElse(null);
        if (owner == null) return list;

        codeEntity.setCreateTime(Instant.now());
        codeEntity.setUpdateTime(Instant.now());
        codeEntity.setOwner(owner);

        CodeEntity newCodeEntity = codeRepository.save(codeEntity);
        newCodeEntity = setUrlForCodeEntity(newCodeEntity);
        list.add(newCodeEntity);

        return list;
    }

    public List<CodeEntity> parseCodesFromString(@Nonnull String input) {
        final List<Code> codeList = new CodeParser()
                .addCodeTypes(CodeType.all())
                .parseString(input);

        final List<CodeEntity> codeEntityList = new ArrayList<>();
        for (Code code : codeList) {
            CodeEntity codeEntity = codeRepository
                    .findCodeEntityByIdentifierAndOwnerAndPasscodeAndCodeType(
                            code.getIdentifier(),
                            code.getOwner(),
                            code.getPasscode(),
                            code.getCodeType());
            if (codeEntity != null) codeEntityList.add(codeEntity);
        }

        return codeEntityList;
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
        return list;
    }

    public boolean deleteCodeById(@Nonnull Integer id) {
        if (!codeRepository.existsById(id)) return false;
        codeRepository.deleteById(id);
        return !codeRepository.existsById(id);
    }

    private URL getUrlForId(Integer id) throws MalformedURLException {
        if (id == null) {
            return null;
        }
        return new URL(
                PROTOCOL +
                        "://" +
                        HOSTNAME +
                        PORT +
                        "/code/" +
                        id.toString());
    }

    private CodeEntity setUrlForCodeEntity(CodeEntity codeEntity) {
        if (codeEntity == null) {
            return null;
        }

        URL codeEntityUrl = null;
        try {
            codeEntityUrl = getUrlForId(codeEntity.getId());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (codeEntityUrl == null) {
            return codeEntity;
        }

        codeEntity.setUrl(codeEntityUrl);

        return codeRepository.save(codeEntity);
    }

    private List<CodeEntity> setUrlForCodeEntities(List<CodeEntity> codeEntities) {
        if (codeEntities.size() == 0) {
            return codeEntities;
        }

        List<CodeEntity> newList = new ArrayList<>();

        for (CodeEntity codeEntity: codeEntities) {
            codeEntity = setUrlForCodeEntity(codeEntity);
            newList.add(codeEntity);
        }
        return newList;
    }
}
