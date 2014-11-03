/*
 * Copyright (C) 2013 Steven Schoen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stevenschoen.accountsuggesttextview;

import java.util.ArrayList;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * <p>An editable text view based on AutoCompleteTextView that reads the
 * names of the user's email (and Gmail) accounts and provides them
 * as suggestions. Useful for login/register fields.</p>
 * 
 * <p>Requires the GET_ACCOUNTS permission.</p>
 * 
 * <h1>Options:</h1>
 * <p>{@link #setShowDropDownWithNoText(boolean)}</p>
 * 
 * See {@link android.widget.AutoCompleteTextView} for more info.
 */
public class AccountSuggestTextView extends AutoCompleteTextView {
	
	private ArrayList<String> emails = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	public static final Pattern EMAIL_ADDRESS_COMPAT = Pattern
			.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
					+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
	
	private boolean showDropDownWithNoText = false;
	
	public AccountSuggestTextView(Context context) {
		super(context);
		init();
	}

	public AccountSuggestTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public AccountSuggestTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, emails);
		setAdapter(adapter);
		
		populateEmails();
	}
	
	private void populateEmails() {
		emails.clear();
		Pattern emailPattern = getEmailPattern();
		Account[] accounts = AccountManager.get(getContext()).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches() && !emails.contains(account.name)) {
		        emails.add(account.name);
		    }
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean enoughToFilter() {
		if (showDropDownWithNoText) return true;
		return super.enoughToFilter();
	}
	
	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (focused && showDropDownWithNoText) showDropDown();
	}
	
	public void invalidateEmails() {
		populateEmails();
	}
	
	/**
     * Sets whether the drop-down can be shown before the user has entered text,
     * usually as soon as they tap on the text field.
     *
     * @param show Whether to show the drop-down when the text field is empty.
     */
	public void setShowDropDownWithNoText(boolean show) {
		this.showDropDownWithNoText = show;
		invalidate();
	}
	
	@SuppressLint("NewApi")
	private Pattern getEmailPattern() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			return Patterns.EMAIL_ADDRESS;
		} else {
			return EMAIL_ADDRESS_COMPAT;
		}
	}
}