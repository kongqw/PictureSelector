package com.luck.picture.lib.config

import android.content.Context
import android.media.MediaMetadataRetriever
import android.text.TextUtils
import com.luck.picture.lib.R
import com.luck.picture.lib.entity.LocalMedia
import java.io.File

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.config
 * email：893855882@qq.com
 * data：2017/5/24
 * @author luck
 */
object PictureMimeType {
    @JvmStatic
    fun ofAll(): Int {
        return PictureConfig.TYPE_ALL
    }

    @JvmStatic
    fun ofImage(): Int {
        return PictureConfig.TYPE_IMAGE
    }

    @JvmStatic
    fun ofVideo(): Int {
        return PictureConfig.TYPE_VIDEO
    }

    @JvmStatic
    fun ofAudio(): Int {
        return PictureConfig.TYPE_AUDIO
    }

    @JvmStatic
    fun isPictureType(pictureType: String?): Int {
        when (pictureType) {
            "image/png", "image/PNG", "image/jpeg", "image/JPEG", "image/webp", "image/WEBP", "image/gif", "image/bmp", "image/GIF", "imagex-ms-bmp" -> return PictureConfig.TYPE_IMAGE
            "video/3gp", "video/3gpp", "video/3gpp2", "video/avi", "video/mp4", "video/quicktime", "video/x-msvideo", "video/x-matroska", "video/mpeg", "video/webm", "video/mp2ts" -> return PictureConfig.TYPE_VIDEO
            "audio/mpeg", "audio/x-ms-wma", "audio/x-wav", "audio/amr", "audio/wav", "audio/aac", "audio/mp4", "audio/quicktime", "audio/lamr", "audio/3gpp" -> return PictureConfig.TYPE_AUDIO
        }
        return PictureConfig.TYPE_IMAGE
    }

    /**
     * 是否是gif
     *
     * @param pictureType
     * @return
     */
    @JvmStatic
    fun isGif(pictureType: String?): Boolean {
        when (pictureType) {
            "image/gif", "image/GIF" -> return true
        }
        return false
    }

    /**
     * 是否是gif
     *
     * @param path
     * @return
     */
    fun isImageGif(path: String): Boolean {
        if (!TextUtils.isEmpty(path)) {
            val lastIndex = path.lastIndexOf(".")
            val pictureType = path.substring(lastIndex, path.length)
            return pictureType.startsWith(".gif") || pictureType.startsWith(".GIF")
        }
        return false
    }

    /**
     * 是否是视频
     *
     * @param pictureType
     * @return
     */
    @JvmStatic
    fun isVideo(pictureType: String?): Boolean {
        when (pictureType) {
            "video/3gp", "video/3gpp", "video/3gpp2", "video/avi", "video/mp4", "video/quicktime", "video/x-msvideo", "video/x-matroska", "video/mpeg", "video/webm", "video/mp2ts" -> return true
        }
        return false
    }

    /**
     * 是否是网络图片
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun isHttp(path: String): Boolean {
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("http") || path.startsWith("https")) {
                return true
            }
        }
        return false
    }

    /**
     * 判断文件类型是图片还是视频
     *
     * @param file
     * @return
     */
    @JvmStatic
    fun fileToType(file: File?): String {
        if (file != null) {
            val name = file.name
            if (
                    name.endsWith(".mp4", true) ||
                    name.endsWith(".avi", true) ||
                    name.endsWith(".3gpp", true) ||
                    name.endsWith(".3gp", true) ||
                    name.endsWith(".mov", true) ||
                    name.endsWith(".m4v", true) ||
                    name.endsWith(".wmv", true)
            ) {
                return "video/mp4"
            } else if (
                    name.endsWith(".PNG", true) ||
                    name.endsWith(".png", true) ||
                    name.endsWith(".jpeg", true) ||
                    name.endsWith(".gif", true) ||
                    name.endsWith(".GIF", true) ||
                    name.endsWith(".jpg", true) ||
                    name.endsWith(".webp", true) ||
                    name.endsWith(".WEBP", true) ||
                    name.endsWith(".JPEG", true) ||
                    name.endsWith(".bmp", true)
            ) {
                return "image/jpeg"
            } else if (
                    name.endsWith(".mp3", true) ||
                    name.endsWith(".amr", true) ||
                    name.endsWith(".aac", true) ||
                    name.endsWith(".war", true) ||
                    name.endsWith(".flac", true) ||
                    name.endsWith(".lamr", true)
            ) {
                return "audio/mpeg"
            }
        }
        return "image/jpeg"
    }

