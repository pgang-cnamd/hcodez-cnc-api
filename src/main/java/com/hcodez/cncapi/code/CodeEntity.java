package com.hcodez.cncapi.code;

import com.hcodez.codeengine.builder.CodeBuilder;
import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import lombok.*;
import org.joda.time.Instant;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.net.URL;

@Component
@Data
@NoArgsConstructor
@Builder
@Entity
public class CodeEntity {

    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cloudId;

    @Getter
    @Setter
    private String identifier;

    @Getter
    @Setter
    private String owner;

    @Getter
    @Setter
    private String passcode;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private URL url;

    @Getter
    @Setter
    private Instant createTime;

    @Getter
    @Setter
    private Instant updateTime;

    @Getter
    @Setter
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
