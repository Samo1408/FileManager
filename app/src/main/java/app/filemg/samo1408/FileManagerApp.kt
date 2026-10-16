package app.filemg.samo1408

import android.app.Application
import android.os.Build
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import coil3.video.VideoFrameDecoder
import app.filemg.samo1408.core.media.ApkIconFetcher
import app.filemg.samo1408.core.media.ApkIconKeyer
import app.filemg.samo1408.core.remote.SafFileSystem
import app.filemg.samo1408.core.root.RootManager
import app.filemg.samo1408.core.shizuku.ShizukuManager
import app.filemg.samo1408.core.sound.SoundPoolDevice
import app.filemg.samo1408.core.sound.UiSounds
import app.filemg.samo1408.core.util.Formatters
import app.filemg.samo1408.core.vault.Vault
import app.filemg.samo1408.ui.i18n.AppLocales
import app.filemg.samo1408.ui.security.AppLock

class FileManagerApp : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        AppLocales.install(this)
        Formatters.locale = resources.configuration.locales[0]
        Vault.init(filesDir, cacheDir)
        AppLock.track(this)
        RootManager.init(this)
        SafFileSystem.appContext = this
        runCatching { ShizukuManager.init(this) }
        runCatching { UiSounds.device = SoundPoolDevice(this).also { it.trackActivities(this) } }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .components {
                add(VideoFrameDecoder.Factory())
                if (Build.VERSION.SDK_INT >= 28) add(AnimatedImageDecoder.Factory()) else add(GifDecoder.Factory())
                add(SvgDecoder.Factory())
                add(ApkIconFetcher.Factory())
                add(ApkIconKeyer())
            }
            .crossfade(true)
            .build()
}
