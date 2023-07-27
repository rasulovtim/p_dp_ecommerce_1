package com.gitlab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import org.springframework.context.event.EventListener;

import java.io.*;

import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

@Configuration
public class OpenApiDocsFileGenerator {

    @EventListener
    public void generateOpenApiDocs(ContextRefreshedEvent event) {
        try (ReadableByteChannel readableByteChannel = Channels.newChannel(
                new URL("http://localhost:8080/v2/api-docs").openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("openApi.json")) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}