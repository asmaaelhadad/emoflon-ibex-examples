package org.emoflon.ibex.handbook.api;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.emoflon.ibex.handbook.serialiser.Serialiser;
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Board;

public class RunSerialiser {
	public void unparse(String filePath, Board board) {
		try {
			File file = new File(filePath);
			FileWriter fileWriter;
			fileWriter = new FileWriter(file);
			fileWriter.write(Serialiser.serialise(board));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
