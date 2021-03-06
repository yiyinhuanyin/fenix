/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.loginexceptions

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.component_exceptions.*
import mozilla.components.feature.logins.exceptions.LoginException
import org.mozilla.fenix.R

/**
 * Interface for the ExceptionsViewInteractor. This interface is implemented by objects that want
 * to respond to user interaction on the ExceptionsView
 */
interface ExceptionsViewInteractor {
    /**
     * Called whenever all exception items are deleted
     */
    fun onDeleteAll()

    /**
     * Called whenever one exception item is deleted
     */
    fun onDeleteOne(item: LoginException)
}

/**
 * View that contains and configures the Exceptions List
 */
class LoginExceptionsView(
    container: ViewGroup,
    val interactor: LoginExceptionsInteractor
) : LayoutContainer {

    override val containerView: FrameLayout = LayoutInflater.from(container.context)
        .inflate(R.layout.component_exceptions, container, true)
        .findViewById(R.id.exceptions_wrapper)

    private val exceptionsAdapter = LoginExceptionsAdapter(interactor)

    init {
        exceptions_learn_more.isVisible = false
        exceptions_empty_message.text =
            containerView.context.getString(R.string.preferences_passwords_exceptions_description_empty)
        exceptions_list.apply {
            adapter = exceptionsAdapter
            layoutManager = LinearLayoutManager(containerView.context)
        }
    }

    fun update(state: ExceptionsFragmentState) {
        exceptions_empty_view.isVisible = state.items.isEmpty()
        exceptions_list.isVisible = state.items.isNotEmpty()
        exceptionsAdapter.updateData(state.items)
    }
}
