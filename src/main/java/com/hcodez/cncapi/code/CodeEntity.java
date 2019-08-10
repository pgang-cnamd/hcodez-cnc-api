package com.hcodez.cncapi.code;

import com.google.gson.annotations.SerializedName;
import com.hcodez.codeengine.builder.CodeBuilder;
import com.hcodez.codeengine.model.Code;
import com.hcodez.codeengine.model.CodeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.Instant;

import javax.persistence.*;
import java.net.URL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "codes")
public class CodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("cloud_id")
    @Column(name="id")
    private Integer id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "owner")
    private String owner;

    @Column(name = "passcode")
    private String passcode;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private URL url;

    @SerializedName("create_time")
    @Column(name = "create_time")
    private Instant createTime;

    @SerializedName("update_time")
    @Column(name = "update_time")
    private Instant updateTime;

    @SerializedName("code_type")
    @Column(name = "code_type")
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

    public static CodeEntity fromLibraryCode(Code libraryCode) {
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
