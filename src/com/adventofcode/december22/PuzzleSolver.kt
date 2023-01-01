package com.adventofcode.december22

import com.adventofcode.PuzzleSolverAbstract
import java.lang.Long.max
import java.lang.Long.min

fun main() {
    PuzzleSolver(test=false).showResult()
}

class PuzzleSolver(test: Boolean) : PuzzleSolverAbstract(test) {

    override fun resultPartOne(): String {
        val actionList = input.inputLines.map {Action(it)}.filter{it.inRange(-50,50)}
        var cuboidSet = CuboidSet()

        actionList.forEach {action ->
            if (action.turnOn) {
                cuboidSet = cuboidSet.plus(action.cuboid)
            } else {
                cuboidSet = cuboidSet.minus(action.cuboid)
            }
//            println(cuboidSet.cubeCount())
        }

        return cuboidSet.cubeCount().toString()
//
//
//        val newCube  = Cuboid(-27, 23, -28, -22, 8, 29)
//        val cube = Cuboid(-20, 28, -29, -22, 8, 16)
//
//        println(newCube.minus(cube))
//
//        val c1 = Cuboid(-27, -21, -28, -22, 8, 16)
//        val c2 = Cuboid(-27, 23, -28, -22, 17, 29)
//
//        println(c1)
//        println(c2)
//
//
//        val org = Cuboid(-22, -21, -29, 23, -38, 16)
//
//        println("nc & org = " + newCube.intersection(org))
//        println("c1 & org = " + c1.intersection(org))
//        println("c2 & org = " + c2.intersection(org))
//
//
//        return "HALLO"
    }

    override fun resultPartTwo(): String {
        val actionList = input.inputLines.map {Action(it)}
        var cuboidSet = CuboidSet()

        actionList.forEach {action ->
            if (action.turnOn) {
                cuboidSet = cuboidSet.plus(action.cuboid)
            } else {
                cuboidSet = cuboidSet.minus(action.cuboid)
            }
        }

        return cuboidSet.cubeCount().toString()
    }

}

//----------------------------------------------------------------------------------------------------------------------

class Action(inputLine: String) {

    override fun toString() = "$turnOn --> $cuboid"

    fun inRange(minRange: Long, maxRange: Long): Boolean {
        return cuboid.intersection(Cuboid(minRange, maxRange, minRange, maxRange, minRange, maxRange)).isLegalCuboid()
    }

    val turnOn = inputLine.substringBefore(" ") == "on"
    val cuboid = Cuboid (
        inputLine.substringAfter("x=").substringBefore("..").toLong(),
        inputLine.substringAfter("..").substringBefore(",").toLong(),
        inputLine.substringAfter("y=").substringBefore("..").toLong(),
        inputLine.substringAfter("y=").substringAfter("..").substringBefore(",").toLong(),
        inputLine.substringAfter("z=").substringBefore("..").toLong(),
        inputLine.substringAfter("z=").substringAfter("..").toLong()
    )
}

class CuboidSet(
    private val cuboidList: List<Cuboid> = emptyList()) {

    fun plus(newCuboid: Cuboid): CuboidSet {
        var leftOver = listOf(newCuboid)
        for (cube in cuboidList) {
            val newLeftOverList = mutableListOf<Cuboid>()
            for (newCube in leftOver) {
                newLeftOverList.addAll(newCube.minus(cube))
            }
            leftOver = newLeftOverList
        }
        return CuboidSet(cuboidList + leftOver)
    }

    fun minus(cuboid: Cuboid): CuboidSet {
        return CuboidSet ( cuboidList.map{it.minus(cuboid)}.flatten() )
    }

    fun cubeCount() = cuboidList.sumOf { it.cubeCount() }
}

