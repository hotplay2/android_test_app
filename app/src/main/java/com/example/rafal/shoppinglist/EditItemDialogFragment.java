package com.example.rafal.shoppinglist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by rafal on 25.03.2017.
 */

public class EditItemDialogFragment extends DialogFragment {

    private EditText mEditText;
    private Button editButton;
    private Button cancelButton;

    public EditItemDialogFragment() {
    }

    public static EditItemDialogFragment newInstance(Item item, int position) {
        EditItemDialogFragment frag = new EditItemDialogFragment();
        Bundle args = new Bundle();
        args.putString("item", item.getName());
        args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    public interface EditItemDialogListener {
        void onFinishEditDialog(String inputText, int position);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        editButton = (Button) view.findViewById(R.id.enter);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                int position = getArguments().getInt("position");
                listener.onFinishEditDialog(mEditText.getText().toString(), position);
                // Close the dialog and return back to the parent activity
                dismiss();
            }
        });
        cancelButton = (Button) view.findViewById(R.id.clear);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        String item = getArguments().getString("item");
        getDialog().setTitle(item);

        mEditText.setText(item);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

}