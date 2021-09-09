package com.example.socialapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.socialapp2.daos.PostDao
import com.example.socialapp2.models.Post
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_main.*

class CreatePostActivity : AppCompatActivity() {
    private lateinit var postDao:PostDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        postDao = PostDao()

        btnPost.setOnClickListener {
            val input = etPostInput.text.toString().trim()
            if (input.isNotEmpty()){
                postDao.addPost(input)
                finish()
            }else{
                Toast.makeText(this, "Please Write Something", Toast.LENGTH_SHORT).show()
            }

        }
    }
}