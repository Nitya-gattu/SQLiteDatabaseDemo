package com.example.databasedemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasedemo.MyUser
import com.example.databasedemo.MyUserAdapter
import com.example.databasedemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var userAdapter: MyUserAdapter
    private lateinit var databaseHelper: DatabaseHelper
    val userDetails= mutableListOf<MyUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        fetchData()
        initview()

    }

    private fun fetchData() = userDetails.addAll(databaseHelper.readData())



    @SuppressLint("NotifyDataSetChanged")
    private fun initview() {
        with(binding){
            recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
            userAdapter = MyUserAdapter(userDetails)
            recyclerview.adapter = userAdapter


            save.setOnClickListener {
                val name = userName.text.toString()
                val ageString = userAge.text.toString()
                val age= ageString.toInt()
                var record= MyUser(id=null, name,age)
                databaseHelper.insertData(record)
                userDetails.add(MyUser(id= null, name,age))
                userAge.text.clear()
               userName.text.clear()
                userAdapter.notifyDataSetChanged()

            }

            btnupdate.setOnClickListener {
                val name = userName.text.toString()
                val ageString = userAge.text.toString()
                val age= ageString.toInt()
                val txtId= enterId.text.toString().toLong()
                val record = MyUser(id=txtId, name,age)

                databaseHelper.updateUser(record)
                val users = databaseHelper.readData()
                userAdapter = MyUserAdapter(users)
                recyclerview.adapter = userAdapter

               userAge.text?.clear()
               userName.text?.clear()
              enterId.text?.clear()

            }

            //deleting the data
            ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    //not necessary
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val user = userDetails[position]

                    user.id?.let {
                        val deleted = databaseHelper.deleteUser(it)

                        if(deleted > 0 ){
                            userDetails.removeAt(position)
                            userAdapter.notifyItemRemoved(position)
                        }
                    }
                }

            }).attachToRecyclerView(recyclerview)

        }

    }


}