package edu.watumull.presencify.core.data.network.academics

import edu.watumull.presencify.core.data.network.BaseApiEndpoints.API_V1
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.BATCHES
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.BRANCHES
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.COURSES
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.DIVISIONS
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.PRESENCIFY_BASE_URL
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.SCHEMES
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.SEMESTERS
import edu.watumull.presencify.core.data.network.BaseApiEndpoints.UNIVERSITIES

object ApiEndpoints {
    // Path segments


    // Batch endpoints
    const val GET_BATCHES = "$PRESENCIFY_BASE_URL/$API_V1/$BATCHES"
    const val ADD_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$BATCHES"
    const val GET_BATCH_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$BATCHES"
    const val UPDATE_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$BATCHES"
    const val REMOVE_BATCH = "$PRESENCIFY_BASE_URL/$API_V1/$BATCHES"

    // Branch endpoints
    const val GET_BRANCHES = "$PRESENCIFY_BASE_URL/$API_V1/$BRANCHES"
    const val ADD_BRANCH = "$PRESENCIFY_BASE_URL/$API_V1/$BRANCHES"
    const val GET_BRANCH_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$BRANCHES"
    const val UPDATE_BRANCH = "$PRESENCIFY_BASE_URL/$API_V1/$BRANCHES"
    const val REMOVE_BRANCH = "$PRESENCIFY_BASE_URL/$API_V1/$BRANCHES"

    // Course endpoints
    const val GET_COURSES = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES"
    const val ADD_COURSE = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES"
    const val GET_COURSE_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES"
    const val UPDATE_COURSE = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES"
    const val REMOVE_COURSE = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES"
    const val BULK_CREATE_COURSES = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES/bulk/create"
    const val BULK_DELETE_COURSES = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES/bulk/delete"
    const val BULK_CREATE_COURSES_FROM_CSV = "$PRESENCIFY_BASE_URL/$API_V1/$COURSES/bulk/csv"

    // Division endpoints
    const val GET_DIVISIONS = "$PRESENCIFY_BASE_URL/$API_V1/$DIVISIONS"
    const val ADD_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$DIVISIONS"
    const val GET_DIVISION_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$DIVISIONS"
    const val UPDATE_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$DIVISIONS"
    const val REMOVE_DIVISION = "$PRESENCIFY_BASE_URL/$API_V1/$DIVISIONS"

    // Scheme endpoints
    const val GET_SCHEMES = "$PRESENCIFY_BASE_URL/$API_V1/$SCHEMES"
    const val ADD_SCHEME = "$PRESENCIFY_BASE_URL/$API_V1/$SCHEMES"
    const val GET_SCHEME_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$SCHEMES"
    const val UPDATE_SCHEME = "$PRESENCIFY_BASE_URL/$API_V1/$SCHEMES"
    const val REMOVE_SCHEME = "$PRESENCIFY_BASE_URL/$API_V1/$SCHEMES"

    // Semester endpoints
    const val GET_SEMESTERS = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS"
    const val ADD_SEMESTER = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS"
    const val GET_SEMESTER_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS"
    const val UPDATE_SEMESTER = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS"
    const val REMOVE_SEMESTER = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS"
    const val GET_COURSES_OF_SEMESTER = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS/courses"
    const val BULK_CREATE_SEMESTERS = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS/bulk/create"
    const val BULK_DELETE_SEMESTERS = "$PRESENCIFY_BASE_URL/$API_V1/$SEMESTERS/bulk/delete"

    // University endpoints
    const val GET_UNIVERSITIES = "$PRESENCIFY_BASE_URL/$API_V1/$UNIVERSITIES"
    const val ADD_UNIVERSITY = "$PRESENCIFY_BASE_URL/$API_V1/$UNIVERSITIES"
    const val GET_UNIVERSITY_BY_ID = "$PRESENCIFY_BASE_URL/$API_V1/$UNIVERSITIES"
    const val UPDATE_UNIVERSITY = "$PRESENCIFY_BASE_URL/$API_V1/$UNIVERSITIES"
    const val REMOVE_UNIVERSITY = "$PRESENCIFY_BASE_URL/$API_V1/$UNIVERSITIES"
}