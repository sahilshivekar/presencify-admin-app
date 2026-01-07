package edu.watumull.presencify.core.data.di

import edu.watumull.presencify.core.data.network.HttpClientFactory
import edu.watumull.presencify.core.data.network.academics.KtorRemoteBatchDataSource
import edu.watumull.presencify.core.data.network.academics.KtorRemoteBranchDataSource
import edu.watumull.presencify.core.data.network.academics.KtorRemoteCourseDataSource
import edu.watumull.presencify.core.data.network.academics.KtorRemoteDivisionDataSource
import edu.watumull.presencify.core.data.network.academics.KtorRemoteSchemeDataSource
import edu.watumull.presencify.core.data.network.academics.KtorRemoteSemesterDataSource
import edu.watumull.presencify.core.data.network.academics.KtorRemoteUniversityDataSource
import edu.watumull.presencify.core.data.network.academics.RemoteBatchDataSource
import edu.watumull.presencify.core.data.network.academics.RemoteBranchDataSource
import edu.watumull.presencify.core.data.network.academics.RemoteCourseDataSource
import edu.watumull.presencify.core.data.network.academics.RemoteDivisionDataSource
import edu.watumull.presencify.core.data.network.academics.RemoteSchemeDataSource
import edu.watumull.presencify.core.data.network.academics.RemoteSemesterDataSource
import edu.watumull.presencify.core.data.network.academics.RemoteUniversityDataSource
import edu.watumull.presencify.core.data.network.admin.KtorRemoteAdminDataSource
import edu.watumull.presencify.core.data.network.admin.RemoteAdminDataSource
import edu.watumull.presencify.core.data.network.admin_auth.KtorRemoteAdminAuthDataSource
import edu.watumull.presencify.core.data.network.admin_auth.RemoteAdminAuthDataSource
import edu.watumull.presencify.core.data.network.attendance.KtorRemoteAttendanceDataSource
import edu.watumull.presencify.core.data.network.attendance.RemoteAttendanceDataSource
import edu.watumull.presencify.core.data.network.schedule.KtorRemoteClassSessionDataSource
import edu.watumull.presencify.core.data.network.schedule.KtorRemoteRoomDataSource
import edu.watumull.presencify.core.data.network.schedule.KtorRemoteTimetableDataSource
import edu.watumull.presencify.core.data.network.schedule.RemoteClassSessionDataSource
import edu.watumull.presencify.core.data.network.schedule.RemoteRoomDataSource
import edu.watumull.presencify.core.data.network.schedule.RemoteTimetableDataSource
import edu.watumull.presencify.core.data.network.student.KtorRemoteStudentDataSource
import edu.watumull.presencify.core.data.network.student.RemoteStudentDataSource
import edu.watumull.presencify.core.data.network.student_auth.KtorRemoteStudentAuthDataSource
import edu.watumull.presencify.core.data.network.student_auth.RemoteStudentAuthDataSource
import edu.watumull.presencify.core.data.network.teacher.KtorRemoteTeacherDataSource
import edu.watumull.presencify.core.data.network.teacher.RemoteTeacherDataSource
import edu.watumull.presencify.core.data.network.teacher_auth.KtorRemoteTeacherAuthDataSource
import edu.watumull.presencify.core.data.network.teacher_auth.RemoteTeacherAuthDataSource
import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {

    includes(platformNetworkModule)

    // HttpClientFactory (depends on TokenRepository)
    single { HttpClientFactory(get()) }

    // HttpClient instance (depends on HttpClientFactory and HttpClientEngine)
    single {
        val factory = get<HttpClientFactory>()
        val engine = get<HttpClientEngine>()
        factory.create(engine)
    }

    // Academics Data Sources
    single<RemoteBatchDataSource> { KtorRemoteBatchDataSource(get()) }
    single<RemoteBranchDataSource> { KtorRemoteBranchDataSource(get()) }
    single<RemoteCourseDataSource> { KtorRemoteCourseDataSource(get()) }
    single<RemoteDivisionDataSource> { KtorRemoteDivisionDataSource(get()) }
    single<RemoteSchemeDataSource> { KtorRemoteSchemeDataSource(get()) }
    single<RemoteSemesterDataSource> { KtorRemoteSemesterDataSource(get()) }
    single<RemoteUniversityDataSource> { KtorRemoteUniversityDataSource(get()) }

    // Admin Data Sources
    single<RemoteAdminDataSource> { KtorRemoteAdminDataSource(get()) }
    single<RemoteAdminAuthDataSource> { KtorRemoteAdminAuthDataSource(get()) }

    // Attendance Data Sources
    single<RemoteAttendanceDataSource> { KtorRemoteAttendanceDataSource(get()) }

    // Schedule Data Sources
    single<RemoteClassSessionDataSource> { KtorRemoteClassSessionDataSource(get()) }
    single<RemoteRoomDataSource> { KtorRemoteRoomDataSource(get()) }
    single<RemoteTimetableDataSource> { KtorRemoteTimetableDataSource(get()) }

    // Student Data Sources
    single<RemoteStudentDataSource> { KtorRemoteStudentDataSource(get()) }
    single<RemoteStudentAuthDataSource> { KtorRemoteStudentAuthDataSource(get()) }

    // Teacher Data Sources
    single<RemoteTeacherDataSource> { KtorRemoteTeacherDataSource(get()) }
    single<RemoteTeacherAuthDataSource> { KtorRemoteTeacherAuthDataSource(get()) }
}