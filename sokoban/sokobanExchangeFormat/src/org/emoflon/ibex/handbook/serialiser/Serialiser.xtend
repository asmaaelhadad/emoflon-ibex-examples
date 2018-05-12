package org.emoflon.ibex.handbook.serialiser

import org.emoflon.ibex.handbook.sokobanExchangeFormat.Board
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Entry
import org.emoflon.ibex.handbook.sokobanExchangeFormat.End
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Normal
import org.emoflon.ibex.handbook.sokobanExchangeFormat.Row

class Serialiser {
	static def String serialise(Board board) {
		'''
		Name::"«board.name»"
		Author::"«board.author»"
		
		«serialise(board.firstRow)»'''
	}

	static def String serialise(Row r) {
		'''
		«serialise(r.firstEntry)»«IF (r.next !== null)»
		«serialise(r.next)»«ENDIF»'''
	}

	static def String serialise(Entry e) {
		'''«handleEntry(e)»«IF (e.next !== null)»«serialise(e.next)»«ENDIF»'''
	}
	
	static def String handleEntry(Entry e){
		'''«IF (e instanceof End)»«serialiseEnd(e)»«ELSEIF (e instanceof Normal)»«serialiseNormal(e)»«ENDIF»'''
	}
	
	static def String serialiseEnd(End e){
		'''«IF(e.symbol !== null)»«e.symbol.value.literal»«ELSE».«ENDIF»'''
	}
	
	static def String serialiseNormal(Normal e){
		'''«IF(e.symbol !== null)»«e.symbol.value.literal»«ELSE» «ENDIF»'''
	}
}
