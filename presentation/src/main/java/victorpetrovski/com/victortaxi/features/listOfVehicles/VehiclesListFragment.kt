package victorpetrovski.com.victortaxi.features.listOfVehicles


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.fragment_vehicles_list.*
import victorpetrovski.com.victortaxi.R
import victorpetrovski.com.victortaxi.R.id.*
import victorpetrovski.com.victortaxi.base.BaseFragment
import victorpetrovski.com.victortaxi.di.extensions.ViewModelProviderFactory
import victorpetrovski.com.victortaxi.model.VehicleView
import victorpetrovski.com.victortaxi.state.ViewResource
import victorpetrovski.com.victortaxi.state.ViewState
import victorpetrovski.com.victortaxi.util.extension.myObserver
import javax.inject.Inject

class VehiclesListFragment : BaseFragment() {

    val TAG = "VehiclesListFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory

    @Inject
    lateinit var vehiclesViewModel: VehiclesViewModel

    @Inject
    lateinit var vehiclesListAdapter: VehiclesListAdapter

    override fun getLayout() = R.layout.fragment_vehicles_list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate $this")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.v(TAG, "onViewStateRestored $this")
    }


    override fun setupViews(savedInstanceState: Bundle?) {

        Log.v(TAG, "setupViews $this")

        setupRecyclerView()


        // if(savedInstanceState == null) {
        vehiclesViewModel.loadVehiclesList()

        vehiclesViewModel.getVehiclesLiveData().myObserver(this) {

            it?.let {
                Log.v(TAG, "myObserver Receved state ${it.status} ")
                handleViewState(it)
            }
        }

        //}

        //If The error is visible tap on it to retry loading data
        view_error.setOnClickListener {
            vehiclesViewModel.loadVehiclesList()
        }

        //    retainInstance = true
    }


    private fun setupRecyclerView() {
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_vehicles_list.layoutManager = mLayoutManager
        rv_vehicles_list.adapter = vehiclesListAdapter

        swipe_refresh.setColorSchemeResources(R.color.colorPrimary)

        swipe_refresh.setOnRefreshListener {
            vehiclesViewModel.loadVehiclesList()
        }
    }

    private fun handleViewState(viewResource: ViewResource<List<VehicleView>>) {
        when (viewResource.status) {
            ViewState.LOADING -> {
                loading_bar.visibility = if (swipe_refresh.isRefreshing) View.GONE else View.VISIBLE
                view_error.visibility = View.GONE
            }
            ViewState.ERROR -> {
                swipe_refresh.isRefreshing = false
                loading_bar.visibility = View.GONE
                rv_vehicles_list.visibility = View.GONE
                view_error.visibility = View.VISIBLE
                //Toast.makeText(context, "Error : ${viewResource.message}", Toast.LENGTH_LONG).show()
            }
            ViewState.SUCCESS -> {
                swipe_refresh.isRefreshing = false
                loading_bar.visibility = View.GONE
                rv_vehicles_list.visibility = View.VISIBLE
                view_error.visibility = View.GONE
                viewResource.data?.let {
                    vehiclesListAdapter.listOfVehicles = it as MutableList<VehicleView>
                }

            }
        }
    }

}
