package com.ydh.phonebookmvvm.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ydh.phonebookmvvm.R
import com.ydh.phonebookmvvm.databinding.ItemContactBinding
import com.ydh.phonebookmvvm.databinding.ItemFavoriteBinding
import com.ydh.phonebookmvvm.model.ContactModel

class FavoriteAdapter(
    private val context: Context, private val listener: FavoriteListener
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    interface FavoriteListener {
        fun onClick(contactModel: ContactModel)
    }

    var list = mutableListOf<ContactModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun insertTodo(contactModel: ContactModel) {
        list.add(0, contactModel)
        notifyItemInserted(0)
    }

    fun deleteTodo(contactModel: ContactModel) {
        val index = list.indexOfFirst { it.id == contactModel.id }
        if (index != -1) {
            list.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    class ViewHolder(val itemBinding: ItemFavoriteBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        private var binding : ItemFavoriteBinding? = null

        init {
            this.binding = itemBinding
        }

//        fun bindData(todoModel: TodoModel) {
//            binding.run {
//                tvTodo.text = todoModel.task
//                ivStatus.setImageResource(if (todoModel.status) R.drawable.ic_done else R.drawable.ic_todo)
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val inflater = LayoutInflater.from(context)
        val binding: ItemFavoriteBinding = DataBindingUtil.inflate(inflater,
                R.layout.item_favorite,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model by lazy { list[position] }

        holder.itemBinding.fav = model
        holder.itemBinding.run {
            ivItemFav.setOnClickListener { listener.onClick(model) }
        }
    }

    override fun getItemCount(): Int = list.size
}