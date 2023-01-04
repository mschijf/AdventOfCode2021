package com.adventofcode.december24

import com.adventofcode.PuzzleSolverAbstract
import java.lang.Long.max
import java.lang.String.format

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {
    val actionList = input.inputLines.map {Action(it)}

    override fun resultPartOne(): String {
        val alu = Alu("12345678912345")
        actionList.forEach {
            alu.doAction(it)
            println(alu)
        }
        return "WIP"
    }
}

//----------------------------------------------------------------------------------------------------------------------

class Alu (
    val inputList: String) {
    val variableList = mutableListOf(0L,0L,0L,0L)
    var inputIndex = 0

    fun doAction(action: Action) {
        val variableIndex = action.variable - 'w'
        val operand2Value = if (action.operand2 in "wxyz") variableList[action.operand2[0] - 'w'] else action.operand2.toLong()
        when (action.instruction) {
            "inp" -> variableList[variableIndex] = inputList[inputIndex++].digitToInt().toLong()
            "add" -> variableList[variableIndex] += operand2Value
            "mul" -> variableList[variableIndex] *= operand2Value
            "div" -> variableList[variableIndex] /= operand2Value
            "mod" -> variableList[variableIndex] %= operand2Value
            "eql" -> variableList[variableIndex] = if (variableList[variableIndex] == operand2Value) 1 else 0
            else -> throw Exception("Unexpected instruction")
        }
    }

    override fun toString() = "$variableList"
}

class Action(inputStr: String) {
    val instruction = inputStr.substring(0, 3)
    val variable = inputStr[4]
    val operand2 = if (instruction != "inp") inputStr.substring(6) else "0"
}
