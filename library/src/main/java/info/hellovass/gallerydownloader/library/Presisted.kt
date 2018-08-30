package info.hellovass.gallerydownloader.library

import java.io.InputStream

interface Presisted {

    fun write(inputsteam: InputStream, fileName: String)
}

