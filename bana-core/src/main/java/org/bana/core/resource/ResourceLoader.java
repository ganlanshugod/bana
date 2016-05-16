package org.bana.core.resource;

import java.io.IOException;

import org.bana.core.exception.BanaCoreException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class ResourceLoader {
	public static Resource[] getResource(String partern) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			Resource[] resources = resolver.getResources(partern);
			return resources;
		} catch (IOException e) {
			throw new BanaCoreException(e);
		}

	}

	public static void main(String[] args) {
		Resource[] resource = getResource("classpath*:/**/*.vm");
		for (Resource resource2 : resource)
			System.out.println(resource2.getFilename());
	}
}