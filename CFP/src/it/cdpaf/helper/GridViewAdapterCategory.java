package it.cdpaf.helper;

/**
 * This file is part of AdvancedListViewDemo.
 * You should have downloaded this file from www.intransitione.com, if not, 
 * please inform me by writing an e-mail at the address below:
 *
 * Copyright [2011] [Marco Dinacci <marco.dinacci@gmail.com>]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.

 * The license text is available online and in the LICENSE file accompanying the distribution
 * of this program.
 */

import it.cdpaf.R;
import it.cdpaf.entity.Category;
import it.cdpaf.entity.ListCategories;
import it.cdpaf.entity.ListMacrocategories;
import it.cdpaf.entity.Macrocategory;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapterCategory extends ArrayAdapter<Category> {

	private LayoutInflater mInflater;
	private Context context;
	
	
	private ListCategories listCategories =new ListCategories();
	private int mViewResourceId;
	
	public GridViewAdapterCategory(Context ctx, int viewResourceId, ListCategories mList) {
		super(ctx, viewResourceId,mList);
		context=ctx;
		mInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listCategories=mList;
		
		mViewResourceId = viewResourceId;
	}

	@Override
	public int getCount() {
		return listCategories.size();
	}

	@Override
	public Category getItem(int position) {
		return listCategories.get(position);
	}


	@Override
	public long getItemId(int position) {
		return listCategories.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.category_grid_layout, null);
		
		ImageView iv = (ImageView)convertView.findViewById(R.id.ivCategory);
		TextView tvTitolo = (TextView) convertView.findViewById(R.id.tvTitleCategory);
		int h = context.getResources().getDisplayMetrics().densityDpi;
		convertView.setLayoutParams(new GridView.LayoutParams(h, h));

//		ImageView imageView = new ImageView(context);
//        imageView.setImageResource(R.drawable.ic_launcher);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//		iv.setLayoutParams(new GridView.LayoutParams(200, 200));
		tvTitolo.setText(listCategories.get(position).getName());
        DrawableManager.fetchDrawableOnThread(listCategories.get(position), iv,context);
          
        return convertView;
	}
}
