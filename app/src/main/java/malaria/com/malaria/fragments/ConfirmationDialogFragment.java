package malaria.com.malaria.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class ConfirmationDialogFragment extends DialogFragment {

    private static final String ARG_MESSAGE = "message";
    private static final String ARG_PERMISSIONS = "permissions";
    private static final String ARG_REQUEST_CODE = "request_code";
    private static final String ARG_NOT_GRANTED_MESSAGE = "not_granted_message";

    public static ConfirmationDialogFragment newInstance(@StringRes int message,
                                                         String[] permissions, int requestCode, @StringRes int notGrantedMessage) {
        ConfirmationDialogFragment fragment = new ConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MESSAGE, message);
        args.putStringArray(ARG_PERMISSIONS, permissions);
        args.putInt(ARG_REQUEST_CODE, requestCode);
        args.putInt(ARG_NOT_GRANTED_MESSAGE, notGrantedMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();
        return new AlertDialog.Builder(getActivity())
                .setMessage(args.getInt(ARG_MESSAGE))
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> {
                            String[] permissions = args.getStringArray(ARG_PERMISSIONS);
                            if (permissions == null) {
                                throw new IllegalArgumentException();
                            }
                            ActivityCompat.requestPermissions(getActivity(),
                                    permissions, args.getInt(ARG_REQUEST_CODE));
                        })
                .setNegativeButton(android.R.string.cancel,
                        (dialog, which) -> Toast.makeText(getActivity(),
                                args.getInt(ARG_NOT_GRANTED_MESSAGE),
                                Toast.LENGTH_SHORT).show())
                .create();
    }

}