package com.adventofcode.december19

import com.adventofcode.PuzzleSolverAbstract
import com.adventofcode.mylambdas.splitByCondition
import tool.coordinate.threedimensional.Point3D
import tool.mylambdas.collectioncombination.mapCombinedItems

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    private val scannerList = input.inputLines.splitByCondition { it.isBlank() }.map{Scanner.of(it)}

    override fun resultPartOne(): String {
        return getTransformedAndTransposedScannerList()
            .flatMap {it.beaconPositions }
            .distinct()
            .size.toString()
    }

    override fun resultPartTwo(): String {
        return getTransformedAndTransposedScannerList()
            .mapCombinedItems { scanner1, scanner2 ->  scanner1.scannerPosition!!.distanceTo(scanner2.scannerPosition!!)}
            .max()
            .toString()
    }

    private var lazyResult: List<Scanner>? = null
    private fun getTransformedAndTransposedScannerList(): List<Scanner> {
        if (lazyResult != null)
            return lazyResult!!
        val scanner0 = scannerList.first()
        val todo = scannerList.drop(1).toMutableSet()
        val transformedScannerList = mutableListOf(Scanner(scanner0.id, scanner0.beaconPositions, Point3D(0,0,0)))
        transformedScannerList.addAll(todo.mapNotNull { scanner0.transformAndTranspose(it) })
        while (todo.isNotEmpty()) {
            todo.toList().forEach { scanner ->
                for (transformedScanner in transformedScannerList) {
                    val result = transformedScanner.transformAndTranspose(scanner)
                    if (result != null) {
                        todo -= scanner
                        transformedScannerList.add(result)
                        break
                    }
                }
            }
        }
        lazyResult = transformedScannerList
        return transformedScannerList
    }

}

//----------------------------------------------------------------------------------------------------------------------

data class Scanner(val id: Int, val beaconPositions: Set<Point3D>, val scannerPosition: Point3D? = null) {

    fun transformAndTranspose(otherScanner: Scanner): Scanner? {
        (0..5).forEach { facingIndex ->
            (0..3).forEach { rotatingIndex ->
                val transformedSet = otherScanner.beaconPositions.map { it.face(facingIndex).rotate(rotatingIndex) }.toSet()

                this.beaconPositions.forEach { s1 ->
                    transformedSet.forEach { s2 ->
                        val difference = s1-s2
                        val movedTransformedSet = transformedSet.map { it.plus(difference) }.toSet()
                        if (movedTransformedSet.intersect(this.beaconPositions).size >= 12) {
                            //we have a mapping!!
                            return Scanner(otherScanner.id, movedTransformedSet, difference)
                        }
                    }
                }

            }
        }
        return null
    }

    companion object {
        //--- scanner 0 ---
        //404,-588,-901
        // ...
        fun of (rawInput: List<String>) : Scanner {
            return Scanner(
                id = rawInput.first().substringAfter("--- scanner "). substringBefore(" ---").toInt(),
                beaconPositions = rawInput.drop(1).map{ Point3D.of(it) }.toSet()
            )
        }
    }
}

operator fun Point3D.plus(other: Point3D): Point3D =
    Point3D(x + other.x, y + other.y, z + other.z)

operator fun Point3D.minus(other: Point3D): Point3D =
    Point3D(x - other.x, y - other.y, z - other.z)

fun Point3D.face(facing: Int): Point3D =
    when (facing) {
        0 -> this
        1 -> Point3D(x, -y, -z)
        2 -> Point3D(x, -z, y)
        3 -> Point3D(-y, -z, x)
        4 -> Point3D(y, -z, -x)
        5 -> Point3D(-x, -z, -y)
        else -> error("Invalid facing")
    }

fun Point3D.rotate(rotating: Int): Point3D =
    when (rotating) {
        0 -> this
        1 -> Point3D(-y, x, z)
        2 -> Point3D(-x, -y, z)
        3 -> Point3D(y, -x, z)
        else -> error("Invalid rotation")
    }
