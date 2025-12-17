package edu.watumull.presencify.admin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform