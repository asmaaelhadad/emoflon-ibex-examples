package org.emoflon.ibex.handbook.preprocessing;

import java.io.File;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emoflon.ibex.handbook.preprocessing.api.PreprocessingAPI;
import org.emoflon.ibex.handbook.preprocessing.api.PreprocessingDemoclesApp;

public class Preprocessor extends PreprocessingDemoclesApp {

	public Preprocessor(ResourceSet resourceSet) {
		File root = new File(Preprocessor.class.getResource(".").getFile());
		workspacePath = root.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile()
				.getParentFile().getParent() + File.separatorChar;
		workspacePath = workspacePath.replace("%20", " ");
		this.resourceSet = resourceSet;
	}

	public void preprocess() {
		PreprocessingAPI api = initAPI();
		api.completeFirstRow().enableAutoApply();
		api.completeAllOtherRows().enableAutoApply();
		api.init().apply();
	}
}
