package com.jmabilon.tipsy.ui.truthordare.addplayers

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmabilon.tipsy.databinding.FragmentTruthOrDareAddPlayersBinding
import com.jmabilon.tipsy.extensions.abstract.AbsViewBindingFragment
import com.jmabilon.tipsy.extensions.android.safeNavigation
import com.jmabilon.tipsy.ui.truthordare.addplayers.adapter.TruthOrDareAddPLayersAdapter
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersAddButtonViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersBottomViewHolder
import com.jmabilon.tipsy.ui.truthordare.addplayers.viewholder.AddPlayersTextFieldViewHolder

class TruthOrDareAddPlayersFragment :
    AbsViewBindingFragment<FragmentTruthOrDareAddPlayersBinding>(
        FragmentTruthOrDareAddPlayersBinding::inflate
    ),
    AddPlayersBottomViewHolder.AddPLayersBottomListener,
    AddPlayersAddButtonViewHolder.AddPlayersAddButtonListener,
    AddPlayersTextFieldViewHolder.AddPlayersTextFieldListener {

    private var adapter: TruthOrDareAddPLayersAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TruthOrDareAddPLayersAdapter(
            requireContext(),
            this,
            this,
            this
        )
        adapter?.initData(listOf(), resources.displayMetrics.density)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            adapter = this@TruthOrDareAddPlayersFragment.adapter
        }
    }

    override fun onBackClicked() {
        val directions = TruthOrDareAddPlayersFragmentDirections
            .actionTruthOrDareAddPlayersFragmentToTruthOrDareFragment()
        safeNavigation(directions)
    }

    override fun onAddButtonClicked(newPlayerName: String) {
        adapter?.updateTextFieldsList(newPlayerName)
    }

    override fun onRemoveClicked(position: Int) {
        adapter?.removeTextField(position)
    }
}