    /**
     * is type Equal
     *
     * @param p1
     * @param p2
     * @return
     */
    @JvmStatic
    fun mimeToEqual(p1: String?, p2: String?): Boolean {
        return isPictureType(p1) == isPictureType(p2)
    }

    @JvmStatic
    fun createImageType(path: String?): String {
        try {
            if (!TextUtils.isEmpty(path)) {
                val file = File(path)
                val fileName = file.name
                val last = fileName.lastIndexOf(".") + 1
                val temp = fileName.substring(last, fileName.length)
                return "image/$temp"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "image/jpeg"
        }
        return "image/jpeg"
    }

    @JvmStatic
    fun createVideoType(path: String?): String {
        try {
            if (!TextUtils.isEmpty(path)) {
                val file = File(path)
                val fileName = file.name
                val last = fileName.lastIndexOf(".") + 1
                val temp = fileName.substring(last, fileName.length)
                return "video/$temp"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "video/mp4"
        }
        return "video/mp4"
    }

    /**
     * Picture or video
     *
     * @return
     */
    @JvmStatic
    fun pictureToVideo(pictureType: String): Int {
        if (!TextUtils.isEmpty(pictureType)) {
            if (pictureType.startsWith("video")) {
                return PictureConfig.TYPE_VIDEO
            } else if (pictureType.startsWith("audio")) {
                return PictureConfig.TYPE_AUDIO
            }
        }
        return PictureConfig.TYPE_IMAGE
    }

    /**
     * get Local video duration
     *
     * @return
     */
    @JvmStatic
    fun getLocalVideoDuration(videoPath: String?): Int {
        val duration: Int
        duration = try {
            val mmr = MediaMetadataRetriever()
            mmr.setDataSource(videoPath)
            mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toInt()
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }
        return duration
    }

    /**
     * 是否是长图
     *
     * @param media
     * @return true 是 or false 不是
     */
    @JvmStatic
    fun isLongImg(media: LocalMedia?): Boolean {
        if (null != media) {
            val width = media.width
            val height = media.height
            val h = width * 3
            return height > h
        }
        return false
    }

    /**
     * 获取图片后缀
     *
     * @param path
     * @return
     */
    @JvmStatic
    fun getLastImgType(path: String): String {
        return try {
            val index = path.lastIndexOf(".")
            if (index > 0) {
                val imageType = path.substring(index, path.length)
                when (imageType) {
                    ".png", ".PNG", ".jpg", ".jpeg", ".JPEG", ".WEBP", ".bmp", ".BMP", ".webp" -> imageType
                    else -> ".png"
                }
            } else {
                ".png"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ".png"
        }
    }

    /**
     * 根据不同的类型，返回不同的错误提示
     *
     * @param mediaMimeType
     * @return
     */
    @JvmStatic
    fun s(context: Context, mediaMimeType: Int): String {
        val ctx = context.applicationContext
        return when (mediaMimeType) {
            PictureConfig.TYPE_IMAGE -> ctx.getString(R.string.picture_error)
            PictureConfig.TYPE_VIDEO -> ctx.getString(R.string.picture_video_error)
            PictureConfig.TYPE_AUDIO -> ctx.getString(R.string.picture_audio_error)
            else -> ctx.getString(R.string.picture_error)
        }
    }

    const val JPEG = ".JPEG"
    const val PNG = ".png"
}