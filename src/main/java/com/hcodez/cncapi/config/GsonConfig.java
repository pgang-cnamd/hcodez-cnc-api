package com.hcodez.cncapi.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.hcodez.codeengine.json.serialization.CodeTypeDeserializer;
import com.hcodez.codeengine.json.serialization.CodeTypeSerializer;
import com.hcodez.codeengine.json.serialization.InstantDeserializer;
import com.hcodez.codeengine.json.serialization.InstantSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class GsonConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(gsonHttpMessageConverter());
    }

    private GsonHttpMessageConverter gsonHttpMessageConverter() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(CodeTypeSerializer.class, new CodeTypeSerializer())
                .registerTypeAdapter(CodeTypeDeserializer.class, new CodeTypeDeserializer())
                .registerTypeAdapter(InstantSerializer.class, new InstantSerializer())
                .registerTypeAdapter(InstantDeserializer.class, new InstantDeserializer())
                .create();

        final GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(gson);

        return gsonHttpMessageConverter;
    }
}
