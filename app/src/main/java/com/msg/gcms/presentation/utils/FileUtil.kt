package com.msg.gcms.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

@SuppressLint("Range")
fun Uri.toFile(context: Context): File {
    val fileName = getFileName(context)
    val file = createTempFile(context, fileName)
    copyToFile(context, this, file)

    return File(file.absolutePath)
}

fun Uri.uriToBitMap(context: Context): Bitmap {
    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                context.contentResolver,
                this
            )
        )
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, this)
    }
    return bitmap
}

private fun Uri.getFileName(context: Context): String {
    val name = this.toString().split("/").last()
    val ext = context.contentResolver.getType(this)!!.split("/").last()
    return "$name.$ext"
}

private fun createTempFile(context: Context, fileName: String): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File(storageDir, fileName)
}

private fun copyToFile(context: Context, uri: Uri, file: File) {
    val inputStream = context.contentResolver.openInputStream(uri)
    val outputStream = FileOutputStream(file)

    val buffer = ByteArray(4 * 1024)
    while (true) {
        val byteCount = inputStream!!.read(buffer)
        if (byteCount < 0) break
        outputStream.write(buffer, 0, byteCount)
    }

    outputStream.flush()
}