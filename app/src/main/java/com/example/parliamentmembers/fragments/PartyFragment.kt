package com.example.parliamentmembers.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.parliamentmembers.R
import com.example.parliamentmembers.adapters.PartyAdapter
import com.example.parliamentmembers.adapters.PartyListListener
import com.example.parliamentmembers.databinding.Fragment1Binding
import com.example.parliamentmembers.viewmodels.PartyViewModel

/*Name: My Mai, student ID: 2012197
This Fragment observes the changes in the LiveData object of PartyViewModel
From that update adapter for list of parties sorted from database
Basically it has business logic and binding
Date: 04/10/2021
*/

class PartyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<Fragment1Binding>(
            inflater,
            R.layout.fragment_1, container, false
        )

        //create instance of view model
        val partyViewModel =
            ViewModelProvider(
                this
            ).get(PartyViewModel::class.java)

        setHasOptionsMenu(true) //set option menu to true for navigating

        binding.partyViewModel = partyViewModel

        //call adapter with clickListener to get position of the party clicked
        val adapter = PartyAdapter(PartyListListener { party ->
            partyViewModel.onPartyClicked(party)
            Toast.makeText(context, party, Toast.LENGTH_LONG).show()
        })
        binding.partyList.adapter = adapter

        //update recyclerview with list of Parties
        partyViewModel.parties.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })


        //navigate to MembersFragment while sending the string of Party clicked
        partyViewModel.navigateToMemberList.observe(viewLifecycleOwner, { party ->
            party?.let {
                this.findNavController()
                    .navigate(PartyFragmentDirections.actionFragment1ToFragment2(party))
                partyViewModel.onMemberListNavigated()
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }

    //inflate option menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    //on item select navigate to About Fragment
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}






