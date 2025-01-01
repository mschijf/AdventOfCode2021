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

    /*
     *   1. ga overeenkomsten met scanner 0 na
     *      --> dit olever een lijst van sets op die relatief zijn tov scanner 0
     *   2. ga voor alle nog niet gemapte scanners, de overeenkomsten zoeken met de reeds gevonden set.
     */



//    override fun resultPartOne(): String {
//        val baseSet = scannerList[0].beaconPositions.toMutableSet()
//        val scannerSet = mutableSetOf<String>(scannerList[0].name)
//        while (scannerSet.size != scannerList.size) {
//            scannerList.filter{ it.name !in scannerSet}.forEach { scanner ->
//                val result = Scanner("", baseSet).getOverlapWithOrNull(scanner)
//                if (result != null) {
//                    scannerSet.add(scanner.name)
//                    baseSet += result.beaconSet
//                }
//            }
//        }
//        return baseSet.size.toString()
//    }


    // 17435 is too high
    override fun resultPartTwo(): String {
        val baseSet = scannerList[0].beaconPositions.toMutableSet()
        val scannerSet = mutableSetOf<String>(scannerList[0].name)
        val scannerPositionSet = mutableSetOf(Point3D(0,0,0))
        while (scannerSet.size != scannerList.size) {
            scannerList.filter{ it.name !in scannerSet}.forEach { scanner ->
                val result = Scanner("", baseSet).getOverlapWithOrNull(scanner)
                if (result != null) {
                    scannerSet.add(scanner.name)
                    baseSet += result.beaconSet
                    scannerPositionSet.add(result.difference)
                }
            }
        }
        return scannerPositionSet.toList()
            .mapCombinedItems { beacon1, beacon2 ->  beacon1.distanceTo(beacon2)}
            .max()
            .toString()
    }

}

//----------------------------------------------------------------------------------------------------------------------

private class Solution(val scanners: Set<Point3D>, val beacons: Set<Point3D>)

data class Transform(val scanner1: String, val scanner2: String, val difference: Point3D, val beaconSet: Set<Point3D>)

class Scanner(val name: String, val beaconPositions: Set<Point3D>, var scannerPosition: Point3D? = null) {

    fun getOverlapWithOrNull(otherScanner: Scanner): Transform? {
        (0..5).forEach { facingIndex ->
            (0..3).forEach { rotatingIndex ->
                val transformedSet = otherScanner.beaconPositions.map { it.face(facingIndex).rotate(rotatingIndex) }.toSet()

                this.beaconPositions.forEach { s1 ->
                    transformedSet.forEach { s2 ->
                        val difference = s1-s2
                        val movedTransformedSet = transformedSet.map { it.plus(difference) }.toSet()
                        val doorsnede = movedTransformedSet.intersect(this.beaconPositions)
                        if (doorsnede.size >= 12) {
                            //we have a mapping!!
                            return Transform(this.name, otherScanner.name, difference, movedTransformedSet)
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
                name = rawInput.first().substringAfter("--- scanner "). substringBefore(" ---"),
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
