package info.hellovass.gallerydownloader.library

import java.io.Closeable

class IOUtils {

    companion object {

        fun closeQuietly(closeable: Closeable?) {
            try {
                closeable?.close()
            } catch (e: Throwable) {
                // 忽略
            }
        }

        fun close(closeable: Closeable?) {
            try {
                closeable?.close()
            } catch (e: Throwable) {
                throw RuntimeException("未知错误")
            }
        }
    }
}
