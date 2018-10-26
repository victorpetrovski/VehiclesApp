package victorpetrovski.com.victortaxi.features.listOfVehicles

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import victorpetrovski.com.victortaxi.R
import victorpetrovski.com.victortaxi.model.VehicleType
import victorpetrovski.com.victortaxi.model.VehicleView
import javax.inject.Inject

class VehiclesListAdapter @Inject constructor(var context: Context) : RecyclerView.Adapter<VehiclesListAdapter.VehicleViewHolder>() {

    var listOfVehicles: MutableList<VehicleView> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.vehicle_list_item, parent, false)
        return VehicleViewHolder(itemView)
    }

    override fun getItemCount() = listOfVehicles.size

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {

        val vehicleView = listOfVehicles[position]

        holder.vehicleId.text = vehicleView.locationAddress

        if (vehicleView.type == VehicleType.TAXI)
            loadImage(R.drawable.ic_taxi_type, holder.vehicleType)
        else if (vehicleView.type == VehicleType.POOLING)
            loadImage(R.drawable.ic_car, holder.vehicleType)


        holder.vehicleDirection.rotation = vehicleView.heading

    }

    private fun loadImage(resId: Int, view: ImageView) {
        Glide.with(context)
                .load(resId)
                .into(view)
    }

    inner class VehicleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var vehicleId: TextView = view.findViewById(R.id.tv_vehicle_info)
        var vehicleType: ImageView = view.findViewById(R.id.iv_vehicle_type)
        var vehicleDirection: ImageView = view.findViewById(R.id.iv_vehicle_direction)
    }
}