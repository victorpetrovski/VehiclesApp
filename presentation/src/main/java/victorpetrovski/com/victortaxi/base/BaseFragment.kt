package victorpetrovski.com.victortaxi.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import victorpetrovski.com.victortaxi.di.extensions.Injectable

abstract class BaseFragment : Fragment(), Injectable {


    abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(tag,"onCreate")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.v(tag,"onViewStateRestored")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        Log.v(tag,"onCreateView")
        return inflater.inflate(getLayout(), container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(tag,"onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.v(tag,"onPause")
    }

    abstract fun setupViews(savedInstanceState: Bundle?)

}