package info.hellovass.gallerydownloader.library

import android.content.Context
import android.media.MediaScannerConnection
import java.io.File

class MediaScanner {

    companion object {

        fun scanDir(context: Context, dirPath: String) =
                MediaScannerConnection.scanFile(context.applicationContext, listDirFilePath(dirPath), null, null)

        /**
         * get specific dir's file path
         */
        private fun listDirFilePath(dirPath: String): Array<String> {

            return File(dirPath).list()
                    .map { filePath -> "$dirPath${File.separator}$filePath" }
                    .toTypedArray()
        }
    }
}