package org.emoflon.ibex.handbook.serialiser

import org.emoflon.ibex.handbook.sokobanExchangeFormat.Board
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Entry
import org.emoflon.ibex.handbook.sokobanExchangeFormat.End
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Normal

class Serialiser {
	static def String serialise(Board board) {
		var rep = ""
		var r = board.firstRow

		do {
			var e = r.firstEntry
			do {
				rep += serialise(e)
				e = e.next
			} while (e !== null)
			if(r.next !== null)
				rep += "\n"
			r = r.next
		} while (r !== null)

		'''
		Name::"«board.name»"
		Author::"«board.author»"
		
		«rep»'''
	}

	static def String serialise(Entry e) {
		switch (e) {
			case e instanceof End: {
				val end = e as End
				if(end.symbol !== null)
					return end.symbol.value.literal
				else
					return "." 
			}
			case e instanceof Normal: {
				val end = e as Normal
				if(end.symbol !== null)
					return end.symbol.value.literal
				else
					return " " 
			}
			default: {
			}
		}
	}
}
