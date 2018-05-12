package SokobanRules;

import java.io.File;

import org.eclipse.emf.common.util.URI;

import SokobanLanguage.Board;
import SokobanRules.api.SokobanRulesDemoclesApp;

public class SokobanValidator extends SokobanRulesDemoclesApp {
	public SokobanValidator(Board board) {
		File root = new File(SokobanValidator.class.getResource(".").getFile());
		workspacePath = root.getParentFile().getParentFile().getParent() + File.separatorChar;

		if(board.eResource() == null) {
			createModel(URI.createURI("board.xmi"));
			resourceSet.getResources().get(0).getContents().add(board);
		} else {
			resourceSet = board.eResource().getResourceSet();
		}
	}
}