package com.example.databasedemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.databasedemo.databinding.ActivityUserdetailsBinding

class MyUserAdapter(
    private val user:List<MyUser>
):RecyclerView.Adapter<MyUserAdapter.MyViewHolder>() {
    private lateinit var userbinding:ActivityUserdetailsBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyUserAdapter.MyViewHolder {
       userbinding = ActivityUserdetailsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(userbinding)
    }

    override fun onBindViewHolder(holder: MyUserAdapter.MyViewHolder, position: Int) {
       holder.bind(user[position])
    }

    override fun getItemCount() = user.size

    inner class MyViewHolder(private val userbinding: ActivityUserdetailsBinding):
        RecyclerView.ViewHolder(userbinding.root){

        fun bind(userdetail: MyUser){
            with(userbinding){
                displayname.text = userdetail.name
                displayage.text = userdetail.age.toString()
            }
        }

    }

}