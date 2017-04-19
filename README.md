# AccountSuggestTextView

An editable text view based on AutoCompleteTextView that reads the names of the user's email (and Gmail) accounts and provides them as suggestions.

Useful for login/register fields.

__Works on API 5 (2.0, Eclair) and up.__

![Screenshot](http://i.imgur.com/YaXGjvG.png "Screenshot")

### Usage

Firstly, __make sure your app uses the `GET_ACCOUNTS` permission in its manifest.__

Use `com.stevenschoen.accountsuggesttextview.AccountSuggestTextView` whereever you would use an EditText that's meant for email address entry.

This can be in an xml layout:

```XML
<com.stevenschoen.accountsuggesttextview.AccountSuggestTextView
	android:id="@+id/text_email"
	android:layout_width="match_parent"
	android:layout_height="wrap_content" />
```

Or you can inflate it like any other view.

By default, the user's email addresses won't appear until a few characters are typed into the text field (following AutoCompleteTextView's behavior). Use `setShowDropDownWithNoText(true)` to change this.

### License

Copyright 2013 Steven Schoen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
