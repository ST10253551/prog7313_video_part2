package vcmsa.projects.prog7313_prog2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class CategoryTotals : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryTotalsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_category_totals)

        recyclerView = findViewById(R.id.recyclerViewCategoryTotals)
        val backBtn = findViewById<Button>(R.id.btnBackToAddCategory)

        adapter = CategoryTotalsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val totals = db.expenseDao().getTotalPerCategory()
            adapter.setData(totals)
        }

        backBtn.setOnClickListener {
            startActivity(Intent(this, Add_CategoryActivity::class.java))
        }
    }
}

