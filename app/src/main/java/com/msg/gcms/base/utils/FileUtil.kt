package com.msg.gcms.base.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object FileUtil {
    fun createTempFile(context: Context, fileName: String): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(storageDir, fileName)
    }


    fun copyToFile(context: Context, uri: Uri, file: File) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        val buffer = ByteArray(4 * 1024)
        while(true) {
            val byteCount = inputStream!!.read(buffer)
            if(byteCount < 0) break
            outputStream.write(buffer, 0, byteCount)
        }
        outputStream.flush()
    }
}