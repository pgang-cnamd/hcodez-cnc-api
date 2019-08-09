package com.hcodez.cncapi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hcodez.codeengine.json.serialization.CodeTypeDeserializer;
import com.hcodez.codeengine.json.serialization.CodeTypeSerializer;
import com.hcodez.codeengine.json.serialization.InstantDeserializer;
import com.hcodez.codeengine.json.serialization.InstantSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(CodeTypeSerializer.class, new CodeTypeSerializer())
                .registerTypeAdapter(CodeTypeDeserializer.class, new CodeTypeDeserializer())
                .registerTypeAdapter(InstantSerializer.class, new InstantSerializer())
                .registerTypeAdapter(InstantDeserializer.class, new InstantDeserializer())
                .create();
    }
}
