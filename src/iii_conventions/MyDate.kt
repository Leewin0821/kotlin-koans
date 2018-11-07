package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            this.year > other.year -> 1
            this.year < other.year -> -1
            this.month > other.month -> 1
            this.month < other.month -> -1
            this.dayOfMonth > other.dayOfMonth -> 1
            this.dayOfMonth < other.dayOfMonth -> -1
            else -> 0
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    return addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
    return addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.num)
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val num: Int)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(num: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, num)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return DataRangeIterator(start, endInclusive)
    }
}

class DataRangeIterator(val start: MyDate, val endInclusive: MyDate) : Iterator<MyDate> {
    var current: MyDate = start
    override fun hasNext(): Boolean = current <= endInclusive

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
}