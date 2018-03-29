package org.emoflon.tutorial.sokobanrules;

import org.eclipse.emf.common.util.URI;
import org.emoflon.ibex.gt.democles.runtime.DemoclesGTEngine;
import org.emoflon.tutorial.sokobanrules.api.SokobanrulesAPI;
import org.emoflon.tutorial.sokobanrules.api.SokobanrulesApp;

import SokobanLanguage.Board;

public class SokobanValidator extends SokobanrulesApp {

	public SokobanValidator(Board board) {
		createModel(URI.createURI("board.xmi"));
		resourceSet.getResources().get(0).getContents().add(board);
	}

	public SokobanrulesAPI getAPI() {
		return initAPI(new DemoclesGTEngine());
	}

}