class Cuboid(
    val minX: Long,
    val maxX: Long,
    val minY: Long,
    val maxY: Long,
    val minZ: Long,
    val maxZ: Long) {

    override fun toString() = "(X:$minX..$maxX  Y:$minY..$maxY  Z:$minZ..$maxZ)"

    private fun emptyCuboid() = Cuboid(1,0,0,0,0,0)

    fun intersection(other: Cuboid): Cuboid {
        if (!this.isLegalCuboid() || !other.isLegalCuboid())
            return emptyCuboid()

        return Cuboid(
            max(minX, other.minX), min(maxX, other.maxX),
            max(minY, other.minY), min(maxY, other.maxY),
            max(minZ, other.minZ), min(maxZ, other.maxZ)
        )
    }

    fun plus(other: Cuboid): List<Cuboid> {
        if (!other.isLegalCuboid())
            return listOf(this)
        val intersection = intersection(other)
        val left = this.minus(intersection)
        val right = other.minus(intersection)
        return (left + right + listOf(intersection)).filter { it.isLegalCuboid() }
    }

    fun minus(other: Cuboid): List<Cuboid> {
        if (!other.isLegalCuboid())
            return listOf(this)

        var minX = minX
        val maxX = maxX
        var minY = minY
        val maxY = maxY
        var minZ = minZ
        val maxZ = maxZ

        val result = mutableListOf<Cuboid>()
        val intersection = intersection(other)

        if (!intersection.isLegalCuboid())
            return listOf(this)



        if (minX < intersection.minX && intersection.maxX < maxX) {
            result.add(Cuboid(minX, intersection.minX-1, minY, maxY, minZ, maxZ))
            minX = intersection.minX
        }
        if (minY < intersection.minY && intersection.maxY < maxY) {
            result.add(Cuboid(minX, maxX, minY, intersection.minY-1, minZ, maxZ))
            minY = intersection.minY
        }
        if (minZ < intersection.minZ && intersection.maxZ < maxZ) {
            result.add(Cuboid(minX, maxX, minY, maxY, minZ, intersection.minZ-1))
            minZ = intersection.minZ
        }

        if (minX < intersection.minX) {
            if (minY < intersection.minY) {
                if (minZ < intersection.minZ) {
                    //intersect rechtsonder , ZO
                    result.add(Cuboid(minX, intersection.minX-1, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, intersection.minY-1,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                                   minZ, intersection.minZ-1))
                } else {
                    result.add(Cuboid(minX, intersection.minX-1, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, intersection.minY-1,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                             intersection.maxZ+1, maxZ))
                }
            } else {
                //intersect rechtsboven , NO
                if (minZ < intersection.minZ) {
                    result.add(Cuboid(minX, intersection.minX-1, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              intersection.maxY+1, maxY,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                                   minZ, intersection.minZ-1))
                } else {
                    result.add(Cuboid(minX, intersection.minX-1, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              intersection.maxY+1, maxY,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                             intersection.maxZ+1, maxZ))
                }
            }
        } else {
            if (minY < intersection.minY) {
                if (minZ < intersection.minZ) {
                    //intersect linksonder , ZW
                    result.add(Cuboid(intersection.maxX+1, maxX, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, intersection.minY-1,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                                   minZ, intersection.minZ-1))
                } else {
                    result.add(Cuboid(intersection.maxX+1, maxX, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, intersection.minY-1,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                             intersection.maxZ+1, maxZ))
                }
            } else {
                if (minZ < intersection.minZ) {
                    //intersect linksboven , NW
                    result.add(Cuboid(intersection.maxX+1, maxX, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              intersection.maxY+1, maxY,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                                   minZ, intersection.minZ-1))
                } else {
                    result.add(Cuboid(intersection.maxX+1, maxX, intersection.minY, intersection.maxY, intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              intersection.maxY+1, maxY,              intersection.minZ, intersection.maxZ))
                    result.add(Cuboid(minX, maxX,              minY, maxY,                             intersection.maxZ+1, maxZ))
                }
            }
        }
        return result.filter{it.isLegalCuboid()}
    }

    fun isLegalCuboid() = minX <= maxX && minY <= maxY && minZ <= maxZ

    fun cubeCount() = (1 + maxX - minX).toLong() * (1 + maxY - minY).toLong() * (1 + maxZ - minZ).toLong()
}

