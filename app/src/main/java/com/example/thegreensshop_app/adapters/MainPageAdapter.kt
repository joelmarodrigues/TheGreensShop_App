import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thegreensshop_app.R
import com.example.thegreensshop_app.activities.CartActivity
import com.example.thegreensshop_app.activities.MainActivity
import com.example.thegreensshop_app.activities.OrdersActivity
import com.example.thegreensshop_app.activities.UserDetailsActivity

class MainPageAdapter(private val context: Context) : RecyclerView.Adapter<MainPageAdapter.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE_MAIN = 0
        private const val ITEM_TYPE_CART = 1
        private const val ITEM_TYPE_ORDERS = 2
        private const val ITEM_TYPE_ACCOUNT = 3
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ITEM_TYPE_MAIN
            1 -> ITEM_TYPE_CART
            2 -> ITEM_TYPE_ORDERS
            3 -> ITEM_TYPE_ACCOUNT
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = when (viewType) {
            ITEM_TYPE_MAIN -> layoutInflater.inflate(R.layout.activity_main, parent, false)
            ITEM_TYPE_CART -> layoutInflater.inflate(R.layout.activity_cart, parent, false)
            ITEM_TYPE_ORDERS -> layoutInflater.inflate(R.layout.activity_orders, parent, false)
            ITEM_TYPE_ACCOUNT -> layoutInflater.inflate(R.layout.activity_user_details, parent, false)
            else -> throw IllegalStateException("Unexpected viewType $viewType")
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            // Set click listener for each item in the ViewPager2
            itemView.setOnClickListener {
                val intent = when (bindingAdapterPosition) {
                    0 -> Intent(context, MainActivity::class.java)
                    1 -> Intent(context, CartActivity::class.java)
                    2 -> Intent(context, OrdersActivity::class.java)
                    3 -> Intent(context, UserDetailsActivity::class.java)
                    else -> throw IllegalStateException("Unexpected position $bindingAdapterPosition")
                }
                context.startActivity(intent)
            }
        }
    }
}
