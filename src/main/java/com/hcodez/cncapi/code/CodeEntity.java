package com.hcodez.cncapi.code;

import com.hcodez.codeengine.builder.CodeBuilder;
import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import lombok.Builder;
import lombok.Data;
import org.joda.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URL;

@Data
@Builder
@Entity
public class CodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cloudId;

    private String identifier;

    private String owner;

    private String passcode;

    private String name;

    private URL url;

    private Instant createTime;

    private Instant updateTime;

    private CodeType codeType;

    /**
     * Create a library Code model from a CodeEntity
     * @return the Code model built
     */
    public Code toLibraryCode() {
        return CodeBuilder.createBuilder()
                .withIdentifier(this.getIdentifier())
                .withOwner(this.getOwner())
                .withPasscode(this.getPasscode())
                .withName(this.getName())
                .withUrl(this.getUrl())
                .withCreateTime(this.getCreateTime())
                .withUpdateTime(this.getUpdateTime())
                .withCodeType(this.getCodeType())
                .build();
    }

    public CodeEntity fromLibraryCode(Code libraryCode) {
        return CodeEntity.builder()
                .identifier(libraryCode.getIdentifier())
                .owner(libraryCode.getOwner())
                .passcode(libraryCode.getPasscode())
                .codeType(libraryCode.getCodeType())
                .name(libraryCode.getName())
                .createTime(libraryCode.getCreateTime())
                .updateTime(libraryCode.getUpdateTime())
                .url(libraryCode.getUrl())
                .build();
    }
}
