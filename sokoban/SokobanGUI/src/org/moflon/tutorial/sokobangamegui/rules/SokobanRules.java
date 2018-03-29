package org.moflon.tutorial.sokobangamegui.rules;

import SokobanLanguage.Board;
import SokobanLanguage.Field;
import SokobanLanguage.Figure;

public class SokobanRules {
	private String allsWell = "Everything seems to be ok...";

	public SokobanRules(Board board) {

	}

	public Result move(Figure figure, Field field) {
		// FIXME: Only move if it makes sense
		field.setFigure(figure);
		return new Result(true, allsWell);
	}

	public Result validateBoard(Board board) {
		// FIXME: Check if the board is valid
		return new Result(true, allsWell);
	}
}
