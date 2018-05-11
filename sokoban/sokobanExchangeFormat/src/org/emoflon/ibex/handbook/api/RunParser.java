package org.emoflon.ibex.handbook.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.emoflon.ibex.handbook.SokobanExchangeFormatStandaloneSetup;
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Board;

import com.google.inject.Injector;

public class RunParser {
	private final static Injector injector = new SokobanExchangeFormatStandaloneSetup().createInjectorAndDoEMFRegistration();
	
	private URI fileURI;
	
	public RunParser(String filePath) {
		fileURI = URI.createFileURI(filePath);
	}
	
	public Optional<Board> parse() {
		// Obtain a resource set from the injector
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);

		// Load a resource by URI, in this case from the file system
		Resource resource = resourceSet.getResource(fileURI, true);

		// Validation
		IResourceValidator validator = ((XtextResource) resource).getResourceServiceProvider().getResourceValidator();
		List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
		for (Issue issue : issues) {
			System.err.println(issue.getMessage());
		}

		if (issues.isEmpty())
			return Optional.of((Board) resource.getContents().get(0));
		
		return Optional.empty();
	}
}
