package cinema

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val columns = readln().toInt()

    val blockedSeats = mutableListOf<Seat>()
    val room = CinemaRoom(rows, columns, blockedSeats)


    printMenu()
    var choice = readln().toInt()

    while (choice != 0) {
        when (choice) {
            1 -> room.printRoom()
            2 -> room.buySeat()
            3 -> room.printStatistics()
        }

        printMenu()
        choice = readln().toInt()
    }
}

fun printMenu() {
    println(
        """
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent()
    )
}



