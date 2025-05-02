package vcmsa.projects.prog7313_prog2

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream

class IncomeAdapter(private val listener: OnIncomeClickListener) :
    ListAdapter<IncomeEntity, IncomeAdapter.IncomeViewHolder>(DiffCallback) {

    interface OnIncomeClickListener {
        fun onDeleteClick(income: IncomeEntity)
        fun onEditClick(income: IncomeEntity)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<IncomeEntity>() {
            override fun areItemsTheSame(oldItem: IncomeEntity, newItem: IncomeEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: IncomeEntity, newItem: IncomeEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_income, parent, false)
        return IncomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = getItem(position)
        holder.bind(income)
    }

    inner class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_income_title)
        private val amount: TextView = itemView.findViewById(R.id.tv_income_amount)
        private val date: TextView = itemView.findViewById(R.id.tv_income_date)
        private val time: TextView = itemView.findViewById(R.id.tv_income_time)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_income_image)

        fun bind(income: IncomeEntity) {
            title.text = income.incomeTitle
            amount.text = "Amount: R${income.incomeAmount}"
            date.text = "Date: ${income.incomeDateTime.split(" ")[0]}"
            time.text = "Time: ${income.incomeDateTime.split(" ")[1]}"

            income.incomeImage?.let {
                val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(it))
                imageView.setImageBitmap(bitmap)
            }

            // Optional: set click listeners for edit/delete
            itemView.setOnClickListener { listener.onEditClick(income) }
            itemView.setOnLongClickListener {
                listener.onDeleteClick(income)
                true
            }
        }
    }
}
