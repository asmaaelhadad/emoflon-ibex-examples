package org.moflon.tutorial.sokobangamegui.rules;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import SokobanLanguage.Board;
import SokobanLanguage.Field;
import SokobanLanguage.Figure;
import SokobanLanguage.Sokoban;
import SokobanRules.SokobanValidator;
import SokobanRules.api.SokobanRulesAPI;

public class SokobanRules {
	private SokobanRulesAPI api;
	private String allsWell = "Everything seems to be ok...";

	// Keep track of all currently possible moves
	private Map<Field, Supplier<Result>> possibleMoves;

	public SokobanRules(Board board) {
		api = new SokobanValidator(board).initAPI();
		possibleMoves = new HashMap<>();

		// Subscribe to appearing matches and update possible moves
		api.moveSokobanDown().subscribeAppearing(m -> register(m.getTo(), () -> api.moveSokobanDown().apply(m)));
		api.moveSokobanUp().subscribeAppearing(m -> register(m.getTo(), () -> api.moveSokobanUp().apply(m)));
		api.moveSokobanLeft().subscribeAppearing(m -> register(m.getTo(), () -> api.moveSokobanLeft().apply(m)));
		api.moveSokobanRight().subscribeAppearing(m -> register(m.getTo(), () -> api.moveSokobanRight().apply(m)));

		api.pushBlockUp().subscribeAppearing(m -> register(m.getTo(), () -> api.pushBlockUp().apply(m)));
		api.pushBlockDown().subscribeAppearing(m -> register(m.getTo(), () -> api.pushBlockDown().apply(m)));
		api.pushBlockLeft().subscribeAppearing(m -> register(m.getTo(), () -> api.pushBlockLeft().apply(m)));
		api.pushBlockRight().subscribeAppearing(m -> register(m.getTo(), () -> api.pushBlockRight().apply(m)));

		// Subscribe to disappearing matches and update possible moves
		api.moveSokobanDown().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));
		api.moveSokobanUp().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));
		api.moveSokobanLeft().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));
		api.moveSokobanRight().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));

		api.pushBlockUp().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));
		api.pushBlockDown().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));
		api.pushBlockLeft().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));
		api.pushBlockRight().subscribeDisappearing(m -> possibleMoves.remove(m.getTo()));
	}

	// Add the potential move to this target field
	private void register(Field targetField, Runnable applyRule) {
		possibleMoves.put(targetField, () -> {
			applyRule.run();
			return new Result(true, "Go Sokoban!");
		});
	}

	// If we have a suitable rule application, choose it and apply
	public Result move(Figure figure, Field field) {
		// Refuse to do anything if the chosen figure is not Sokoban
		if (!(figure instanceof Sokoban))
			return new Result(false, "You can only move Sokoban!");

		if (possibleMoves.containsKey(field))
			return possibleMoves.get(field).get();
		else
			return new Result(false, "Sokoban can't move to " + "[" + field.getRow() + "," + field.getCol() + "]");
	}

	public Result validateBoard(Board board) {
		if (api.oneSokoban().countMatches() != 1)
			return new Result(false, "You must have exactly one Sokoban!");

		if (!api.oneEndField().hasMatches())
			return new Result(false, "You must have at least one end field!");

		if (api.oneBlock().countMatches() != api.oneEndField().countMatches())
			return new Result(false, "You must have exactly as many end fields as blocks");

		if (api.boulderOnEndField().hasMatches()) {
			String occupiedFields = api.boulderOnEndField().findMatches().stream()
					.map(m -> "[" + m.getField().getRow() + "," + m.getField().getCol() + "]")
					.collect(Collectors.joining(", "));
			return new Result(false, "These end fields are blocked: " + occupiedFields);
		}

		return new Result(true, allsWell);
	}
}
