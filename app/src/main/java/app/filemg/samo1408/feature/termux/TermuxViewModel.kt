package app.filemg.samo1408.feature.termux

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.filemg.samo1408.core.termux.AndroidTermux
import app.filemg.samo1408.core.termux.TermuxSession

class TermuxViewModel(app: Application) : AndroidViewModel(app) {
    val session = TermuxSession(AndroidTermux(app), viewModelScope)
}
