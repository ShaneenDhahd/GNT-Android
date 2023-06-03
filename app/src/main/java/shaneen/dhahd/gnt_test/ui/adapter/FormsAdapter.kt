package shaneen.dhahd.gnt_test.ui.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import shaneen.dhahd.gnt_test.R
import shaneen.dhahd.gnt_test.databinding.ItemFormBinding
import shaneen.dhahd.gnt_test.ext.formatDate
import shaneen.dhahd.gnt_test.ext.inflateRvItem
import shaneen.dhahd.gnt_test.network.responses.DataX

class FormsAdapter(
    val clickListener: (String) -> Unit
) : BaseRvAdapter<DataX, BaseViewHolder<DataX>>() {

    inner class CardForm(itemView: View) : BaseViewHolder<DataX>(itemView) {
        private val binding = ItemFormBinding.bind(itemView)
        override fun bind(item: DataX) {
            binding.formCard.setOnClickListener {
                clickListener.invoke(item.gps)
            }
            binding.formId.text = item.id.toString()
            binding.formName.text = item.name
            binding.formComment.text = item.comment
            binding.formDate.text = item.created_at.formatDate()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DataX> {
            return CardForm(parent.inflateRvItem(R.layout.item_form))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<DataX>, position: Int) {
        holder.bind(getItem(position))
    }
}