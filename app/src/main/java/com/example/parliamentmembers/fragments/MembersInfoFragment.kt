package com.example.parliamentmembers.fragments

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.parliamentmembers.R
import com.example.parliamentmembers.adapters.CommentAdapter
import com.example.parliamentmembers.databinding.MembersInfoFragmentBinding
import com.example.parliamentmembers.repositories.MemberRepo
import com.example.parliamentmembers.roomdb.Comment
import com.example.parliamentmembers.roomdb.MemberDB
import com.example.parliamentmembers.roomdb.Rating
import com.example.parliamentmembers.viewmodels.MemberDetailViewModel
import kotlinx.android.synthetic.main.list_comments.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception


class MembersInfoFragment : Fragment() {
    private lateinit var binding: MembersInfoFragmentBinding
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Default)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.members_info_fragment, container, false
        )

        val memberDetailViewModel =
            ViewModelProvider(
                this
            ).get(MemberDetailViewModel::class.java)

        binding.memberDetailViewModel = memberDetailViewModel

        //return data to ViewModel
        val memberId = MembersInfoFragmentArgs.fromBundle(requireArguments()).personNumber
        Log.i("mimi", memberId.toString())
        memberDetailViewModel.setPersonalNum(memberId)
        memberDetailViewModel.getRatingByMem(memberId)
        memberDetailViewModel.getCommentByNum(memberId)
        memberDetailViewModel.getRatingAvg(memberId)

        memberDetailViewModel.member.observe(
            viewLifecycleOwner, { member ->
                member.let {
                    binding.apply {
                        activity?.let { it1 ->
                            Glide.with(it1)
                                .load("https://avoindata.eduskunta.fi/" + member.picture)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .circleCrop()
                                .into(memberImage)
                        }
                        binding.ratingBar1.setOnRatingBarChangeListener { p0, _, _ ->
                            coroutineScope.launch {
                                //val rating = Rating(member.personNumber, p0.rating)
                                context?.let { it1 ->
                                    MemberDB.getInstance(it1).ratingDao.insert(
                                        Rating(
                                            personNum = member.personNumber,
                                            numberOfStars = p0.rating
                                        )
                                    )
                                }

                            }
                        }

                        binding.submitButton.setOnClickListener {
                            coroutineScope.launch {
                                context?.let { it1 ->
                                    MemberDB.getInstance(it1).commentDao.insert(
                                        Comment(
                                            personNum = member.personNumber,
                                            comment = binding.commentEditText.text.toString()
                                        )
                                    )
                                }
                            }

                            try {
                                val imm: InputMethodManager =
                                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(
                                    activity?.currentFocus!!.windowToken, 0)
                            } catch (e: Exception) {
                                Log.i("e", error(e))
                            }
                            binding.commentEditText.text = null
                        }




                        if (member.minister) minister.text =
                            getString(R.string.minister) else minister.text = getString(R.string.member)
                        name.text = getString(R.string.name, member.first, member.last)
                        if (member != null) {
                            age.text = getString(R.string.age, 2021 - member.bornYear)
                        }
                        constituency.text = getString(R.string.constituency, member.constituency)
                        party.text = getString(R.string.party, member.party)


                        memberDetailViewModel.ratingAvg.observe(
                            viewLifecycleOwner,
                            Observer { rating ->
                                rating.let {
                                    if(rating != null){ ratingBar1.rating = rating
                                    binding.ratingText.text = rating.toString()}
                                }
                            }
                        )
                        val commentAdapter = CommentAdapter()
                        binding.listComments.adapter = commentAdapter
                        memberDetailViewModel.comment.observe(
                            viewLifecycleOwner,
                            Observer { comment ->
                                comment.let {
                                    if (comment != null)
                                        commentAdapter.submitList(it)
                                }
                            })

                        /*binding.listComments.deleteButton.setOnClickListener{
                            coroutineScope.launch {
                                context?.let { it1 ->
                                    MemberDB.getInstance(it1).commentDao.deleteComment(commentAdapter.)
                                }
                            }*/
                        invalidateAll()
                    }
                }
            })



        return binding.root
    }

}

