package vcmsa.projects.prog7313_prog2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vcmsa.projects.prog7313_prog2.ExpenseEntity
import java.io.ByteArrayInputStream

class ExpenseAdapter(private val listener: OnEventClickListener) :
    ListAdapter<ExpenseEntity, ExpenseAdapter.EventViewHolder>(DiffCallback)
{

    interface OnEventClickListener {
        fun onDeleteClick(event: ExpenseEntity)
        fun onEditClick(event: ExpenseEntity)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ExpenseEntity>() {
            override fun areItemsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tv_expense_title)
        private val amount: TextView = itemView.findViewById(R.id.tv_expense_amount)
        private val date: TextView = itemView.findViewById(R.id.tv_expense_date)
        private val time: TextView = itemView.findViewById(R.id.tv_expense_time)
        private val category: TextView = itemView.findViewById(R.id.tv_expense_category)
        private val description: TextView = itemView.findViewById(R.id.tv_expense_description)
        private val imageView: ImageView = itemView.findViewById(R.id.iv_expense_image)

        fun bind(event: ExpenseEntity) {
            title.text = event.expenseTitle
            amount.text = "Amount: R${event.expenseAmount}"
            val dateTimeParts = event.expenseDateTime.split(" ")
            val datePart = dateTimeParts.getOrNull(0) ?: "N/A"
            val timePart = dateTimeParts.getOrNull(1) ?: "N/A"

            date.text = "Date: $datePart"
            time.text = "Time: $timePart"
            category.text = "Category: ${event.expenseCategory}"
            description.text = "Description: ${event.expenseDescription}"

            // Set the image if available
            event.expenseImage?.let {
                val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(it))
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}