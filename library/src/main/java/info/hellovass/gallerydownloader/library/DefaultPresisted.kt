package info.hellovass.gallerydownloader.library

import java.io.*

class DefaultPresisted(private val savePath: String) : Presisted {

    override fun write(inputsteam: InputStream, fileName: String) {

        // 输入输出流
        var bufferedInputStream: BufferedInputStream? = null
        var bufferedOutputStream: BufferedOutputStream? = null

        try {
            bufferedInputStream = BufferedInputStream(inputsteam)
            bufferedOutputStream = BufferedOutputStream(createFileOutputStream(fileName))

            val buffer = ByteArray(1024)

            var len: Int?
            while (true) {
                len = bufferedInputStream.read(buffer)
                if (len <= 0) {
                    break
                }
                bufferedOutputStream.write(buffer, 0, len)
            }
            // flush
            bufferedOutputStream.flush()
        } catch (e: Throwable) {
            e.printStackTrace()
        } finally {
            IOUtils.closeQuietly(bufferedOutputStream)
            IOUtils.closeQuietly(bufferedInputStream)
        }
    }

    /**
     * create FileOUtputStream for save file to storage
     */
    private fun createFileOutputStream(fileName: String): FileOutputStream {

        mkSaveDir(savePath)
        return FileOutputStream("$savePath${File.separator}$fileName.jpg")
    }


    /**
     * create save dir if not exists
     */
    private fun mkSaveDir(savePath: String) {

        val saveDir = File(savePath)

        if (saveDir.exists() && saveDir.isDirectory) {
            return
        }

        if (saveDir.mkdirs())
            return
        else
            throw IllegalStateException("create saveDir failed")
    }
}


