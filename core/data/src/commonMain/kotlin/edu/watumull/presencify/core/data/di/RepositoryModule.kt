package edu.watumull.presencify.core.data.di

import edu.watumull.presencify.core.data.repository.academics.BatchRepositoryImpl
import edu.watumull.presencify.core.data.repository.academics.BranchRepositoryImpl
import edu.watumull.presencify.core.data.repository.academics.CourseRepositoryImpl
import edu.watumull.presencify.core.data.repository.academics.DivisionRepositoryImpl
import edu.watumull.presencify.core.data.repository.academics.SchemeRepositoryImpl
import edu.watumull.presencify.core.data.repository.academics.SemesterRepositoryImpl
import edu.watumull.presencify.core.data.repository.academics.UniversityRepositoryImpl
import edu.watumull.presencify.core.data.repository.admin.AdminRepositoryImpl
import edu.watumull.presencify.core.data.repository.admin_auth.AdminAuthRepositoryImpl
import edu.watumull.presencify.core.data.repository.attendance.AttendanceRepositoryImpl
import edu.watumull.presencify.core.data.repository.schedule.ClassSessionRepositoryImpl
import edu.watumull.presencify.core.data.repository.schedule.RoomRepositoryImpl
import edu.watumull.presencify.core.data.repository.schedule.TimetableRepositoryImpl
import edu.watumull.presencify.core.data.repository.student.StudentRepositoryImpl
import edu.watumull.presencify.core.data.repository.student_auth.StudentAuthRepositoryImpl
import edu.watumull.presencify.core.data.repository.teacher.TeacherRepositoryImpl
import edu.watumull.presencify.core.data.repository.teacher_auth.TeacherAuthRepositoryImpl
import edu.watumull.presencify.core.domain.repository.academics.BatchRepository
import edu.watumull.presencify.core.domain.repository.academics.BranchRepository
import edu.watumull.presencify.core.domain.repository.academics.CourseRepository
import edu.watumull.presencify.core.domain.repository.academics.DivisionRepository
import edu.watumull.presencify.core.domain.repository.academics.SchemeRepository
import edu.watumull.presencify.core.domain.repository.academics.SemesterRepository
import edu.watumull.presencify.core.domain.repository.academics.UniversityRepository
import edu.watumull.presencify.core.domain.repository.admin.AdminRepository
import edu.watumull.presencify.core.domain.repository.admin_auth.AdminAuthRepository
import edu.watumull.presencify.core.domain.repository.attendance.AttendanceRepository
import edu.watumull.presencify.core.domain.repository.schedule.ClassSessionRepository
import edu.watumull.presencify.core.domain.repository.schedule.RoomRepository
import edu.watumull.presencify.core.domain.repository.schedule.TimetableRepository
import edu.watumull.presencify.core.domain.repository.student.StudentRepository
import edu.watumull.presencify.core.domain.repository.student_auth.StudentAuthRepository
import edu.watumull.presencify.core.domain.repository.teacher.TeacherRepository
import edu.watumull.presencify.core.domain.repository.teacher_auth.TeacherAuthRepository
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule: Module = module {

    // Academics Repositories
    single { BatchRepositoryImpl(get()) } bind BatchRepository::class
    single { BranchRepositoryImpl(get()) } bind BranchRepository::class
    single { CourseRepositoryImpl(get()) } bind CourseRepository::class
    single { DivisionRepositoryImpl(get()) } bind DivisionRepository::class
    single { SchemeRepositoryImpl(get()) } bind SchemeRepository::class
    single { SemesterRepositoryImpl(get()) } bind SemesterRepository::class
    single { UniversityRepositoryImpl(get()) } bind UniversityRepository::class

    // Admin Repositories
    single { AdminRepositoryImpl(get()) } bind AdminRepository::class
    single { AdminAuthRepositoryImpl(get(), get(), get()) } bind AdminAuthRepository::class

    // Attendance Repositories
    single { AttendanceRepositoryImpl(get()) } bind AttendanceRepository::class

    // Schedule Repositories
    single { ClassSessionRepositoryImpl(get()) } bind ClassSessionRepository::class
    single { RoomRepositoryImpl(get()) } bind RoomRepository::class
    single { TimetableRepositoryImpl(get()) } bind TimetableRepository::class

    // Student Repositories
    single { StudentRepositoryImpl(get()) } bind StudentRepository::class
    single { StudentAuthRepositoryImpl(get(), get(), get()) } bind StudentAuthRepository::class

    // Teacher Repositories
    single { TeacherRepositoryImpl(get()) } bind TeacherRepository::class
    single { TeacherAuthRepositoryImpl(get(), get(), get()) } bind TeacherAuthRepository::class
}