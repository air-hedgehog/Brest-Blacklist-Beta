package bignerdrunch.brestblacklistgen.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bignerdrunch.brestblacklistgen.R;
import bignerdrunch.brestblacklistgen.Utils;
import bignerdrunch.brestblacklistgen.list_fragments.BeautyAndHealthFragment;
import bignerdrunch.brestblacklistgen.list_fragments.CrimeFragment;
import bignerdrunch.brestblacklistgen.model.Item;
import bignerdrunch.brestblacklistgen.model.ModelCrime;
import de.hdodenhof.circleimageview.CircleImageView;

public class BeautyAndHealthAdapter extends CrimeAdapter{

    private static final int TYPE_CRIME = 0;
    private static final int TYPE_SEPARATOR = 1;

    public BeautyAndHealthAdapter(BeautyAndHealthFragment crimeFragment) {
        super(crimeFragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case TYPE_CRIME:
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.model_crime, viewGroup, false);
                TextView title = (TextView) v.findViewById(R.id.tvCrimeTitle);
                TextView date = (TextView) v.findViewById(R.id.tvCrimeDate);
                CircleImageView priority = (CircleImageView) v.findViewById(R.id.cvTaskPriority);

                return new CrimeViewHolder(v, title, date, priority);
            /*case TYPE_SEPARATOR:
                break;*/
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Item item = items.get(position);
        if (item.isCrime()){
            viewHolder.itemView.setEnabled(true);
            final ModelCrime crime = (ModelCrime) item;
            final CrimeViewHolder crimeViewHolder = (CrimeViewHolder) viewHolder;

            View itemView = crimeViewHolder.itemView;
            final Resources resources = itemView.getResources();

            crimeViewHolder.title.setText(crime.getTitle());

            if (crime.getDate() != 0){
                crimeViewHolder.date.setText(Utils.getFullDate(crime.getDate()));
            } else {
                crimeViewHolder.date.setText(null);
            }

            itemView.setVisibility(View.VISIBLE);

            itemView.setBackgroundColor(resources.getColor(R.color.gray_50));

            crimeViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
            crimeViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
            crimeViewHolder.priority.setColorFilter(resources.getColor(crime.getPriorityColor()));
            crimeViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

            //Выполненая задача по клику на картинке:
            crimeViewHolder.priority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    crime.setStatus(ModelCrime.STATUS_DONE);

                    crimeViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
                    crimeViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
                    crimeViewHolder.priority.setColorFilter(resources.getColor(crime.getPriorityColor()));

                    //Добавление анимации поворота картинки по вертикальной оси:
                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(crimeViewHolder.priority, "rotationY", -180.0f, 0.0f);

                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            crimeViewHolder.priority.setImageResource(R.drawable.ic_check_circle);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                    flipIn.start();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isCrime()) return TYPE_CRIME;
        else return TYPE_SEPARATOR;
    }
}
