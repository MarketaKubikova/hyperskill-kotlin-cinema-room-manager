package cinema

class CinemaRoom(
    private val rows: Int,
    private val columns: Int,
    private var blockedSeatList: MutableList<Seat> = mutableListOf()
) {

    fun printRoom() {
        println("Cinema:")
        for (i in 0..this.rows) {
            for (j in 0..this.columns) {
                when {
                    i == 0 && j == 0 -> {
                        print("  ")
                    }

                    i == 0 -> {
                        print("$j ")
                    }

                    j == 0 -> {
                        print("$i ")
                    }

                    else -> if (this.blockedSeatList.find { it.row == i && it.column == j } != null) {
                        print("B ")
                    } else print("S ")
                }
            }
            print("\n")
        }
    }

    fun buySeat() {
        var status = "nok"
        var selectedSeat = selectSeat()
        while (status != "ok") {
            if (isSeatAvailable(selectedSeat)) {
                this.blockedSeatList.add(selectedSeat)
                status = "ok"
                println("Ticket price: ${formatAmount(getTicketPrice(selectedSeat))}")
            } else {
                println("That ticket has already been purchased!")
                selectedSeat = selectSeat()
            }
        }
    }

    private fun isSeatAvailable(selectedSeat: Seat): Boolean {
        for (seat in this.blockedSeatList) {
            if (seat.row == selectedSeat.row && seat.column == selectedSeat.column) {
                return false
            }
        }
        return true
    }

    private fun selectSeat(): Seat {
        var status = "nok"
        var selectedSeat = Seat(0, 0)

        while (status != "ok") {
            println("Enter a row number:")
            val selectedRow = readln().toInt()
            println("Enter a seat number in that row:")
            val selectedColumn = readln().toInt()

            if (selectedRow > 0 && selectedRow <= this.rows &&
                selectedColumn > 0 && selectedColumn <= this.columns
            ) {
                status = "ok"
                selectedSeat = Seat(selectedRow, selectedColumn)
            } else {
                println("Wrong input")
            }
        }
        return selectedSeat
    }

    fun printStatistics() {
        println("""
            Number of purchased tickets: ${getNumberOfPurchasedTickets()}
            Percentage: ${formatPercentage(getPercentage())}
            Current income: ${formatAmount(getCurrentIncome())}
            Total income: ${formatAmount(getTotalIncome())}
        """.trimIndent()
        )
    }

    private fun getTotalSeats(): Int {
        return this.rows * this.columns
    }

    private fun getTicketPrice(seat: Seat): Int {
        val seats = getTotalSeats()

        return when {
            seats < 60 -> {
                10
            }

            else -> {
                val firstHalf = this.rows / 2

                when {
                    seat.row <= firstHalf -> {
                        10
                    }

                    else -> {
                        8
                    }
                }
            }
        }
    }

    private fun getNumberOfPurchasedTickets(): Int {
        return blockedSeatList.size
    }

    private fun getCurrentIncome(): Int {
        var sum = 0
        for (seat in blockedSeatList) {
            sum += getTicketPrice(seat)
        }
        return sum
    }

    private fun getTotalIncome(): Int {
        val seats = getTotalSeats()

        return when {
            seats < 60 -> {
                10 * seats
            }

            else -> {
                val firstHalf = rows / 2
                val secondHalf = rows - firstHalf

                firstHalf * columns * 10 + secondHalf * columns * 8
            }
        }
    }

    private fun getPercentage():Double {
        return (getNumberOfPurchasedTickets().toDouble() / getTotalSeats().toDouble()) * 100.00
    }

    private fun formatPercentage(number: Double): String {
        val percentage = "%.2f".format(number)
        return "$percentage%"
    }

    private fun formatAmount(amount: Int): String {
        return "$$amount"
    }
}
