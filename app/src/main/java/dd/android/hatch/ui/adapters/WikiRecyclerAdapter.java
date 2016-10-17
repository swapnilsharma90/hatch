package dd.android.hatch.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import dd.android.hatch.R;
import dd.android.hatch.entities.model.Page;
import dd.android.hatch.entities.model.Thumbnail;
import dd.android.hatch.utils.WikiAppUtils;

/**
 * Created by swapsharma on 4/7/16.
 */
public class WikiRecyclerAdapter extends RecyclerView.Adapter<WikiRecyclerAdapter.WikiViewHolder> {

    Context context;
    private List<Page> pages;
    private OnCardViewClickedListener onCardViewClickedListener;

    public WikiRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public WikiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_layout_new, parent, false);
        WikiViewHolder holder = new WikiViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final WikiViewHolder holder, final int position) {
        String url = pages.get(position).thumbnail.source;

        if (!url.isEmpty()) {
            Picasso.with(context).load(url).placeholder(R.drawable.iv_placeholder).error(android.R.drawable.ic_dialog_alert).
                    into(holder.resultIv, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.resultIv.startAnimation(WikiAppUtils.getScaleAnimation(600));
                        }

                        @Override
                        public void onError() {
                            holder.resultIv.setImageDrawable(context.getResources().getDrawable(android.R.drawable.ic_dialog_alert));
                        }
                    });
        }
        holder.resultIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCardViewClickedListener != null) {
                    onCardViewClickedListener.onCardViewClicked(holder.itemView,
                            pages.get(position).thumbnail);
                }
            }
        });
    }

    public void clearAdapterData() {
        pages.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return pages == null ? 0 : pages.size();
    }

    public void setWikiData(List<Page> pages) {
        this.pages = pages;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setOnCardViewClickedListener(OnCardViewClickedListener listener) {
        onCardViewClickedListener = listener;
    }

    public interface OnCardViewClickedListener {
        void onCardViewClicked(View v, Thumbnail thumbnail);
    }

    protected final class WikiViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView resultIv;

        WikiViewHolder(View itemView) {
            super(itemView);
            resultIv = (ImageView) itemView.findViewById(R.id.resultIv);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }

}