package victorpetrovski.com.victortaxi.util.extension

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

fun <T> LiveData<T>.myObserver(owner: LifecycleOwner, observer: (T?) -> Unit) = observe(owner, Observer<T> { v -> observer.invoke(v) })
