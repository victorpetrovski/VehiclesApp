package victorpetrovski.com.victortaxi.features

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import victorpetrovski.com.victortaxi.R
import victorpetrovski.com.victortaxi.di.extensions.Injectable
import victorpetrovski.com.victortaxi.features.listOfVehicles.VehiclesListFragment
import victorpetrovski.com.victortaxi.features.vehiclesMap.MapsFragment

class MainActivity : AppCompatActivity(), Injectable, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationBar: BottomNavigationView

    lateinit var viewPagerAdapter: ViewPagerAdapter

    private var vehiclesListFragment = VehiclesListFragment()

    private var mapsFragment = MapsFragment()

    private var bottomMenuItems = mutableListOf<MenuItem>()

    lateinit var titlesList : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navigationBar = bottomNavigationView
        navigationBar.setOnNavigationItemSelectedListener(this)

        titlesList = listOf(getString(R.string.hamburg_list), getString(R.string.map))

        bottomMenuItems.add(navigationBar.menu.getItem(0))
        bottomMenuItems.add(navigationBar.menu.getItem(1))


        setupViewPager(viewpager)

        //Select the default
        toolbar.title = titlesList[0]


    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(vehiclesListFragment)
        viewPagerAdapter.addFragment(mapsFragment)
        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 2

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                changeFragment(position)
            }

        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == navigationBar.selectedItemId) {
            return false
        }

        when (item.itemId) {
            R.id.action_list -> {
                changeFragment(0)
            }
            R.id.action_map -> {
                changeFragment(1)
            }
        }

        return true
    }


    private fun changeFragment(position: Int) {
        viewpager.currentItem = position

        //Clear Checked state
        bottomMenuItems.forEach {
            it.isChecked = false
        }

        bottomMenuItems[position].isChecked = true

        toolbar.title = titlesList[position]
    }


}
