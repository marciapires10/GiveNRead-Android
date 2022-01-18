package pt.ua.givenread;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BookStopCheckFragment extends Fragment {

    private String scanResult = "";
    private String[] result;
    private String bookstop = "";
    private String check_type = "";
    TextView bookstop_idTV, check_typeTV;
    Button scanBook;

    public BookStopCheckFragment() {
        // Required empty public constructor
    }

    /**public static BookStopCheckFragment newInstance(String scanResult) {
        BookStopCheckFragment fragment = new BookStopCheckFragment();

        Bundle args = new Bundle();
        args.putString("ScanResult", scanResult);
        fragment.setArguments(args);

        return fragment;
    }**/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("ScanResult")) {
            scanResult = getArguments().getString("ScanResult");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_book_stop_check, container, false);

        bookstop_idTV = view.findViewById(R.id.bookstop_id);
        check_typeTV = view.findViewById(R.id.check_type);
        scanBook = view.findViewById(R.id.scan_book);

        result = scanResult.split(", ", 2);
        bookstop = result[0];
        check_type = result[1];

        bookstop_idTV.setText(bookstop);
        check_typeTV.setText(check_type);

        scanBook.setOnClickListener(v -> {
            BarcodeScannerFragment fragment = new BarcodeScannerFragment();
            Bundle args = new Bundle();
            args.putString("BookStop", bookstop);
            args.putString("CheckType", check_type);
            fragment.setArguments(args);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        });

        return view;
    }
}