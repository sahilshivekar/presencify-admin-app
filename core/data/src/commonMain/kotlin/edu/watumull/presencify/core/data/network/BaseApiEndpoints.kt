package edu.watumull.presencify.core.data.network

object BaseApiEndpoints {
//    private const val LOCAL_IP = "192.168.1.100" // Change this to your actual Wi-Fi IP
    private const val LOCAL_IP = "192.168.208.234" // samsung galaxy ip
    private const val LOCAL_PORT = "4444"

//    const val PRESENCIFY_BASE_URL = "http://$LOCAL_IP:$LOCAL_PORT" // use if using on android app
    const val PRESENCIFY_BASE_URL = "http://localhost:$LOCAL_PORT" // use if using on desktop app

    const val API_V1 = "api/v1"

    const val STUDENTS = "students"
    const val TEACHERS = "teachers"
    const val PASSWORD = "password"
    const val IMAGE = "image"
    const val SUBJECTS = "subjects"
    const val CLASSES = "classes"
    const val ROOMS = "rooms"
    const val TIMETABLES = "timetables"
    const val CANCELLED = "cancelled"
    const val ATTENDANCES = "attendances"
    const val ADMINS = "admins"
    const val AUTH = "auth"
    const val BATCHES = "batches"
    const val BRANCHES = "branches"
    const val COURSES = "courses"
    const val DIVISIONS = "divisions"
    const val SCHEMES = "schemes"
    const val SEMESTERS = "semesters"
    const val UNIVERSITIES = "universities"
}