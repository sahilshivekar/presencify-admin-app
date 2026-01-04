package edu.watumull.presencify.core.data.local

import edu.watumull.presencify.core.data.Constants
import java.io.File

actual fun dataStorePath(): String {
    return File(System.getProperty("user.home"), Constants.DATASTORE_FILENAME).absolutePath
}