package com.example.parliamentmembers.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.parliamentmembers.R
import com.example.parliamentmembers.adapters.MemberAdapter
import com.example.parliamentmembers.adapters.MemberListListener
import com.example.parliamentmembers.databinding.Fragment2Binding
import com.example.parliamentmembers.viewmodels.MemberViewModel
import timber.log.Timber

class MembersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<Fragment2Binding>(
            inflater,
            R.layout.fragment_2, container, false
        )
        val memberViewModel =
            ViewModelProvider(
                this
            ).get(MemberViewModel::class.java)

        binding.memberViewModel = memberViewModel

        val adapter = MemberAdapter(MemberListListener { personalNumber ->
            memberViewModel.onMemberClicked(personalNumber)
            Toast.makeText(context, personalNumber.toString(), Toast.LENGTH_LONG).show()
        })
        binding.memberList.adapter = adapter

        val party = MembersFragmentArgs.fromBundle(requireArguments()).party


        memberViewModel.setParty(party)
        memberViewModel.members.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        Log.i("mimi", party)



        memberViewModel.navigateToMemberDetails.observe(viewLifecycleOwner, Observer{ member ->
            member?.let {
                this.findNavController().navigate(MembersFragmentDirections.actionFragment2ToMembersInfoFragment2(member))
                memberViewModel.onMemberDetailNavigated()
            }
            //Log.i("mimi", party)
        })

        return binding.root
    }

}

