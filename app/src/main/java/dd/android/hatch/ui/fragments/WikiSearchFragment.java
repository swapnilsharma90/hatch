package dd.android.hatch.ui.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import dd.android.hatch.R;
import dd.android.hatch.api.APIService;
import dd.android.hatch.api.WikiApi;
import dd.android.hatch.entities.model.Thumbnail;
import dd.android.hatch.entities.response.WikiResponse;
import dd.android.hatch.ui.adapters.WikiRecyclerAdapter;
import dd.android.hatch.utils.Constants;
import dd.android.hatch.utils.WikiAppUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by swapsharma on 4/7/16.
 */
public class WikiSearchFragment
        extends BaseFragment implements SearchView.OnQueryTextListener, WikiRecyclerAdapter.OnCardViewClickedListener {

    public SearchView searchView;
    private WeakReference<RecyclerView> weakWikiRecyclerView;
    private WeakReference<WikiRecyclerAdapter> weakWikiRecyclerAdapter;
    private WeakReference<WikiApi> weakWikiApi;
    private CompositeSubscription mSubscription = new CompositeSubscription();

    public static WikiSearchFragment newInstance() {
        return new WikiSearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_wikisearch, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Nullable
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hideSoftKeyboard();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycler_view);
        weakWikiRecyclerView = new WeakReference<>(rv);
        weakWikiRecyclerAdapter = new WeakReference<>(new WikiRecyclerAdapter(getActivity()));
        weakWikiRecyclerAdapter.get().setOnCardViewClickedListener(this);
        weakWikiRecyclerView.get().setAdapter(weakWikiRecyclerAdapter.get());
    }

    @Override
    public void onResume() {
        super.onResume();
        mSubscription = WikiAppUtils.getNewCompositeSubIfUnSubscribed(mSubscription);
    }

    @Override
    public void onPause() {
        super.onPause();
        WikiAppUtils.unSubscribeIfNotNull(mSubscription);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onCardViewClicked(View v, Thumbnail thumbnail) {
        showBottomSheetDialog(thumbnail);
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        Log.i("onQueryTextSubmit", query);

        weakWikiApi = new WeakReference<WikiApi>(APIService.createWikiApi());
        onUserInputChanged(query);
        return true;
    }

    public void callService(final String newText) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weakWikiApi = new WeakReference<WikiApi>(APIService.createWikiApi());
                onUserInputChanged(newText);
            }
        });
    }


    @Override
    public boolean onQueryTextChange(final String newText) {
        Log.i("onQueryTextChange", newText);
        if (mSubscription.hasSubscriptions()) {
            mSubscription.clear();
        }
        weakWikiApi = new WeakReference<WikiApi>(APIService.createWikiApi());
        onUserInputChanged(newText);
        return true;
    }

    private void onUserInputChanged(String queryText) {
        updateProgressBar(true);
        mSubscription.add(
                weakWikiApi.get().getWikiSearchResultsNew
                        (Constants.ACTION, Constants.PROP, Constants.FORMAT,
                                Constants.PIPROP, Constants.PITHUMBSIZE, Constants.PILIMIT, Constants.GENERATOR,
                                queryText, 0).debounce(400, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<WikiResponse>() {
                            @Override
                            public void onCompleted() {
                                updateProgressBar(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                updateProgressBar(false);
                                if (e instanceof UnknownHostException) {
                                    showErrorToast(getActivity(), "network error");
                                } else {
                                    showErrorToast(getActivity(), "some error occurred please try later");
                                }
                            }

                            @Override
                            public void onNext(WikiResponse wikiResponse) {
                                hideSoftKeyboard();
                                if (wikiResponse != null && wikiResponse.query != null && wikiResponse.query.pages != null) {
                                    weakWikiRecyclerAdapter.get().setWikiData(WikiAppUtils.getObjectsWithImagesAsList
                                            (wikiResponse.query.pages));
                                    weakWikiRecyclerView.get().setAdapter(weakWikiRecyclerAdapter.get());
                                    weakWikiRecyclerAdapter.get().notifyItemRangeChanged(0, weakWikiRecyclerAdapter.get().getItemCount());
                                    updateProgressBar(false);
                                } else {
                                    updateProgressBar(false);
                                    showToast(getActivity(), "No data found please try a new query");
                                    weakWikiRecyclerAdapter.get().clearAdapterData();
                                }
                            }
                        })

        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_wiki_search_fragment, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            searchView.setOnQueryTextListener(this);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(this);
        return super.onOptionsItemSelected(item);
    }

    private void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}