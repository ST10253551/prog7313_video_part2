package vcmsa.projects.prog7313_prog2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.prog7313_prog2.R

class CategoryTotalsAdapter : RecyclerView.Adapter<CategoryTotalsAdapter.ViewHolder>() {

    private var totalsList: List<CategoryTotal> = emptyList()

    fun setData(newData: List<CategoryTotal>) {
        totalsList = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryText: TextView = itemView.findViewById(R.id.textCategory)
        val totalText: TextView = itemView.findViewById(R.id.textTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_total, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = totalsList[position]
        holder.categoryText.text = item.category
        holder.totalText.text = "R${item.total}" // You can format to 2 decimal places if needed
    }

    override fun getItemCount(): Int = totalsList.size
}
