package com.example.parliamentmembers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.parliamentmembers.R
import com.example.parliamentmembers.adapters.MemberAdapter
import com.example.parliamentmembers.adapters.MemberListListener
import com.example.parliamentmembers.databinding.Fragment2Binding
import com.example.parliamentmembers.viewmodels.MemberViewModel
import timber.log.Timber

/*Name: My Mai, student ID: 2012197
This Fragment observes the changes in the LiveData object of MemberViewModel
From that update adapter for list of Members of each party.
Date: 05/10/2021
*/

class MembersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<Fragment2Binding>(
            inflater,
            R.layout.fragment_2, container, false
        )
        //create instance of viewmodel
        val memberViewModel =
            ViewModelProvider(
                this
            ).get(MemberViewModel::class.java)

        binding.memberViewModel = memberViewModel

        //call adapter with clickListener to get position of the member clicked
        val adapter = MemberAdapter(MemberListListener { personalNumber ->
            memberViewModel.onMemberClicked(personalNumber)
            Toast.makeText(context, personalNumber.toString(), Toast.LENGTH_LONG).show()
        })
        binding.memberList.adapter = adapter

        //get bundle from PartyFragment and pass to Viewmodel
        val party = MembersFragmentArgs.fromBundle(requireArguments()).party
        memberViewModel.setParty(party)

        //update recyclerview with list of Parliament Members from the party clicked
        memberViewModel.members.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        Timber.i(party)

        //navigate to MembersInfoFragment while sending the person number of the Member clicked
        memberViewModel.navigateToMemberDetails.observe(viewLifecycleOwner, { member ->
            member?.let {
                this.findNavController()
                    .navigate(MembersFragmentDirections.actionFragment2ToMembersInfoFragment2(member))
                memberViewModel.onMemberDetailNavigated()
            }
        })

        return binding.root
    }
}


