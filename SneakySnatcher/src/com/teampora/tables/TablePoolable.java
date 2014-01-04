package com.teampora.tables;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.teampora.labels.SneakyLabel;

public class TablePoolable extends TableOverlay implements Poolable{

	
	public TablePoolable(NinePatch bg, float x, float y, float width,
			float height , SneakyLabel label) {
		super(bg, x, y, width, height);
		add(label);
		// TODO Auto-generated constructor stub
	}

	public TablePoolable(NinePatch bg, float x, float y, float width,
			float height, float alpha, Color c) {
		super(bg, x, y, width, height, alpha, c);
		// TODO Auto-generated constructor stub
	}

	public TablePoolable(NinePatch bg, float x, float y, float width,
			float height, float alpha) {
		super(bg, x, y, width, height, alpha);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.ui.Table#reset()
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		//super.reset();
	}
	
	
	

}
