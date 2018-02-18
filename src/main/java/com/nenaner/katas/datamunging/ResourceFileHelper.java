package com.nenaner.katas.datamunging;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class ResourceFileHelper {
    public Stream<String> getResourceFileAsInputStream(String fileName) throws URISyntaxException, IOException {
        URI uri = this.getClass().getClassLoader().getResource(fileName).toURI();
        Path path = Paths.get(uri);
        return Files.lines(path);
    }
}
