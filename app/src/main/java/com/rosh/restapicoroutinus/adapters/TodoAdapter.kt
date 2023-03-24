package com.rosh.restapicoroutinus.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.rosh.restapicoroutinus.databinding.ItemRvBinding
import com.rosh.restapicoroutinus.models.MyData

class TodoAdapter(var list:List<MyData> = emptyList(), val rvClick: RvClick): RecyclerView.Adapter<TodoAdapter.Vh>() {

    inner class Vh(var itemRvBinding: ItemRvBinding): RecyclerView.ViewHolder(itemRvBinding.root){

        fun onBind(myData: MyData, position: Int){
            itemRvBinding.itemTvName.text = myData.sarlavha
            itemRvBinding.itemTvAbout.text = myData.matn
            itemRvBinding.itemTvHolati.text = myData.holat
            itemRvBinding.itemTvMuddat.text = myData.oxirgi_muddat

            itemRvBinding.imageMore.setOnClickListener{
                rvClick.menuClick(itemRvBinding.imageMore, myData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TodoAdapter.Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface RvClick{
        fun menuClick(imageView: ImageView, myData: MyData)
    }
}